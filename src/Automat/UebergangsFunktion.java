package Automat;

/**
 * @author 846995
 */
public class UebergangsFunktion {

    private Datenbank datenbank;

    public Zustand uebergang(Zustand aktuellerZustand, Symbol eingabeSymbol){
        for(UebergangsWert wert : this.datenbank.uebergangsTabelle) {
            if(aktuellerZustand.getId() == wert.aktuellerZustand.getId() &&
                    eingabeSymbol.getId() == wert.eingabeSymbol.getId())
                return wert.folgeZustand;
        }
        return null;
    }

    public void addToTabelle(Zustand aktuellerZustand, Symbol eingabeSymbol, Zustand folgeZustand){
        UebergangsWert x = new UebergangsWert();
        x.aktuellerZustand = aktuellerZustand;
        x.eingabeSymbol = eingabeSymbol;
        x.folgeZustand = folgeZustand;
        this.datenbank.uebergangsTabelle.add(x);
    }

    public UebergangsFunktion(Datenbank datenbank) {
        this.datenbank = datenbank;
    }
}
