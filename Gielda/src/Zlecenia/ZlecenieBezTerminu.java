package Zlecenia;

import Gielda.Akcja;
import Gielda.Inwestor;

import static java.lang.Integer.MAX_VALUE;

public class ZlecenieBezTerminu extends Zlecenie {

    public ZlecenieBezTerminu(Inwestor inwestor, Akcja akcja, Zlecenie next, TypZlecenia typZlecenia,
                              int limitCeny, int ilosc, int tura) {
        super(inwestor, akcja, next, typZlecenia, limitCeny, ilosc, tura, MAX_VALUE);

    }
    public void przetworz(){

    }
}
