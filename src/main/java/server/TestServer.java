package server;

import rest.ApplicationConfig;
import java.io.IOException;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class TestServer {

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost/api").port(9090).build();
  }

  static final URI BASE_URI = getBaseURI();

  static HttpServer startServer() {
    ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
    //ResourceConfig rc = new ResourceConfig(ApplicationConfig.class);
    return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
  }

  public static void main(String[] args) throws IOException {
    System.out.println("Starting grizzly...");
    HttpServer httpServer = startServer();
    System.in.read();
    httpServer.shutdownNow();
  }
}