package Zlecenia;

import Gielda.Akcja;
import Gielda.Inwestor;

public class ZlecenieWykonajLubAnuluj extends Zlecenie {

    public ZlecenieWykonajLubAnuluj(Inwestor inwestor, Akcja akcja, Zlecenie next, TypZlecenia typZlecenia,
                                    int limitCeny, int ilosc, int tura) {
        super(inwestor, akcja, next, typZlecenia, limitCeny, ilosc, tura, tura);


    }
    public void przetworz(){

    }
}
