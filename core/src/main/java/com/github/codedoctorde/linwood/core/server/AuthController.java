package com.github.codedoctorde.linwood.core.server;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class AuthController {
    public static void login(@NotNull Context context) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://discord.com/api/oauth2/token?grant_type=authorization_code&code=" + URLEncoder.encode(Objects.requireNonNull(context.formParam("code")), StandardCharsets.UTF_8))).build();
        var response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        context.result(response.body());
    }
}
