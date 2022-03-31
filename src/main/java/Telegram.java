import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Telegram {

    private final TelegramBot bot;
    private final IcoDrops icoDrops;
    private final String user;

    public Telegram() throws IOException {
        String bot_token = System.getenv("BOT_TOKEN");
        bot = new TelegramBot(bot_token);
        icoDrops = new IcoDrops();
        icoDrops.login();
        user = System.getenv("USER");
    }

    public void start() {
        // Register for updates
        bot.setUpdatesListener(updates -> {
            // ... process updates
            updates.forEach(update -> {

                var username = update.message().from().username();
                if (username.equals(user)) {
                    System.out.println("Message from user" + username + ": " + update.message().text());

                    long chatId = update.message().chat().id();

                    String balance;
                    try {
                        balance = getBalance();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }

                    SendMessage request = new SendMessage(chatId, "Balance: " + balance);
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
                else {
                    System.out.println("Message from unknown user" + username + ": " + update.message().text());
                }
            });
            // return id of last processed update or confirm them all
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
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


