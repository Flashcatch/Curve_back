package services;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class HomeService {
    private static final Logger LOG = LoggerFactory.getLogger(HomeService.class);

    private final WSClient ws;

    @Inject
    public HomeService(WSClient ws) {
        this.ws = ws;
    }

    // http://localhost:8080/auth/realms/pgk-app/protocol/openid-connect/userinfo with token

    public String getToken() throws ExecutionException, InterruptedException {

        try {

            String url = "http://192.168.100.65:9080/auth/realms/master/protocol/openid-connect/token/";
            //String url = "http://localhost:8080/auth/realms/pgk-app/protocol/openid-connect/token/";
            String token = "";

            CompletionStage<WSResponse> response = ws.url(url)
                    .setContentType("application/x-www-form-urlencoded")
                    //.post("grant_type=password&username=test-user&password=test&client_id=pgk-frontend");
                    .post("grant_type=password&username=admin&password=admin&client_id=pgk-frontend");

            WSResponse resp = response.toCompletableFuture().get();

            if (resp.getStatus() == Http.Status.OK) {
                String responseString = resp.getBodyAsBytes().utf8String();
                JsonNode jsonNode = Json.parse(responseString);

                if (jsonNode.has("access_token")) {
                    token = jsonNode.get("access_token").asText();
                }
            }
            return token;
        }
        catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public Boolean checkToken (String token) throws ExecutionException, InterruptedException {

        //String url = "http://localhost:8080/auth/realms/pgk-app/protocol/openid-connect/userinfo/";
        String url = "http://192.168.100.65:9080/auth/realms/master/protocol/openid-connect/userinfo/";

        CompletionStage<WSResponse> response = ws.url(url)
                .setContentType("application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer "+token)
                .get();

        WSResponse resp = response.toCompletableFuture().get();

        if (resp.getStatus() == Http.Status.OK) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
