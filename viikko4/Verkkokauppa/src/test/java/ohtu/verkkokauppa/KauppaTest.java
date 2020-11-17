package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa kauppa;

    // alustetaan testeille yhteiset mock-oliot ja kauppa joka käyttää niitä
    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        kauppa = new Kauppa(varasto, pankki, viite);

        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));  
        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 3));
        when(varasto.saldo(4)).thenReturn(6);
        when(varasto.haeTuote(4)).thenReturn(new Tuote(4, "juusto", 11));
        
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        kauppa.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());   
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOIkeallaNimellaTilinumerollaJaSummalla() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("mikko", "54321");

        // testataan että pankin metodia kutsutaan oikealla nimellä, tilinumerolla ja summalla
        verify(pankki).tilisiirto(eq("mikko"), anyInt(), eq("54321"), anyString(), eq(3));
    }

    @Test
    public void kahdenEriTuotteenOstamisenJalkeenPankinMetodiaTilisiirtoKutsutaanOIkeillaParamtreilla() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.lisaaKoriin(4);
        kauppa.tilimaksu("mikko", "54321");

        // testataan että pankin metodia kutsutaan oikeilla parametreilla kahden eri tuotteen ostamisen jälkeen
        verify(pankki).tilisiirto(eq("mikko"), anyInt(), eq("54321"), anyString(), eq(14));
    }

    @Test
    public void kahdenSamanTuotteenOstamisenJalkeenPankinMetodiaTilisiirtoKutsutaanOIkeillaParamtreilla() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(4);
        kauppa.lisaaKoriin(4);
        kauppa.tilimaksu("tytti", "52341");

        // testataan että pankin metodia kutsutaan kutsutaan oikeilla parametreilla kahden eri tuotteen ostamisen jälkeen
        verify(pankki).tilisiirto(eq("tytti"), anyInt(), eq("52341"), anyString(), eq(22));
    }

    @Test
    public void tuotteenJotaEiOleVarastossaOstamisenJalkeenPankinMetodiaTilisiirtoKutsutaanOIkeillaParamtreilla() {
        // määritellään testissä käytettävät tuotteeet
        when(varasto.saldo(6)).thenReturn(0);
        when(varasto.haeTuote(6)).thenReturn(new Tuote(6, "juusto", 6));

        // tehdään ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.lisaaKoriin(6);
        kauppa.tilimaksu("jonny", "75267");

        // testataan että pankin metodia kutsutaan oikeilla parametreilla tuotteen jota ei ole varastossa ostamisen jälkeen
        verify(pankki).tilisiirto(eq("jonny"), anyInt(), eq("75267"), anyString(), eq(3));
    }

    @Test
    public void kaupanMetodiAloitaAsiointiNollaaOstoskorin() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("raimo", "12343");

        // tehdään toiset ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(4);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("juulia", "98372");

        // testataan että, pankin metodia kutsutaan oikealla summalla
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(16));
    }

    @Test
    public void viitegeneraattoriltaPyydetaanAloitaAsioinninYhteydessaUusiViite() {
        // tehdään kahdet ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(12);
        kauppa.tilimaksu("matti", "12345");

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(12);
        kauppa.tilimaksu("teppo", "12346");

        // testataan että viitegeneraattorilta on pyydetty uutta viitettä kaksi kertaan
        verify(viite, times(2)).uusi();
    }

    @Test
    public void tuotteenPoistaminenOstoskoristaToimii() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.lisaaKoriin(4);
        kauppa.poistaKorista(2);
        kauppa.tilimaksu("teppo", "12398");

        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(11));
    }
}