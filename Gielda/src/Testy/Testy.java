package Testy;

import Gielda.*;
import Main.Symulacja;
import Zlecenia.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Testy {
    @Test
    public void testy() {
        Akcja akcja = new Akcja("ABC", 100);

        Map<Akcja, Integer> portfel = new HashMap<>();
        portfel.put(akcja, 100);
        Inwestor inwestor = new LosowyInwestor(1000, portfel);

        assertEquals(100, akcja.getOstatniaCena());
        akcja.setOstatniaCena(200);
        assertEquals(200, akcja.getOstatniaCena());
        assertEquals(1, akcja.getLicznikTur());
        Zlecenie zlecenie1 = new ZlecenieBezTerminu(inwestor, akcja, null, TypZlecenia.KUPNO, 100, 10, 1, 1);
        akcja.dodajZlecenie(zlecenie1);
        assertEquals(zlecenie1, akcja.getZleceniaKupna().getHead().getNext());
        Zlecenie zlecenie4 = new ZlecenieNatychmiastowe(inwestor, akcja, null, TypZlecenia.SPRZEDAZ, 100, 10, 1, 1);
        akcja.dodajZlecenie(zlecenie4);
        assertEquals(zlecenie4, akcja.getZleceniaSprzedazy().getHead().getNext());

        Zlecenie zlecenie3 = new ZlecenieBezTerminu(inwestor, akcja, null, TypZlecenia.KUPNO, 100, 10, 1, 1);
        akcja.dodajZlecenie(zlecenie3);
        akcja.usunZlecenie(zlecenie3);
        //assertNull(akcja.getZleceniaKupna().getHead().getNext().getNext());

        for (int i = 0; i < 5; i++){
            akcja.setOstatniaCena(100 + i * 10);
            akcja.przetworzZlecenia(i);
        }
        assertEquals(120.0, akcja.obliczSMA(5));

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
        assertEquals(105, inwestor.ileAkcji(akcja));

        Symulacja symulacja = new Symulacja();
        inwestor.zlozZlecenie(akcja, TypZlecenia.KUPNO, 100, 5, 1, 20, 1, 0, symulacja);
        assertEquals(100, akcja.getZleceniaKupna().getHead().getNext().getLimitCeny());


    }
}