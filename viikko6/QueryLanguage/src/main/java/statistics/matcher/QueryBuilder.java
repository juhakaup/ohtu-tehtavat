package statistics.matcher;

public class QueryBuilder {
    
    Matcher matcher;
    
    public QueryBuilder() {
        this.matcher = new All();
    }
    
    public Matcher build() {
        return matcher;
    }
    
    public QueryBuilder playsIn(String joukkue) {
        this.matcher = new And(new PlaysIn(joukkue), matcher);
        return this;
    }
    
    public QueryBuilder hasAtLeast(int value, String category) {
        this.matcher = new And(new HasAtLeast(value, category), matcher);
        return this;
    }
    
    public QueryBuilder hasFewerThan(int value, String category) {
        this.matcher = new And(new HasFewerThan(value, category), matcher);
        return this;
    }
}
