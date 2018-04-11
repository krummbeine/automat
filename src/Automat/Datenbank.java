package Automat;

import Automat.AusgabeWert;
import Automat.UebergangsWert;
import Automat.Zustand;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 846995
 */
@XmlRootElement(name="Automat")
@XmlAccessorType(XmlAccessType.FIELD)
public class Datenbank {

    @XmlElementWrapper(name = "uebergaenge")
    @XmlElement(name="uebergang")
    public List<UebergangsWert> uebergangsTabelle = new ArrayList<UebergangsWert>();

    @XmlElementWrapper(name = "ausgaben")
    @XmlElement(name="ausgabe")
    public List<AusgabeWert> ausgabeTabelle = new ArrayList<AusgabeWert>();

    @XmlElementWrapper(name = "symbole")
    @XmlElement(name="symbol")
    public List<Symbol> symbolTabelle = new ArrayList<Symbol>();

    @XmlElementWrapper(name = "zustaende")
    @XmlElement(name="zustand")
    public List<Zustand> zustandsTabelle = new ArrayList<Zustand>();

    @XmlElement(name="startzustand")
    public Zustand startZustand = new Zustand();

    @XmlElement(name="starteingabesymbol")
    public Symbol startEingabeSymbol = new Symbol();
}
