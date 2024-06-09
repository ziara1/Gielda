package Zlecenia;

import Gielda.Akcja;
import Gielda.Inwestor;

import static java.lang.Math.abs;

public class Zlecenie {
    private TypZlecenia typZlecenia;
    private int limitCeny;
    private int ilosc;
    private Inwestor inwestor;
    private Akcja akcja;
    private int tura;
    private int terminWaznosci;
    private Zlecenie next;
    private int kolejnosc;

    public Zlecenie(Inwestor inwestor, Akcja akcja, Zlecenie next,
                    TypZlecenia typZlecenia, int limitCeny, int ilosc,
                    int tura, int terminWaznosci, int kolejnosc){
        this.typZlecenia = typZlecenia;
        this.limitCeny = limitCeny;
        this.ilosc = ilosc;
        this.inwestor = inwestor;
        this.akcja = akcja;
        this.tura = tura;
        this.terminWaznosci = terminWaznosci;
        this.kolejnosc = kolejnosc;
        this.next = next;
    }

    public int getLimitCeny() {
        return limitCeny;
    }

    public int getIlosc() {
        return ilosc;
    }

    public Inwestor getInwestor() {
        return inwestor;
    }

    private void zmniejszIlosc(int ilosc) {
        this.ilosc -= ilosc;
    }

    public Akcja getAkcja() {
        return akcja;
    }

    public TypZlecenia getTypZlecenia() {
        return typZlecenia;
    }

    public int getTura(){
        return tura;
    }

    public Zlecenie getNext() {
        return next;
    }

    public void setNext(Zlecenie next) {
        this.next = next;
    }

    public int getKolejnosc() {
        return kolejnosc;
    }

    public int getTerminWaznosci() {
        return terminWaznosci;
    }

    public void usunNastepne() {
//        System.out.println("usunieto " + this.getNext());
        this.next = this.next.getNext();
    }

    // przetwarza dane zlecenie this (przetwarza je z pozostałymi zleceniami)
    // jeśli to, z którym je przetwarza jest zleceniem wykonaj lub anuluj, to
    // musi się je dać przetworzyć w całości
    public void przetworz(Zlecenie z, int aktualnaTura) {
        while (this.getIlosc() != 0 && z.getNext() != null) {
            Zlecenie kupno = this;
            Zlecenie sprzedaz = z;
            if (this.typZlecenia == TypZlecenia.SPRZEDAZ) {
                kupno = z;
                sprzedaz = this;
            } // jeśli cena sprzedaży jest wyższa, to nie dojdzie do transakcji
            if (kupno.getLimitCeny() >= sprzedaz.getLimitCeny()) {
                Inwestor kupiec = kupno.getInwestor();
                Inwestor sprzedawca = sprzedaz.getInwestor();
                int cenaTransakcji = this.getLimitCeny();
                if (this.czyPozniejsze(z)) // liczy się limit wcześniejszego
                    cenaTransakcji = z.getLimitCeny();
                int iloscTransakcji = Math.min(Math.min(kupno.getIlosc(),
                        sprzedaz.getIlosc()), Math.min(sprzedawca.ileAkcji
                        (akcja), kupiec.getGotowka() / cenaTransakcji));
                if (!(z instanceof ZlecenieWykonajLubAnuluj) ||
                        iloscTransakcji >= z.getIlosc()) {
                    this.zmniejszIlosc(iloscTransakcji);
                    z.zmniejszIlosc(iloscTransakcji);
                    kupiec.dodajGotowke(-cenaTransakcji * iloscTransakcji);
                    sprzedawca.dodajGotowke(cenaTransakcji * iloscTransakcji);
                    kupiec.dodajAkcje(akcja, iloscTransakcji);
                    sprzedawca.dodajAkcje(akcja, -iloscTransakcji);
                    akcja.setOstatniaCena(cenaTransakcji);
                }
            }
            if (z.getIlosc() == 0)
                z.getAkcja().usunZlecenie(z);
            z = z.getNext();
        }
        if (this.getIlosc() == 0 || this.getTerminWaznosci() <= aktualnaTura)
            this.getAkcja().usunZlecenie(this);
    }

    // czy this jest pozniej w kolejności od z
    public boolean czyPozniejsze(Zlecenie z){
        boolean result = false;
        if (this.typZlecenia == z.getTypZlecenia()) {
            if (this.typZlecenia == TypZlecenia.SPRZEDAZ)
                result = true;
            if (this.limitCeny < z.getLimitCeny())
                return !result;
            if (this.limitCeny > z.getLimitCeny())
                return result;
        }
        if (this.tura == z.getTura())
            return this.kolejnosc > z.getKolejnosc();
        return this.tura > z.getTura();
    }

    @Override
    public String toString(){
        return typZlecenia.toString() + " " + limitCeny + " " + ilosc + " " + akcja.toString() +
                " inwestor:  " + inwestor.toString();
    }
}


