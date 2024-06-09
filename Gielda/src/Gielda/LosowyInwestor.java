package Gielda;

import Main.Symulacja;
import Zlecenia.*;

import java.util.*;

public class LosowyInwestor extends Inwestor {
    private static final Random random = new Random();

    public LosowyInwestor(int gotowka, Map<Akcja, Integer> portfel) {
        super(gotowka, portfel);
    }

    @Override
    public void podejmijDecyzje(Symulacja symulacja) {
        String[] tickery = symulacja.getTickeryAkcji();
        String ticker = tickery[random.nextInt(tickery.length)];
        Akcja akcja = symulacja.getAkcja(ticker);
        int cena = akcja.getOstatniaCena();
        int limitCeny = cena + random.nextInt(21) - 10;
        int typ = random.nextInt(0, 4);
        int tura = symulacja.getAktualnaTura();
        int kolejnosc = symulacja.getAktualnaKolejnosc();

        if (random.nextBoolean()) {
            int ilosc = random.nextInt(10) + 1;
            if (gotowka >= limitCeny * ilosc) {
                zlozZlecenie(akcja, TypZlecenia.KUPNO, limitCeny, ilosc, tura, kolejnosc, typ, symulacja);
            }
        } else {
            if (portfel.get(akcja) > 0) {
                int ilosc = random.nextInt(portfel.get(akcja)) + 1;
                if (ilosc <= super.ileAkcji(akcja)) {
                    zlozZlecenie(akcja, TypZlecenia.SPRZEDAZ, limitCeny, ilosc, tura, kolejnosc, typ, symulacja);
                }
            }
        }
    }
}
// zrobic metode zloz zlecenie w inwestorze

