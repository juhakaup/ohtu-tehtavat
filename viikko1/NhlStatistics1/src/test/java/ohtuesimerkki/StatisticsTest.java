
package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author scyti
 */
public class StatisticsTest {
    
    Reader readerStub = new Reader() {
 
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<>();
 
            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));
 
            return players;
        }
    };
 
    Statistics stats;

    @Before
    public void setUp(){
        // luodaan Statistics-olio joka k‰ytt‰‰ "stubia"
        stats = new Statistics(readerStub);
    } 
    
    public StatisticsTest() {
    }
    
    @Test
    public void testiTesti() {
        assertEquals(3,3);
    }
    
    @Test
    public void playerSearchPalauttaaOikeanPelaajan() {
        Player player = stats.search("Kurri");
        assertEquals(player.getName(), "Kurri");
        assertEquals(player.getAssists(), 53);
        assertEquals(player.getGoals(), 37);
        assertEquals(player.getTeam(), "EDM");
    }
    
    @Test
    public void playerSearchPalauttaaNullJosPelaajaaEiLoydy() {
        Player player = stats.search("Kinnunen");
        assertEquals(player, null);
    }
    
    @Test
    public void teamPalauttaaOikein() {
        List<Player> team = stats.team("EDM");
        assertEquals(team.size(), 3);
        for(Player p : team) {
            assertEquals(p.getTeam(), "EDM");
        }
    }
    
    @Test
    public void topScorersPalauttaaOikein() {
        List<Player> topScorers = stats.topScorers(3);
        assertEquals(topScorers.size(), 3);
        int score = 9999;
        for (Player p : topScorers) {
            int pScore = p.getPoints();
            assertTrue(score >= pScore);
        }
    }

    
}

