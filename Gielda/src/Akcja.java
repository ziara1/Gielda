import java.util.*;

public class Akcja {
    private String ticker;
    private int ostatniaCena;
    private List<Integer> cenyHistoria = new ArrayList<>();
    //private PriorityQueue<Zlecenie> zleceniaKupna = new LinkedList<>();
    //private PriorityQueue<Zlecenie> zleceniaSprzedazy = new LinkedList<>();
    private KolejkaZlecen zleceniaKupna = new KolejkaZlecen(TypZlecenia.KUPNO);
    private KolejkaZlecen zleceniaSprzedazy = new KolejkaZlecen(TypZlecenia.SPRZEDAZ);

    public Akcja(String ticker, int cena) {
        this.ticker = ticker;
        this.ostatniaCena = cena;
        cenyHistoria.add(cena);
    }

    public int getOstatniaCena() {
        return ostatniaCena;
    }

    public int getLicznikTur() {
        return cenyHistoria.size();
    }

    public void dodajZlecenieKupna(Zlecenie zlecenie) {
        zleceniaKupna.dodajZlecenie(zlecenie);
    }

    public void dodajZlecenieSprzedazy(Zlecenie zlecenie) {
        zleceniaSprzedazy.dodajZlecenie(zlecenie);
    }

    public void przetworzZlecenia() {
// TODO
        cenyHistoria.add(ostatniaCena);
    }

    public double obliczSMA(int n) {
        int suma = 0;
        for (int i = cenyHistoria.size() - n; i < cenyHistoria.size(); i++) {
            suma += cenyHistoria.get(i);
        }
        return suma / (double) n;
    }
}
