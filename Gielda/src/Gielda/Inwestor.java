package Gielda;

import Main.Symulacja;

import java.util.Map;

public abstract class Inwestor {
    protected int gotowka;
    protected Map<Akcja, Integer> portfel;

    public Inwestor(int gotowka, Map<Akcja, Integer> portfel) {
        this.gotowka = gotowka;
        this.portfel = portfel;
    }

    public void setGotowka(int gotowka) {
        this.gotowka = gotowka;
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

}
