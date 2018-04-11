package Automat;

/**
 * @author 846995
 */
public class AusgabeFunktion {

    private Datenbank datenbank;

    public Symbol[] ausgabe(Zustand aktuellerZustand, Symbol eingabeSymbol){
        for(AusgabeWert wert : this.datenbank.ausgabeTabelle) {
            if(aktuellerZustand.getId() == wert.aktuellerZustand.getId() &&
                    eingabeSymbol.getId() == wert.eingabeSymbol.getId())
                return wert.ausgabeSymbole;
        }
        return null;
    }

    public void addToAusgabeTabelle(Zustand aktuellerZustand, Symbol eingabeSymbol, Symbol[] ausgabeSymbol){
        AusgabeWert x = new AusgabeWert();
        x.aktuellerZustand = aktuellerZustand;
        x.eingabeSymbol = eingabeSymbol;
        x.ausgabeSymbole = ausgabeSymbol;
        this.datenbank.ausgabeTabelle.add(x);
    }

    public AusgabeFunktion(Datenbank datenbank){
        this.datenbank = datenbank;
    }
}
