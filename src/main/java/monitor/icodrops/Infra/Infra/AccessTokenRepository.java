package monitor.icodrops.Infra.Infra;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.text.MessageFormat;

public class AccessTokenRepository {
    private LoginResponse loginResponse;
    private static final Gson gson = new Gson();
    private String email;
    private String password;

    public AccessTokenRepository() {
    }

    public String getAccessToken() {
        return loginResponse.getAccessToken();
    }

    public LoginResponse updateToken() throws IOException {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();
        RequestBody body = RequestBody.create(MessageFormat.format("'{'\"email\":\"{0}\",\"password\":\"{1}\"'}'", email, password), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("https://api.icodrops.com/portfolio/login")
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

    public AccessTokenRepository setEmail(String e) {
        email = e;
        return this;
    }

    public void setPassword(String p) {
        password = p;
    }
}
