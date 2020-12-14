package ohtu.kivipaperisakset;

import java.util.Scanner;

public abstract class KiviSaksetPaperi {
    private static final Scanner scanner = new Scanner(System.in);
    private String ennenViesti = "";
    private String jalkiViesti = "Tietokone valitsi %s%n";
    private Pelaaja ekaPelaaja;
    
    public void pelaa() {
        Tuomari tuomari = new Tuomari();
        ekaPelaaja = new Ihmispelaaja();
        
        String ekanSiirto = "k";
        String tokanSiirto = "k";
        
        while (onkoOkSiirto(ekanSiirto) && onkoOkSiirto(tokanSiirto)) {
           
           System.out.println("Ensimmäisen pelaajan siirto");
           ekanSiirto = ekaPelaaja.annaSiirto();
           if (!onkoOkSiirto(ekanSiirto)) {
               continue;
           }
           System.out.printf(ennenViesti);
           tokanSiirto = toisenSiirto();
           if (!onkoOkSiirto(tokanSiirto)) {
               continue;
           }
           System.out.printf(jalkiViesti, tokanSiirto);
 
           asetaSiirto(ekanSiirto);
            
           tuomari.kirjaaSiirto(ekanSiirto, tokanSiirto);
           System.out.println(tuomari);
           System.out.println();
        }

        System.out.println();
        System.out.println("Kiitos!");
        System.out.println(tuomari);
    }
    
    public void setViesti(String ennenViesti, String jalkiVieti) {
        this.ennenViesti = ennenViesti;
        this.jalkiViesti = jalkiVieti;
    }

    abstract protected String toisenSiirto();
    
    abstract protected void asetaSiirto(String siirto);
    
    protected static boolean onkoOkSiirto(String siirto) {
        return "k".equals(siirto) || "p".equals(siirto) || "s".equals(siirto);
    }
}