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

            } else if (smaKrotki > smaDlugi) {
                int ilosc = random.nextInt(10) + 1;
                if (gotowka >= limitCeny * ilosc) {
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
            }
        }
    }
}
