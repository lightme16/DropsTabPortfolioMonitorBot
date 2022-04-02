import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class IcoDrops {
    private static final Gson gson = new Gson();
    private static final AccessTokenRepository accessTokenRepository = new AccessTokenRepository();
    private static final OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .authenticator(new AccessTokenAuthenticator(accessTokenRepository))
            .build();
    private static final String PORTFOLIO_GROUP_SHORT = "https://api.icodrops.com/portfolio/api/portfolioGroup/short";


    public IcoDrops() {
        accessTokenRepository
                .setEmail(System.getenv("EMAIL"))
                .setPassword(System.getenv("PASSWORD"));
    }

    public void login() throws IOException {
        accessTokenRepository.updateToken();
    }

    public ShortPortfolio getShortPortfolio() throws IOException {
        Request request = new Request
                .Builder()
                .url(PORTFOLIO_GROUP_SHORT)
                .method("GET", null)
                .addHeader("authorization", "Bearer %s".formatted(accessTokenRepository.getAccessToken()))
                .addHeader("content-type", "application/json")
                .addHeader("user-agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36")
                .addHeader("accept", "*/*")
                .build();
        Response response = client.newCall(request).execute();
        String body = response.body().string();
        if (response.code() != 200) {
            System.out.println("Response body: " + body);
            throw new RuntimeException("Failed to get short portfolio");
        }
        return gson.fromJson(body, ShortPortfolio.class);
    }
}
