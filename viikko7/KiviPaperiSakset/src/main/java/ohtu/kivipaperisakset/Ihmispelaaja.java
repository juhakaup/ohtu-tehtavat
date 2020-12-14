package ohtu.kivipaperisakset;

import java.util.Scanner;

public class Ihmispelaaja implements Pelaaja {
    
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String annaSiirto() {
        return scanner.nextLine();
    }

    @Override
    public void asetaSiirto(String siirto) {
        // do nothing
    }
    
}
