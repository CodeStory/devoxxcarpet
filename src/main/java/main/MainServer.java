package main;

import net.codestory.http.WebServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainServer {
    public static void main(String[] args) {
        new WebServer().configure(routes -> routes
            .add(CarpetsResource.class)
            .add(VoteResource.class)
            .get("/version", "Java (" + hostname() + ")")
            .get("/_ah/start", "OK")
            .get("/_ah/stop", "OK")
            .get("/_ah/health", "OK"))
            .start();
    }

    private static String hostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "<UNKNOWN>";
        }
    }
}
