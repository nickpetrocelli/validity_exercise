import java.io.IOException;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import app.process.Process;
/**
 * Main application. Extremely simple, it just prints the output of Process to a
 * simple webpage.
 *
 * Webpage is hosted at localhost:4567/parse .
 *
 * @author Nick Petrocelli
 */
public class Application {

  public static void main(String[] args){
    Spark.get("/parse", new Route() {

              @Override
              public Object handle(Request request, Response response) throws Exception {
                response.body(Process.parse());
                return response.body();
              }
            });
  }
}
