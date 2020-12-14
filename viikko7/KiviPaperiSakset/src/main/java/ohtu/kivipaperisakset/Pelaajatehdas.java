package ohtu.kivipaperisakset;

public class Pelaajatehdas {
    public Pelaaja luoIhmispelaaja() {
        return new Ihmispelaaja();
    }
    
    public Pelaaja luoTekoaly() {
        return new Tekoaly();
    }
    
    public Pelaaja luoParempiTekoaly(int muisti) {
        return new TekoalyParannettu(muisti);
    }
}
