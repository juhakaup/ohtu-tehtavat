
package ohtu;

public class Player implements Comparable<Player> {
    private String name;
    private String nationality;
    private String team;
    private int goals;
    private int assists;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getTeam() {
        return team;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }

    public int getScore(){
        return assists + goals;
    } 

    @Override
    public String toString() {
        return name + " team " + team + " goals " + goals + " assists " + assists;
    }

    @Override
    public int compareTo(Player other) {
        int scoreThis = goals + assists;
        int scoreOther = other.getGoals() + other.getAssists();

        return scoreThis - scoreOther;
    } 
      
}
