import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Telegram {

    private static final int PERIOD = 5000;
    private final TelegramBot bot;
    private final IcoDrops icoDrops;
    private final String targetUser;
    private Timer timer;
    private int last_balance;

    public void start() {
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
                            I can tell you how many drops you have left.
                            You can also check your drops in real time.
                            To get started, send me your ETH address.""", chatId);
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
                            } catch (IOException e) {
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
        var threshold = 0.1;
        Boolean rv = isChangedSignificant(balance, last_balance, threshold);
        if (rv) {
            sendMessageToChat("Balance changed: " + balance, chatId);
            last_balance = balance;
        }
    }

    private Boolean isChangedSignificant(int curr_balance, int last_balance, double threshold) {
        return Math.abs(curr_balance - last_balance) > threshold * last_balance;
    }

    public Telegram() throws IOException {
        String bot_token = System.getenv("BOT_TOKEN");
        bot = new TelegramBot(bot_token);
        icoDrops = new IcoDrops();
        icoDrops.login();
        targetUser = System.getenv("USER");
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
        ShortPortfolio shortPortfolio;
        shortPortfolio = icoDrops.getShortPortfolio();

        var balance = shortPortfolio.getPortfolioGroups()
                .stream()
                .filter(portfolioGroup -> portfolioGroup.getName().equals(account))
                .map(portfolioGroup -> portfolioGroup.getPortfolioTotal().getTotalCap().getUsd())
                .findFirst();
        return parseInt(balance);

    }

    private int parseInt(@NotNull Optional<String> text) {
        int default_value = 0;
        if (text.isPresent()) {
            try {
                return (int) Float.parseFloat(text.get());
            } catch (NumberFormatException e) {
                return default_value;
            }
        } else {
            return default_value;
        }
    }
}
