package Zlecenia;

import Gielda.Akcja;
import Gielda.Inwestor;

public class ZlecenieWykonajLubAnuluj extends Zlecenie {

    public ZlecenieWykonajLubAnuluj(Inwestor inwestor, Akcja akcja,
                                    Zlecenie next, TypZlecenia typZlecenia,
                                    int limitCeny, int ilosc,
                                    int tura, int kolejnosc) {
        super(inwestor, akcja, next, typZlecenia,
                limitCeny, ilosc, tura, tura, kolejnosc);
    }

    // sprawdza czy da się to zlecenie przetworzyć w całości
    // jeśli tak, to przetwarza je normalnie
    // jeśli nie, to usuwa z kolejki
    @Override
    public void przetworz(Zlecenie z, int aktualnaTura){
        int ilosc = this.getIlosc();
        while (ilosc != 0 && z.getNext() != null) {
            Zlecenie kupno = this;
            Zlecenie sprzedaz = z;
            if (this.getTypZlecenia() == TypZlecenia.SPRZEDAZ) {
                kupno = z;
                sprzedaz = this;
            }
            if (kupno.getLimitCeny() >= sprzedaz.getLimitCeny()) {
                Inwestor kupiec = kupno.getInwestor();
                Inwestor sprzedawca = sprzedaz.getInwestor();
                int cenaTransakcji = this.getLimitCeny();
                if (this.czyPozniejsze(z))
                    cenaTransakcji = z.getLimitCeny();
                int iloscTransakcji = Math.min(Math.min(kupno.getIlosc(),
                        sprzedaz.getIlosc()), Math.min(sprzedawca.ileAkcji(this
                        .getAkcja()), kupiec.getGotowka() / cenaTransakcji));

                ilosc -= iloscTransakcji;
            }
            z = z.getNext();
        }
        if (ilosc == 0) // jeśli tak, to zlecenie można przetworzyć w calości
            super.przetworz(z, aktualnaTura);
        else
            this.getAkcja().usunZlecenie(this);
    }
}
