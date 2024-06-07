public class ZlecenieNatychmiastowe extends Zlecenie{

    public ZlecenieNatychmiastowe(Inwestor inwestor, Akcja akcja, Zlecenie next, TypZlecenia typZlecenia,
                                  int limitCeny, int ilosc, int tura) {
        super(inwestor, akcja, next, typZlecenia, limitCeny, ilosc, tura, tura);

    }
    public void przetworz(){

    }
}
