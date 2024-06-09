package Gielda;

import Main.Symulacja;
import Zlecenia.*;

import java.util.*;

public class SMAInwestor extends Inwestor {
    private static final int SMA_KROTKI = 5;
    private static final int SMA_DLUGI = 10;
    private static final Random random = new Random();

    public SMAInwestor(int gotowka, Map<Akcja, Integer> portfel) {
        super(gotowka, portfel);
    }

    @Override
    public void podejmijDecyzje(Symulacja symulacja) {
        String[] tickery = symulacja.getTickeryAkcji();
        String ticker = tickery[random.nextInt(tickery.length)];
        Akcja akcja = symulacja.getAkcja(ticker);
        if (akcja.getLicznikTur() >= SMA_DLUGI) {
            double smaKrotki = akcja.obliczSMA(SMA_KROTKI);
            double smaDlugi = akcja.obliczSMA(SMA_DLUGI);

            int cena = akcja.getOstatniaCena();
            int tura = symulacja.getAktualnaTura();
            int limitCeny = cena + random.nextInt(21) - 10;
            int typ = random.nextInt(0, 4);
            int kolejnosc = symulacja.getAktualnaKolejnosc();

            if (smaKrotki < smaDlugi) {
                int ilosc = random.nextInt(portfel.getOrDefault(ticker + 1, 1)) + 1;
                if (ilosc <= super.ileAkcji(akcja)) {
                    zlozZlecenie(akcja, TypZlecenia.SPRZEDAZ, limitCeny, ilosc, tura, kolejnosc, typ, symulacja);
                }

            } else if (smaKrotki > smaDlugi) {
                int ilosc = random.nextInt(10) + 1;
                if (gotowka >= limitCeny * ilosc) {
                    zlozZlecenie(akcja, TypZlecenia.KUPNO, limitCeny, ilosc, tura, kolejnosc, typ, symulacja);
                }
            }
        }
    }
}
