package com.github.codedoctorde.linwood.server;


import io.sentry.Sentry;
import org.eclipse.jetty.server.Server;

/**
 * @author CodeDoctorDE
 */
public class WebInterface {
    private final Server server;

    public WebInterface(){
        server = new Server();
        server.setHandler(new WebInterfaceHandler());
    }

    public Server getServer() {
        return server;
    }
    public void start(){
        try {
            server.start();
            server.join();
        }catch(Exception e){
            e.printStackTrace();
            Sentry.capture(e);
        }
    }
    public void stop() {
        try{
        server.stop();
        }catch(Exception e){
            e.printStackTrace();
            Sentry.capture(e);
        }
    }
}
