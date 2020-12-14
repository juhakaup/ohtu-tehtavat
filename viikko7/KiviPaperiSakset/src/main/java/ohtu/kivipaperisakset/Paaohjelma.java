package ohtu.kivipaperisakset;

import java.util.Scanner;

public class Paaohjelma {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\nValitse pelataanko"
                    + "\n (a) ihmistä vastaan "
                    + "\n (b) tekoälyä vastaan"
                    + "\n (c) parannettua tekoälyä vastaan"
                    + "\nmuilla valinnoilla lopetataan");

            String vastaus = scanner.nextLine();
            if (vastaus.endsWith("a")) {
                tulostaOhje();
                new KSPPeli().pelaajaVsPelaaja().pelaa();
            } else if (vastaus.endsWith("b")) {
                tulostaOhje();
                new KSPPeli().pelaajaVsTekoaly().pelaa();
            } else if (vastaus.endsWith("c")) {
                tulostaOhje();
                new KSPPeli().pelaajaVsParempiTekoaly().pelaa();
            } else {
                break;
            }

        }

    }
    
    private static void tulostaOhje() {
        System.out.println("peli loppuu kun pelaaja antaa virheellisen siirron eli jonkun muun kuin k, p tai s");
    }
    
}
