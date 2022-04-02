package monitor.icodrops.Telegram;

import monitor.icodrops.Infra.AccessTokenRepository;
import monitor.icodrops.Infra.IcoDropsRepository;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MonitorBot {

    private static final int PERIOD = 1000 * 60 * 60;
    private static final double CHANGE_THRESHOLD = 0.1;
    private final TelegramBot bot;
    private final IcoDropsRepository icoDrops;
    private final String targetUser;
    private final AccessTokenRepository accessTokenRepo;
    private Timer timer;
    private int last_balance;

    public void start() throws IOException {
        accessTokenRepo.updateToken();
        bot.setUpdatesListener(updates -> {
            for (var update : updates) {
                if (update.message() == null) {
                    continue;
                }
                var username = update.message().from().username();
                if (!username.equals(targetUser)) {
                    System.out.println("Message from unknown user" + username + ": " + update.message().text());
                    continue;
                }
                var chatId = update.message().chat().id();
                String text = update.message().text();
                String account_prefix = "/account";
                if (text.equals("/start")) {
                    sendMessageToChat("""
                            Hello!
                            I'm icoDrops bot.
                            You can also check your drops in real time.
                            To get started, send me your account name to follow.""", chatId);
                } else if (text.startsWith("/account")) {
                    var account = text.substring(account_prefix.length()).strip();

                    sendMessageToChat("Your account is " + text + ".\n" +
                            "Now, I'll start checking your drops in real time.\n" +
                            "You can stop it by sending /stop command.", chatId);
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                var balance = getBalance(account);
                                sendIfBalanceChanged(balance, chatId);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 0, PERIOD);
                } else if (text.equals("/stop")) {
                    timer.cancel();
                    sendMessageToChat("Drops checking stopped.", chatId);
                } else {
                    sendMessageToChat("""
                            Unknown command.
                            /start - start bot
                            /stop - stop bot
                            0x... - set your ETH address""", chatId);
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void sendIfBalanceChanged(int balance, Long chatId) {
        if (isChangedSignificant(balance, last_balance)) {
            sendMessageToChat("Balance changed: " + balance, chatId);
            last_balance = balance;
        }
    }

    private Boolean isChangedSignificant(int curr_balance, int last_balance) {
        return Math.abs(curr_balance - last_balance) > MonitorBot.CHANGE_THRESHOLD * last_balance;
    }

    public MonitorBot() {
        bot = new TelegramBot(System.getenv("BOT_TOKEN"));
        accessTokenRepo = new AccessTokenRepository(
                System.getenv("EMAIL"),
                System.getenv("PASSWORD"));
        icoDrops = new IcoDropsRepository(accessTokenRepo);
        targetUser = System.getenv("TARGET_USER");
    }

    private void sendMessageToChat(String text, long chatId) {
        SendMessage request = new SendMessage(chatId, text);
        bot.execute(request, new Callback() {
            @Override
            public void onResponse(BaseRequest request, BaseResponse response) {
                System.out.println("Message sent");
            }

            @Override
            public void onFailure(BaseRequest request, IOException e) {
                System.out.println("Message failed");
            }
        });
    }

    private int getBalance(String account) throws IOException {
        var shortPortfolio = icoDrops.getShortPortfolio();

        var balance = shortPortfolio.getPortfolioGroups()
                .stream()
                .filter(portfolioGroup -> portfolioGroup.getName().equals(account))
                .map(portfolioGroup -> portfolioGroup.getPortfolioTotal().getTotalCap().getUsd())
                .findFirst();
        if (balance.isPresent()) {
            return parseInt(balance.get());
        } else {
            throw new IOException("Account not found");
        }

    }

    private int parseInt(String text) {
        try {
            return (int) Float.parseFloat(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
