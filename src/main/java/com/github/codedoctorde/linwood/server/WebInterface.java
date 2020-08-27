package com.github.codedoctorde.linwood.server;


import com.github.codedoctorde.linwood.Linwood;
import io.javalin.Javalin;
import io.javalin.core.JavalinServer;
import io.javalin.http.Context;
import io.sentry.Sentry;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author CodeDoctorDE
 */
public class WebInterface {
    private final Javalin app;

    public WebInterface(){
        app = Javalin.create();
        register();
    }

    public void register(){
        app.get("", WebInterface::info);
        app.post("login", AuthController::login);
    }

    public static void info(@NotNull Context context) {
        context.json(new HashMap<>(){{
            put("name", "Linwood");
            put("version", Linwood.getInstance().getVersion());
            put("id", Linwood.getInstance().getJda().getSelfUser().getId());
            put("api-version", Collections.singletonList(0));
        }});
    }

    public Javalin getApp() {
        return app;
    }

    public void start(){
        try {
            if(Linwood.getInstance().getConfig().getPort() != null)
            app.start(Linwood.getInstance().getConfig().getPort());
        }catch(Exception e){
            e.printStackTrace();
            Sentry.capture(e);
        }
    }

    /*//Function for creating the Http2Server
    public Server createHttp2Server() {

        //Creating Server
        var server = new Server();

        //Creating the basic ServerConnector
        var connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        //Setting up some Settings for SSL
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSendServerVersion(false);
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(8443);

        //Some more Settings for SSL (Like adding the Keystore)
        var sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(JavalinServer.class.getResource("keystore.jks").toExternalForm());
        sslContextFactory.setKeyStorePassword("password");
        sslContextFactory.setProvider("Conscrypt");

        //Adding SecureRequestCustomizer
        HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        //Setting the Protocol to Http2
        HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory(httpsConfig);
        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory();
        alpn.setDefaultProtocol("h2");

        //Setting up the SslConnectionFactory
        SslConnectionFactory ssl = new SslConnectionFactory(sslContextFactory, alpn.getProtocol());

        //Creating the Server Connector
        ServerConnector http2Connector = new ServerConnector(server, ssl, alpn, h2, new HttpConnectionFactory(httpsConfig));
        http2Connector.setPort(8443);
        server.addConnector(http2Connector);

        //Returning the Server
        return server;
    }*/
    public void stop() {
        try{
            app.stop();
        }catch(Exception e){
            e.printStackTrace();
            Sentry.capture(e);
        }
    }
}
