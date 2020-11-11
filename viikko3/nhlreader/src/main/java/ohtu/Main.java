
package ohtu;

import com.google.gson.Gson;
import org.apache.http.client.fluent.Request;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {
        
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        Player[] players = mapper.fromJson(bodyText, Player[].class);

        ArrayList<Player> playersFIN = new ArrayList<>();

        System.out.println("Players from FIN\n");
        for(Player p : players) {
            if (p.getNationality().equals("FIN")) {
                playersFIN.add(p);
            }
        }
        Collections.sort(playersFIN, Collections.reverseOrder());

        for (Player p : playersFIN) {
            String format = "%-20s %3s %2d + %2d = %2d %n";
            System.out.printf(format, p.getName(), p.getTeam(), p.getAssists(), p.getClass(), p.getScore());
        }
        
    }
    
}
