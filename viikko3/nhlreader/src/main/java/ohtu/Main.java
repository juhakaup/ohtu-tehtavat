
package ohtu;

import com.google.gson.Gson;
import org.apache.http.client.fluent.Request;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        Player[] players = mapper.fromJson(bodyText, Player[].class);

        System.out.println("Players from FIN");
        for(Player p : players) {
            if (p.getNationality().equals("FIN")) {
                System.out.println(p);
            }
        }
    }
    
}
