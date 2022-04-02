package monitor.dropstab.Infra;
import com.google.gson.Gson;
import monitor.dropstab.Models.LoginResponse;
import okhttp3.*;

import java.io.IOException;
import java.text.MessageFormat;

public class AccessTokenRepository {
    private static final String LOGIN_URL = "https://api.icodrops.com/portfolio/login";
    private final OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .build();
    private static final Gson gson = new Gson();
    private LoginResponse loginResponse;
    private final String email;
    private final String password;

    public AccessTokenRepository(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getAccessToken() {
        return loginResponse.getAccessToken();
    }

    public LoginResponse updateToken() throws IOException {
        RequestBody body = RequestBody.create(MessageFormat.format("'{'\"email\":\"{0}\",\"password\":\"{1}\"'}'", email, password), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(LOGIN_URL)
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
        LoginResponse loginResponse = gson.fromJson(respBody, LoginResponse.class);
        this.loginResponse = loginResponse;
        return loginResponse;
    }
}
