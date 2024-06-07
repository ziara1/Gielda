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

    public void wczytajZPliku(String nazwaPliku) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nazwaPliku))) {
            String linia;

            // Wczytaj inwestorów
            linia = reader.readLine();
            String[] typyInwestorów = linia.split(" ");
            for (String typ : typyInwestorów) {
                if (typ.equals("R")) {
                    inwestorzy.add(new LosowyInwestor());
                } else if (typ.equals("S")) {
                    inwestorzy.add(new SMAInwestor());
                }
            }

            // Wczytaj akcje
            linia = reader.readLine();
            String[] informacjeOAckjach = linia.split(" ");
            for (String akcja : informacjeOAckjach) {
                String[] części = akcja.split(":");
                String ticker = części[0];
                int cena = Integer.parseInt(części[1]);
                akcje.put(ticker, new Akcja(ticker, cena));
            }

            // Wczytaj początkowy stan portfeli
            linia = reader.readLine();
            String[] informacjeOPortfelu = linia.split(" ");
            int gotówka = Integer.parseInt(informacjeOPortfelu[0]);
            for (Inwestor inwestor : inwestorzy) {
                inwestor.setGotowka(gotówka);
                for (int i = 1; i < informacjeOPortfelu.length; i++) {
                    String[] części = informacjeOPortfelu[i].split(":");
                    String ticker = części[0];
                    int ilość = Integer.parseInt(części[1]);
                    inwestor.dodajAkcje(ticker, ilość);
                }
            }
        }
    }

    public void uruchom(int liczbaTur) {
        for (aktualnaTura = 0; aktualnaTura < liczbaTur; aktualnaTura++) {
            Collections.shuffle(inwestorzy);
            for (Inwestor inwestor : inwestorzy) {
                inwestor.podejmijDecyzje(this);
            }
            przetworzZlecenia();
        }
    }

    private void przetworzZlecenia() {
        for (Akcja akcja : akcje.values()) {
            akcja.przetworzZlecenia();
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
}
