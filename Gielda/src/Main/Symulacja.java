package Main;

import Gielda.Akcja;
import Gielda.Inwestor;
import Gielda.LosowyInwestor;
import Gielda.SMAInwestor;

import java.io.*;
import java.util.*;

public class Symulacja {
    private List<Inwestor> inwestorzy = new ArrayList<>();
    private Map<String, Akcja> akcje = new HashMap<>();
    private int aktualnaTura = 0;
    private int aktualnaKolejnosc = 0;

    public void wczytajZPliku(String input) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String linia;
            linia = reader.readLine();
            while (linia.trim().startsWith("#")) // pomija komentarze
                linia = reader.readLine();
            wczytajInwestorow(linia);

            linia = reader.readLine();
            while (linia.trim().startsWith("#"))
                linia = reader.readLine();
            wczytajAkcje(linia);

            linia = reader.readLine();
            while (linia.trim().startsWith("#"))
                linia = reader.readLine();
            wczytajPortfel(linia);

        }
    }

    private void wczytajInwestorow(String linia) throws IOException {
        String[] typyInwestorow = linia.split(" ");
        for (String typ : typyInwestorow) {
            if (typ.equals("R"))
                inwestorzy.add(new LosowyInwestor(0, new HashMap<>()));
            else if (typ.equals("S"))
                inwestorzy.add(new SMAInwestor(0, new HashMap<>()));
        }
    }
    private void wczytajAkcje(String linia) throws IOException {
        System.out.println(linia);
        String[] informacjeOAkcjach = linia.split(" ");
        for (String akcja : informacjeOAkcjach) {
            String[] czesci = akcja.split(":");
            String ticker = czesci[0];
            int cena = Integer.parseInt(czesci[1]);
            akcje.put(ticker, new Akcja(ticker, cena));
        }
    }
    private void wczytajPortfel(String linia) throws IOException {
        String[] informacjeOPortfelu = linia.split(" ");
        int gotowka = Integer.parseInt(informacjeOPortfelu[0]);
        for (Inwestor inwestor : inwestorzy) {
            inwestor.dodajGotowke(gotowka);
            for (int i = 1; i < informacjeOPortfelu.length; i++) {
                String[] czesci = informacjeOPortfelu[i].split(":");
                Akcja akcja = getAkcja(czesci[0]);
                int ilosc = Integer.parseInt(czesci[1]);
                inwestor.dodajAkcje(akcja, ilosc);
            }
        }
    }

    public void uruchom(int liczbaTur) {
        for (aktualnaTura = 0; aktualnaTura < liczbaTur; aktualnaTura++) {
            aktualnaKolejnosc = 0;
            Collections.shuffle(inwestorzy);
            for (Inwestor inwestor : inwestorzy) {
                inwestor.podejmijDecyzje(this);
            }
            przetworzZlecenia(aktualnaTura);
        }
    }

    private void przetworzZlecenia(int aktualnaTura) {
        for (Akcja akcja : akcje.values()) {
            akcja.przetworzZlecenia(aktualnaTura);
        }
    }

    public void wypiszWyniki() {
        for (Inwestor inwestor : inwestorzy) {
            System.out.println(inwestor);
        }
    }

    public int getAktualnaTura() {
        return aktualnaTura;
    }

    public Akcja getAkcja(String ticker) {
        return akcje.get(ticker);
    }

    public String[] getTickeryAkcji() {
        return akcje.keySet().toArray(new String[0]);
    }

    public int getAktualnaKolejnosc(){
        return aktualnaKolejnosc;
    }

    public void zwiekszKolejnosc(){
        aktualnaKolejnosc++;
    }
}
