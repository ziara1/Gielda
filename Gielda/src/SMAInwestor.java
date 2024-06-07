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
            int limitCeny = cena + random.nextInt(21) - 10;

            if (smaKrotki < smaDlugi && portfel.get(ticker) > 0) {
                akcja.dodajZlecenieSprzedazy(new Zlecenie(TypZlecenia.SPRZEDAZ, limitCeny, portfel.get(ticker), this));
            } else if (smaKrotki > smaDlugi && gotowka >= limitCeny) {
                akcja.dodajZlecenieKupna(new Zlecenie(TypZlecenia.KUPNO, limitCeny, gotowka / limitCeny, this));
            }
        }
    }
}


/*

 @Override
    public void podejmijDecyzje(Symulacja symulacja) {

        for (String ticker : portfel.keySet()) {
            Akcja akcja = symulacja.getAkcja(ticker);
            if (akcja.getLicznikTur() >= SMA_DLUGI) {
                double smaKrotki = akcja.obliczSMA(SMA_KROTKI);
                double smaDlugi = akcja.obliczSMA(SMA_DLUGI);

                int cena = akcja.getOstatniaCena();
                int limitCeny = cena + random.nextInt(21) - 10;

                if (smaKrotki > smaDlugi && portfel.get(ticker) > 0) {
                    akcja.dodajZlecenieSprzedazy(new Zlecenie(TypZlecenia.SPRZEDAZ, limitCeny, portfel.get(ticker), this));
                } else if (smaKrotki < smaDlugi && gotowka >= limitCeny) {
                    akcja.dodajZlecenieKupna(new Zlecenie(TypZlecenia.KUPNO, limitCeny, gotowka / limitCeny, this));
                }
            }
        }
    }
 */