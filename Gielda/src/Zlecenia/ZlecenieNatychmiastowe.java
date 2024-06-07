package Zlecenia;

import Gielda.Akcja;
import Gielda.Inwestor;

public class ZlecenieNatychmiastowe extends Zlecenie {

    public ZlecenieNatychmiastowe(Inwestor inwestor, Akcja akcja, Zlecenie next, TypZlecenia typZlecenia,
                                  int limitCeny, int ilosc, int tura, int kolejnosc) {
        super(inwestor, akcja, next, typZlecenia, limitCeny, ilosc, tura, tura, kolejnosc);

    }
    public void przetworz(){

    }
}
