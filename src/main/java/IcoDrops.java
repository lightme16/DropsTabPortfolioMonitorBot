import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.text.MessageFormat;

public class IcoDrops {
    private static final Gson gson = new Gson();
    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private static final String ICODROPS_COM_PORTFOLIO_LOGIN = "https://api.icodrops.com/portfolio/login";
    private static final String PORTFOLIO_GROUP_SHORT = "https://api.icodrops.com/portfolio/api/portfolioGroup/short";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final String email;
    private final String password;
    private LoginResponse loginResponse;

    public IcoDrops() {
        email = System.getenv("EMAIL");
        password = System.getenv("PASSWORD");
        if (email == null || password == null)
            throw new RuntimeException("Please set EMAIL and PASSWORD environment variables");
    }

    public void login() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = RequestBody.create(MessageFormat.format("'{'\"email\":\"{0}\",\"password\":\"{1}\"'}'", email, password), JSON);
        Request request = new Request.Builder()
                .url(ICODROPS_COM_PORTFOLIO_LOGIN)
                .method("POST", body)
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        String respBody = response.body().string();
        if (response.code() != 200) {
            System.out.println("Response body: " + body);
            throw new RuntimeException("Failed to loginImp");
        }
        System.out.println("Login successful");
        loginResponse = gson.fromJson(respBody, LoginResponse.class);
    }

    public ShortPortfolio getShortPortfolio() throws IOException {
        String auth = "Bearer " + loginResponse.getAccessToken();

        Request request = new Request.Builder().url(PORTFOLIO_GROUP_SHORT)
                .method("GET", null)
                .addHeader("authorization", auth)
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
        System.out.println(body);

        return gson.fromJson(body, ShortPortfolio.class);
    }
}
