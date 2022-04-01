import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Telegram {

    private static final int PERIOD = 5000;
    private final TelegramBot bot;
    private final IcoDrops icoDrops;
    private final String targetUser;
    private Timer timer;

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
                if (text.equals("/start")) {
                    sendMessageToChat("Hello!\n" +
                            "I'm icoDrops bot.\n" +
                            "I can tell you how many drops you have left.\n" +
                            "You can also check your drops in real time.\n" +
                            "To get started, send me your ETH address.", chatId);
                } else if (text.startsWith("0x")) {
                    sendMessageToChat("Your address is " + text + ".\n" +
                            "Now, I'll start checking your drops in real time.\n" +
                            "You can stop it by sending /stop command.", chatId);
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                String balance = getBalance();
                                sendMessageToChat("Balance: " + balance, chatId);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 0, PERIOD);
                } else if (text.equals("/stop")) {
                    timer.cancel();
                    sendMessageToChat("Drops checking stopped.", chatId);
                } else {
                    sendMessageToChat("Unknown command.\n" +
                            "/start - start bot\n" +
                            "/stop - stop bot\n" +
                            "0x... - set your ETH address", chatId);
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
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

    @NotNull
    private String getBalance() throws IOException {
        ShortPortfolio shortPortfolio;
        shortPortfolio = icoDrops.getShortPortfolio();

        var balances = shortPortfolio.getPortfolioGroups().stream()
                .map(portfolioGroup -> portfolioGroup.getPortfolioTotal().getTotalCap().getUsd()).toList();

        return String.join(", ", balances);
    }
}
