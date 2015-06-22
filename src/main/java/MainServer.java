import net.codestory.http.WebServer;

public class MainServer {
    public static void main(String[] args) {
        new WebServer().configure(routes -> routes.add(VoteResource.class)).start();
    }
}
