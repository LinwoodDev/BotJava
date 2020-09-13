package com.github.codedoctorde.linwood.server;

import com.github.codedoctorde.linwood.Linwood;
import io.javalin.http.Context;
import org.apache.http.client.utils.URIBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

public class SessionController {
    public static void login(@NotNull Context context) throws IOException, InterruptedException, URISyntaxException {
        var code = context.formParam("code");
        if(code == null) {
            context.status(500).json(new HashMap<>() {{
                put("error", "No code given");
            }});
            return;
        }
        var client = HttpClient.newHttpClient();
        var uri = new URIBuilder("https://discord.com/api/v6/oauth2/token").addParameter("grant_type", "authorization_code")
                .addParameter("scope", "identify email connections").addParameter("client_id", Linwood.getInstance().getJda().getSelfUser().getId())
                .addParameter("client_secret", Linwood.getInstance().getToken()).addParameter("code",code).addParameter("redirect_uri", Linwood.getInstance().getConfig().getRedirectURI()).build();
        var request = HttpRequest.newBuilder(uri).build();
        var response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        context.result(response.body());
    }
}
