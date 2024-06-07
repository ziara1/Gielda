public class KolejkaZlecen {
    private Zlecenie head;
    private Zlecenie tail;
    private TypZlecenia typZlecenia;

    public KolejkaZlecen(TypZlecenia typZlecenia) {
        this.tail = new Zlecenie(null, null, null, typZlecenia, 0, 0, 0, 0);
        this.head = new Zlecenie(null, null, this.tail, typZlecenia, 0, 0, 0, 0);
        this.typZlecenia = typZlecenia;
    }

    public void dodajZlecenie(Zlecenie z) {
        if (this.typZlecenia != z.getTypZlecenia()) return;
        Zlecenie temp = this.head;
        while (temp.getNext() != this.tail && z.czyPozniejsze(temp.getNext()))
            temp = temp.getNext();
        z.setNext(temp.getNext());
        temp.setNext(z);
    }
}
