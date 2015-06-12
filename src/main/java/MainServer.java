import resources.CarpetsResource;
import resources.VoteResource;
import net.codestory.http.WebServer;

public class MainServer {
    public static void main(String[] args) {
        new WebServer().configure(routes -> routes
            .add(CarpetsResource.class)
            .add(VoteResource.class)
            .get("/_ah/start", "OK")
            .get("/_ah/stop", "OK")
            .get("/_ah/health", "OK"))
            .start();
    }
}
