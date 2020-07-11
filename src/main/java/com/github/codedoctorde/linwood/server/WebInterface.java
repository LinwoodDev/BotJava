package com.github.codedoctorde.linwood.server;


import com.github.codedoctorde.linwood.Linwood;
import io.javalin.Javalin;
import io.sentry.Sentry;
import org.eclipse.jetty.server.Server;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

/**
 * @author CodeDoctorDE
 */
public class WebInterface {
    private final Javalin app;

    public WebInterface(){
        app = Javalin.create();
        register();
        start();
    }

    public void register(){
        app.post("login", AuthController::login);
    }

    public Javalin getApp() {
        return app;
    }

    public void start(){
        try {
            app.start(Linwood.getInstance().getConfig().getPort());
        }catch(Exception e){
            e.printStackTrace();
            Sentry.capture(e);
        }
    }
    public void stop() {
        try{
            app.stop();
        }catch(Exception e){
            e.printStackTrace();
            Sentry.capture(e);
        }
    }
}
