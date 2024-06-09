package Gielda;

import Main.Symulacja;
import Zlecenia.*;

import java.util.Map;
import java.util.Random;

public abstract class Inwestor {
    protected int gotowka;
    protected Map<Akcja, Integer> portfel;
    private static final Random random = new Random();

    public Inwestor(int gotowka, Map<Akcja, Integer> portfel) {
        this.gotowka = gotowka;
        this.portfel = portfel;
    }

    public int getGotowka() {
        return gotowka;
    }

    public void dodajGotowke(int gotowka) {
        this.gotowka += gotowka;
    }

    public Map<Akcja, Integer> getPortfel() {
        return portfel;
    }

    public Integer ileAkcji(Akcja akcja) {
        return portfel.get(akcja);
    }

    public void dodajAkcje(Akcja akcja, int ilosc) {
        portfel.put(akcja, portfel.getOrDefault(akcja, 0) + ilosc);
    }

    public abstract void podejmijDecyzje(Symulacja symulacja);

    public String portfel(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Akcja, Integer> entry : portfel.entrySet()) {
            Akcja akcja = entry.getKey();
            Integer ilosc = entry.getValue();
            sb.append(akcja).append(": ").append(ilosc).append(" ");
        }
        return sb.toString();
    }

    @Override
    public String toString(){
        return gotowka + " " + portfel();
    }

    public void zlozZlecenie(Akcja akcja, TypZlecenia typZlecenia, int limitCeny,
                             int ilosc, int tura, int kolejnosc, int typ, Symulacja symulacja){
        if (typ == 0)
            akcja.dodajZlecenie(new ZlecenieNatychmiastowe(this, akcja, null, typZlecenia,
                    limitCeny, ilosc, tura, kolejnosc));
        else if (typ == 1)
            akcja.dodajZlecenie(new ZlecenieWykonajLubAnuluj(this, akcja, null, typZlecenia,
                    limitCeny, ilosc, tura, kolejnosc));
        else if (typ == 2)
            akcja.dodajZlecenie(new ZlecenieBezTerminu(this, akcja, null, typZlecenia,
                    limitCeny, ilosc, tura, kolejnosc));
        else
            akcja.dodajZlecenie(new ZlecenieDoKoncaTury(this, akcja, null, typZlecenia,
                    limitCeny, ilosc, tura, random.nextInt(tura, tura + 11), kolejnosc));
        symulacja.zwiekszKolejnosc();

    }

}
