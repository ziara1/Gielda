package Zlecenia;

import static java.lang.Integer.MAX_VALUE;

public class KolejkaZlecen {
    private Zlecenie head;
    private Zlecenie tail;
    private TypZlecenia typZlecenia;

    public KolejkaZlecen(TypZlecenia typZlecenia) {
        this.tail = new Zlecenie(
                null, null, null, typZlecenia, 0, 0, 0, MAX_VALUE, 0);
        this.head = new Zlecenie(
                null, null, this.tail, typZlecenia, 0, 0, 0, MAX_VALUE, 0);
        this.typZlecenia = typZlecenia;
    }

    public void dodajZlecenie(Zlecenie z) {
        if (this.typZlecenia != z.getTypZlecenia()) return;
        Zlecenie temp = this.head;
        while (temp.getNext() != this.tail && z.czyPozniejsze(temp.getNext()))
            temp = temp.getNext();
        z.setNext(temp.getNext());
        temp.setNext(z);
  //      System.out.println("dodano " + z);
    }

    public void usunZlecenie(Zlecenie z){
        Zlecenie temp = head;
        while (temp.getNext() != z && temp.getNext() != tail)
            temp = temp.getNext();
        if (temp.getNext() == z)
            temp.usunNastepne();
    }

    public Zlecenie getHead() {
        return this.head;
    }

    public Zlecenie getTail() {
        return this.tail;
    }

}
