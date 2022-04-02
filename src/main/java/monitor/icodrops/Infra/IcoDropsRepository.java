package monitor.icodrops.Infra;

import com.google.gson.Gson;
import monitor.icodrops.Models.ShortPortfolio;
import okhttp3.*;

import java.io.IOException;

public class IcoDropsRepository {
    private static final Gson gson = new Gson();
    private final AccessTokenRepository accessTokenRepository;
    private final OkHttpClient client;

    private static final String PORTFOLIO_URL = "https://api.icodrops.com/portfolio/api/portfolioGroup/short";

    public IcoDropsRepository(AccessTokenRepository accessTokenRepo) {
        accessTokenRepository = accessTokenRepo;
        client = new OkHttpClient()
                .newBuilder()
                .authenticator(new AccessTokenAuthenticator(accessTokenRepository))
                .build();
    }

    public ShortPortfolio getShortPortfolio() throws IOException {
        Request request = new Request.Builder().url(PORTFOLIO_URL).method("GET", null)
                .addHeader("authorization", "Bearer %s".formatted(accessTokenRepository.getAccessToken()))
                .addHeader("content-type", "application/json")
                .addHeader("user-agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36")
                .addHeader("accept", "*/*").build();
        Response response = client.newCall(request).execute();
        String body = response.body().string();
        if (response.code() != 200) {
            System.out.println("Response body: " + body);
            throw new RuntimeException("Failed to get short portfolio");
        }
        return gson.fromJson(body, ShortPortfolio.class);
    }
}
