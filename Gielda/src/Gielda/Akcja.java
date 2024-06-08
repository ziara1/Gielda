package Gielda;

import Zlecenia.KolejkaZlecen;
import Zlecenia.TypZlecenia;
import Zlecenia.Zlecenie;

import java.util.*;

public class Akcja {
    private String ticker;
    private int ostatniaCena;
    private List<Integer> cenyHistoria = new ArrayList<>();
    //private PriorityQueue<Zlecenia.Zlecenie> zleceniaKupna = new LinkedList<>();
    //private PriorityQueue<Zlecenia.Zlecenie> zleceniaSprzedazy = new LinkedList<>();
    private KolejkaZlecen zleceniaKupna = new KolejkaZlecen(TypZlecenia.KUPNO);
    private KolejkaZlecen zleceniaSprzedazy = new KolejkaZlecen(TypZlecenia.SPRZEDAZ);

    public Akcja(String ticker, int cena) {
        this.ticker = ticker;
        this.ostatniaCena = cena;
        cenyHistoria.add(cena);
    }

    public int getOstatniaCena() {
        return ostatniaCena;
    }

    public void setOstatniaCena(int ostatniaCena) {
        this.ostatniaCena = ostatniaCena;
    }

    public int getLicznikTur() {
        return cenyHistoria.size();
    }

    public void dodajZlecenieKupna(Zlecenie zlecenie) {
        zleceniaKupna.dodajZlecenie(zlecenie);
    }

    public void dodajZlecenieSprzedazy(Zlecenie zlecenie) {
        zleceniaSprzedazy.dodajZlecenie(zlecenie);
    }

    public void przetworzZlecenia() {
        Zlecenie buyPtr = zleceniaKupna.getHead().getNext();
        Zlecenie sellPtr = zleceniaSprzedazy.getHead().getNext();
        while (buyPtr != zleceniaKupna.getTail() && sellPtr != zleceniaSprzedazy.getTail()) {
            if (buyPtr.czyPozniejsze(sellPtr)){
                sellPtr.przetworz(zleceniaKupna.getHead().getNext());
                sellPtr = sellPtr.getNext();
            }
            else {
                buyPtr.przetworz(zleceniaSprzedazy.getHead().getNext());
                buyPtr = buyPtr.getNext();
            }
        }
        while (buyPtr != zleceniaKupna.getTail()) {
            buyPtr.przetworz(zleceniaSprzedazy.getHead().getNext());
            buyPtr = buyPtr.getNext();
        }
        while (sellPtr != zleceniaSprzedazy.getTail()) {
            sellPtr.przetworz(zleceniaKupna.getHead().getNext());
            sellPtr = sellPtr.getNext();
        }
        cenyHistoria.add(ostatniaCena);
    }

    public double obliczSMA(int n) {
        int suma = 0;
        for (int i = cenyHistoria.size() - n; i < cenyHistoria.size(); i++) {
            suma += cenyHistoria.get(i);
        }
        return suma / (double) n;
    }

}
