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
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));          

        // tehdään ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        kauppa.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());   
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOIkeallaNimellaTilinumerollaJaSummalla() {
        // määritellään testissä käytettävä tuote
        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 3));

        // tehdään ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("mikko", "54321");

        // testataan että pankin metodia kutsutaan oikealla nimellä, tilinumerolla ja summalla
        verify(pankki).tilisiirto(eq("mikko"), anyInt(), eq("54321"), anyString(), eq(3));
    }

    @Test
    public void kahdenEriTuotteenOstamisenJalkeenPankinMetodiaTilisiirtoKutsutaanOIkeillaParamtreilla() {
        // määritellään testissä käytettävät tuotteeet
        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.saldo(4)).thenReturn(2);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 3));
        when(varasto.haeTuote(4)).thenReturn(new Tuote(4, "juusto", 6));

        // tehdään ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.lisaaKoriin(4);
        kauppa.tilimaksu("mikko", "54321");

        // testataan että pankin metodia kutsutaan oikeilla parametreilla kahden eri tuotteen ostamisen jälkeen
        verify(pankki).tilisiirto(eq("mikko"), anyInt(), eq("54321"), anyString(), eq(9));
    }

    @Test
    public void kahdenSamanTuotteenOstamisenJalkeenPankinMetodiaTilisiirtoKutsutaanOIkeillaParamtreilla() {
        // määritellään testissä käytettävä tuote
        when(varasto.saldo(41)).thenReturn(11);
        when(varasto.haeTuote(41)).thenReturn(new Tuote(41, "kauramaito", 2));

        // tehdään ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(41);
        kauppa.lisaaKoriin(41);
        kauppa.tilimaksu("tytti", "52341");

        // testataan että pankin metodia kutsutaan kutsutaan oikeilla parametreilla kahden eri tuotteen ostamisen jälkeen
        verify(pankki).tilisiirto(eq("tytti"), anyInt(), eq("52341"), anyString(), eq(4));
    }

    @Test
    public void tyotteenJotaEiOleVarastossaOstamisenJalkeenPankinMetodiaTilisiirtoKutsutaanOIkeillaParamtreilla() {
        // määritellään testissä käytettävät tuotteeet
        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.saldo(4)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 3));
        when(varasto.haeTuote(4)).thenReturn(new Tuote(4, "juusto", 6));

        // tehdään ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.lisaaKoriin(4);
        kauppa.tilimaksu("jonny", "75267");

        // testataan että pankin metodia kutsutaan oikeilla parametreilla tuotteen jota ei ole varastossa ostamisen jälkeen
        verify(pankki).tilisiirto(eq("jonny"), anyInt(), eq("75267"), anyString(), eq(3));
    }
}