package Zlecenia;

import Gielda.Akcja;
import Gielda.Inwestor;

public class ZlecenieDoKoncaTury extends Zlecenie {

    public ZlecenieDoKoncaTury(Inwestor inwestor, Akcja akcja, Zlecenie next, TypZlecenia typZlecenia,
                               int limitCeny, int ilosc, int tura, int terminWaznosci) {
        super(inwestor, akcja, next, typZlecenia, limitCeny, ilosc, tura, terminWaznosci);


    }
    public void przetworz(){

    }
}
