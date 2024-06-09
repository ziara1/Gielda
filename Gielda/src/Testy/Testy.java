package Testy;

import Gielda.*;
import Main.Symulacja;
import Zlecenia.*;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Testy {
    @Test
    public void testy() {
        Akcja akcja = new Akcja("ABC", 100);

        assertEquals(100, akcja.getOstatniaCena());
        akcja.setOstatniaCena(200);
        assertEquals(200, akcja.getOstatniaCena());
        assertEquals(1, akcja.getLicznikTur());
        Zlecenie zlecenie1 = new ZlecenieBezTerminu(null, akcja, null, TypZlecenia.KUPNO, 100, 10, 1, 1);
        akcja.dodajZlecenie(zlecenie1);
        assertEquals(zlecenie1, akcja.getZleceniaKupna().getHead().getNext());
        Zlecenie zlecenie4 = new ZlecenieNatychmiastowe(null, akcja, null, TypZlecenia.SPRZEDAZ, 100, 10, 1, 1);
        akcja.dodajZlecenie(zlecenie4);
        assertEquals(zlecenie4, akcja.getZleceniaSprzedazy().getHead().getNext());

        Zlecenie zlecenie3 = new ZlecenieBezTerminu(null, akcja, null, TypZlecenia.KUPNO, 100, 10, 1, 1);
        akcja.dodajZlecenie(zlecenie3);
        akcja.usunZlecenie(zlecenie3);
        assertNull(akcja.getZleceniaKupna().getHead().getNext());

        akcja.setOstatniaCena(110);
        akcja.setOstatniaCena(120);
        akcja.setOstatniaCena(130);
        akcja.setOstatniaCena(140);
        akcja.setOstatniaCena(150);
        assertEquals(130.0, akcja.obliczSMA(5));


        Map<Akcja, Integer> portfel = new HashMap<>();
        Inwestor inwestor = new LosowyInwestor(1000, portfel);
        Zlecenie zlecenie2 = new Zlecenie(inwestor, akcja, null, TypZlecenia.KUPNO, 123, 10, 1, 11, 100);

        assertEquals(TypZlecenia.KUPNO, zlecenie2.getTypZlecenia());
        assertEquals(akcja, zlecenie2.getAkcja());
        assertEquals(10, zlecenie2.getIlosc());
        assertEquals(123, zlecenie2.getLimitCeny());
        assertEquals(inwestor, zlecenie2.getInwestor());
        assertEquals(1, zlecenie2.getTura());
        assertEquals(11, zlecenie2.getTerminWaznosci());
        assertEquals(100, zlecenie2.getKolejnosc());

        inwestor.dodajGotowke(500);
        assertEquals(1500, inwestor.getGotowka());
        inwestor.dodajAkcje(akcja, 5);
        assertEquals(15, inwestor.ileAkcji(akcja));

        Symulacja symulacja = new Symulacja();
        inwestor.zlozZlecenie(akcja, TypZlecenia.KUPNO, 100, 5, 1, 1, 0, symulacja);
        assertEquals(100, akcja.getZleceniaKupna().getHead().getNext().getLimitCeny());


    }
}