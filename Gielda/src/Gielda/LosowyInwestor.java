package Gielda;

import Main.Symulacja;
import Zlecenia.*;

import java.util.*;

public class LosowyInwestor extends Inwestor {
    private static final Random random = new Random();

    public LosowyInwestor(int gotowka, Map<Akcja, Integer> portfel) {
        super(gotowka, portfel);
    }

    // wybiera losową akcję, następnie losuje jej typ i cenę (na przedziale
    // podanym w zadaniu), a następnie składa zlecenie
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
        // losuje czy zlecenie sprzedaży czy kupna
        if (random.nextBoolean()) {
            int ilosc = random.nextInt(10) + 1;
            if (this.getGotowka() >= limitCeny * ilosc) {
                zlozZlecenie(akcja, TypZlecenia.KUPNO, limitCeny,
                        ilosc, tura, random.nextInt(tura, tura + 11),
                        kolejnosc, typ, symulacja);
            }
        } else {
            if (this.getPortfel().get(akcja) > 0) {
                int ilosc = random.nextInt(this.getPortfel().get(akcja)) + 1;
                if (ilosc <= super.ileAkcji(akcja)) {
                    zlozZlecenie(akcja, TypZlecenia.SPRZEDAZ, limitCeny,
                            ilosc, tura, random.nextInt(tura, tura + 11),
                            kolejnosc, typ, symulacja);
                }
            }
        }
    }
}

