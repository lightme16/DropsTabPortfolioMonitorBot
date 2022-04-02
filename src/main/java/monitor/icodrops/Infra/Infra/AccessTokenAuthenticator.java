package monitor.icodrops.Infra.Infra;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.IOException;

public record AccessTokenAuthenticator(AccessTokenRepository accessTokenRepository) implements Authenticator {

    @Nullable
    @Override
    public Request authenticate(Route route, @NotNull Response response) throws IOException {
        final String accessToken = accessTokenRepository.getAccessToken();
        if (!isRequestWithAccessToken(response) || accessToken == null) {
            return null;
        }
        synchronized (this) {
            final String newAccessToken = accessTokenRepository.getAccessToken();
            // Access token is refreshed in another thread.
            if (!accessToken.equals(newAccessToken)) {
                return newRequestWithAccessToken(response.request(), newAccessToken);
            }

            // Need to refresh an access token
            return newRequestWithAccessToken(response.request(), accessTokenRepository.updateToken().getAccessToken());
        }
    }

    private boolean isRequestWithAccessToken(Response response) {
        String header = response.request().header("Authorization");
        return header != null && header.startsWith("Bearer");
    }

    @NotNull
    private Request newRequestWithAccessToken(Request request, String accessToken) {
        return request.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();
    }
}