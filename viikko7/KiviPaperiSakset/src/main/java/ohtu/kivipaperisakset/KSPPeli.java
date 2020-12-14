package ohtu.kivipaperisakset;

public class KSPPeli extends KiviSaksetPaperi {

    private Pelaaja tokaPelaaja;
    
    public KSPPeli pelaajaVsPelaaja() {
        tokaPelaaja = new Pelaajatehdas().luoIhmispelaaja();
        super.setViesti("Toisen pelaajan siirto%n", "");
        return this;
    }
    
    public KSPPeli pelaajaVsTekoaly() {
        tokaPelaaja = new Pelaajatehdas().luoTekoaly();
        return this;
    }
    
    public KSPPeli pelaajaVsParempiTekoaly() {
        tokaPelaaja = new Pelaajatehdas().luoParempiTekoaly(20);
        return this;
    }

    @Override
    protected String toisenSiirto() {
        return tokaPelaaja.annaSiirto();
    }

    @Override
    protected void asetaSiirto(String siirto) {
        tokaPelaaja.asetaSiirto(siirto);
    }
}
