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
        //int limitCeny = cena + random.nextInt(cena / 5) - cena / 10;
        int typ = random.nextInt(0, 4);
        int tura = symulacja.getAktualnaTura();
        int kolejnosc = symulacja.getAktualnaKolejnosc();

        if (random.nextBoolean()) {
            int ilosc = random.nextInt(10) + 1;
            if (gotowka >= limitCeny * ilosc) {
                // zamrazam srodki
                this.dodajGotowke( -limitCeny * ilosc);
                if (typ == 0)
                    akcja.dodajZlecenieKupna(new ZlecenieNatychmiastowe(this, akcja, null, TypZlecenia.KUPNO,
                            limitCeny, ilosc, tura, kolejnosc));
                else if (typ == 1)
                    akcja.dodajZlecenieKupna(new ZlecenieWykonajLubAnuluj(this, akcja, null, TypZlecenia.KUPNO,
                            limitCeny, ilosc, tura, kolejnosc));
                else if (typ == 2)
                    akcja.dodajZlecenieKupna(new ZlecenieBezTerminu(this, akcja, null, TypZlecenia.KUPNO,
                            limitCeny, ilosc, tura, kolejnosc));
                else
                    akcja.dodajZlecenieKupna(new ZlecenieDoKoncaTury(this, akcja, null, TypZlecenia.KUPNO,
                            limitCeny, ilosc, tura, random.nextInt(tura, tura + 11), kolejnosc));
                symulacja.zwiekszKolejnosc();
            }
        } else {
            if (portfel.get(akcja) > 0) {
                int ilosc = random.nextInt(portfel.get(akcja)) + 1;
                if (ilosc <= super.ileAkcji(akcja)) {
                    this.dodajAkcje(akcja, -ilosc);
                    if (typ == 0)
                        akcja.dodajZlecenieSprzedazy(new ZlecenieNatychmiastowe(this, akcja, null, TypZlecenia.SPRZEDAZ,
                                limitCeny, ilosc, tura, kolejnosc));
                    else if (typ == 1)
                        akcja.dodajZlecenieSprzedazy(new ZlecenieWykonajLubAnuluj(this, akcja, null, TypZlecenia.SPRZEDAZ,
                                limitCeny, ilosc, tura, kolejnosc));
                    else if (typ == 2)
                        akcja.dodajZlecenieSprzedazy(new ZlecenieBezTerminu(this, akcja, null, TypZlecenia.SPRZEDAZ,
                                limitCeny, ilosc, tura, kolejnosc));
                    else
                        akcja.dodajZlecenieSprzedazy(new ZlecenieDoKoncaTury(this, akcja, null, TypZlecenia.SPRZEDAZ,
                                limitCeny, ilosc, tura, random.nextInt(tura, tura + 11), kolejnosc));
                    symulacja.zwiekszKolejnosc();
                }
            }
        }
    }
}
// zrobic metode zloz zlecenie w inwestorze

