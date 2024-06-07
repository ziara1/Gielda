package Main;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Użycie: java Główna <plik_wejściowy> <liczba_tur>");
            return;
        }

        String plikWejściowy = args[0];
        int liczbaTur = Integer.parseInt(args[1]);

        Symulacja symulacja = new Symulacja();
        try {
            symulacja.wczytajZPliku(plikWejściowy);
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku wejściowego: " + e.getMessage());
            return;
        }

        symulacja.uruchom(liczbaTur);
        symulacja.wypiszWyniki();
    }
}
