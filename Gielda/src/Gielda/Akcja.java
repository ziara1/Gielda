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
        zleceniaKupna = new KolejkaZlecen(TypZlecenia.KUPNO);
        zleceniaSprzedazy = new KolejkaZlecen(TypZlecenia.SPRZEDAZ);
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

    public KolejkaZlecen getZleceniaKupna() {
        return zleceniaKupna;
    }

    public KolejkaZlecen getZleceniaSprzedazy() {
        return zleceniaSprzedazy;
    }

    public void dodajZlecenie(Zlecenie zlecenie) {
        if (zlecenie.getTypZlecenia() == TypZlecenia.SPRZEDAZ)
            zleceniaSprzedazy.dodajZlecenie(zlecenie);
        else
            zleceniaKupna.dodajZlecenie(zlecenie);
    }

    public void przetworzZlecenia(int aktualnaTura) {
        Zlecenie buyPtr = zleceniaKupna.getHead().getNext();
        Zlecenie sellPtr = zleceniaSprzedazy.getHead().getNext();
        while (buyPtr != zleceniaKupna.getTail() && sellPtr != zleceniaSprzedazy.getTail()) {
            if (buyPtr.czyPozniejsze(sellPtr)){
                sellPtr.przetworz(zleceniaKupna.getHead().getNext(), aktualnaTura);
                sellPtr = sellPtr.getNext();
            }
            else {
                buyPtr.przetworz(zleceniaSprzedazy.getHead().getNext(), aktualnaTura);
                buyPtr = buyPtr.getNext();
            }
        }
        while (buyPtr != zleceniaKupna.getTail()) {
            buyPtr.przetworz(zleceniaSprzedazy.getHead().getNext(), aktualnaTura);
            buyPtr = buyPtr.getNext();
        }
        while (sellPtr != zleceniaSprzedazy.getTail()) {
            sellPtr.przetworz(zleceniaKupna.getHead().getNext(), aktualnaTura);
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

    public void usunZlecenie(Zlecenie z){
        if (z.getTypZlecenia() == TypZlecenia.KUPNO)
            zleceniaKupna.usunZlecenie(z);
        else
            zleceniaSprzedazy.usunZlecenie(z);
    }

    @Override
    public String toString(){
        return ticker;
    }

}
