package Zlecenia;

import Gielda.Akcja;
import Gielda.Inwestor;

public class ZlecenieWykonajLubAnuluj extends Zlecenie {

    public ZlecenieWykonajLubAnuluj(Inwestor inwestor, Akcja akcja, Zlecenie next, TypZlecenia typZlecenia,
                                    int limitCeny, int ilosc, int tura, int kolejnosc) {
        super(inwestor, akcja, next, typZlecenia, limitCeny, ilosc, tura, tura, kolejnosc);


    }
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

                int cenaTransakcji = this.getLimitCeny();
                if (this.czyPozniejsze(z))
                    cenaTransakcji = z.getLimitCeny();

                int iloscTransakcji = Math.min(Math.min(kupno.getIlosc(), sprzedaz.getIlosc()),
                        Math.min(sprzedaz.getInwestor().ileAkcji(this.getAkcja()), kupno.getInwestor().getGotowka() / cenaTransakcji));

                ilosc -= iloscTransakcji;
            }
            z = z.getNext();
        }
        if (ilosc == 0)
            super.przetworz(z, aktualnaTura);
        else
            this.getAkcja().usunZlecenie(this);
    }
}
