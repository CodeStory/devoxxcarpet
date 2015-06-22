import net.codestory.http.WebServer;
import net.codestory.http.injection.Singletons;
import net.codestory.http.misc.Env;

import java.util.Random;

public class TestWebServer {
    private final WebServer webServer;

    public TestWebServer(Random random) {
        this.webServer = new WebServer() {
            @Override
            protected Env createEnv() {
                return Env.prod();
            }
        }.configure(routes -> routes
                .setIocAdapter(new Singletons().register(Random.class, random))
                .add(VoteResource.class)
        ).startOnRandomPort();
    }

    public int port() {
        return webServer.port();
    }

    public void stop() {
        webServer.stop();
    }
}
