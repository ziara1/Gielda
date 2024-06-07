public class Zlecenie {
    private TypZlecenia typZlecenia;
    private int limitCeny;
    private int ilosc;
    private Inwestor inwestor;
    private Akcja akcja;
    private int tura;
    private int terminWaznosci;
    private Zlecenie next;

    public Zlecenie(Inwestor inwestor, Akcja akcja, Zlecenie next, TypZlecenia typZlecenia,
                    int limitCeny, int ilosc, int tura, int terminWaznosci){
        this.typZlecenia = typZlecenia;
        this.limitCeny = limitCeny;
        this.ilosc = ilosc;
        this.inwestor = inwestor;
        this.akcja = akcja;
        this.tura = tura;
        this.terminWaznosci = terminWaznosci;
    }

    public int getLimitCeny() {
        return limitCeny;
    }

    public int getIlosc() {
        return ilosc;
    }

    public Inwestor getInwestor() {
        return inwestor;
    }

    public void zmniejszIlosc(int ilosc) {
        this.ilosc -= ilosc;
    }

    public Akcja getAkcja() {
        return akcja;
    }

    public TypZlecenia getTypZlecenia() {
        return typZlecenia;
    }

    public int getTura(){
        return tura;
    }

    public Zlecenie getNext() {
        return next;
    }

    public void setNext(Zlecenie next) {
        this.next = next;
    }

    public void przetworz(){
    }

    // czy this jest pozniej w kolejce od z
    public boolean czyPozniejsze(Zlecenie z){
        boolean result = false;
        if (this.typZlecenia == TypZlecenia.SPRZEDAZ){
            result = true;
        }

        if (this.limitCeny < z.getLimitCeny())
            result = !result;

        if (this.limitCeny == z.getLimitCeny()){
            if (this.tura > z.getTura())
                return true;
            if (this.tura < z.getTura())
                return false;
            return true;
        }

        return result;
    }
}
