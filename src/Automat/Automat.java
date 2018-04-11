package Automat;

import Empfaenger.*;
import Sender.*;

import javax.xml.bind.JAXBException;
import java.io.*;

/**
 * Baut die Mealy-Maschine aus einer XML-Datei oder via Java-Code
 * Zustände, Symbole, ZustandsÜbergangsFunktion, AusgabeFunktion werden geladen
 * Außerdem werden alle benötigten Komponenten zentral hier registriert,
 * damit sie referenziert werden können (Injektion)
 * @author 846995
 */
public class Automat {

    public Datenbank datenbank;
    public Zustand aktuellerZustand;
    public UebergangsFunktion uebergangsFunktion;
    public AusgabeFunktion ausgabeFunktion;
    public DateiVerwaltung dateiVerwaltung;
    private XmlService xmlService;
    public JsonService jsonService;
    public MealyMaschine mealyMaschine;

    public Queue eingabeQueue;
    public Queue ausgabeQueue;

    public KonsolenEmpfaenger empfaenger1;
    public VerzeichnisWatchService empfaenger2;
    public SocketEmpfaenger empfaenger3;
    public DatagramSocketEmpfaenger empfaenger4;
    public Empfaenger empfaenger;

    public VerzeichnisSender sender1;
    public SocketSender sender2;
    public DatagramSocketSender sender3;
    public Sender sender;

    public Symbol startEingabeSymbol;

    public Automat(int senderPort, int empfaengerPort) throws IOException, JAXBException, InterruptedException {
        xmlService = new XmlService(this);
        jsonService = new JsonService();

        if(true) {
            ampelLadenViaJavaCode();
            xmlService.speichernAlsXmlDatei();
        }
        else{
            xmlService.ladenAusXmlDatei();
        }

        this.uebergangsFunktion = new UebergangsFunktion(this.datenbank);
        this.ausgabeFunktion = new AusgabeFunktion(this.datenbank);

        dateiVerwaltung = new DateiVerwaltung(this);

        // Startzustand
        for(Zustand zustand : this.datenbank.zustandsTabelle)
            if(zustand.getId() == this.datenbank.startZustand.getId())
                aktuellerZustand = zustand;

        // StartEingabeSymbol
        for(Symbol symbol : this.datenbank.symbolTabelle)
            if(symbol.getId() == this.datenbank.startEingabeSymbol.getId())
                startEingabeSymbol = symbol;

        eingabeQueue = new Queue();
        ausgabeQueue = new Queue();

        mealyMaschine = new MealyMaschine(this);

        // Empfänger definieren und starten
        empfaenger = new KonsolenEmpfaenger(this);
        //empfaenger = new VerzeichnisWatchService(this);
        //empfaenger = new SocketEmpfaenger(this, empfaengerPort);
        //empfaenger = new DatagramSocketEmpfaenger(this, empfaengerPort);
        empfaenger = new Empfaenger(this);

        // Sender definieren
        sender = new VerzeichnisSender(this);
        //sender = new SocketSender(this, senderPort);
        //sender = new DatagramSocketSender(this, senderPort);
        sender = new Sender(this);

        this.eingabeQueue.hineinlegen(jsonService.symbolToJsonString(startEingabeSymbol));
    }

    public static void main(String[] args) throws IOException, JAXBException, InterruptedException {
        Automat automat = new Automat(10013, 10013);

        // Drei Automaten mit verbundenen Sender-Empfänger-Paaren:
        // Automat automat1 = new Automat(10014, 10016);
        // Automat automat2 = new Automat(10015, 10014);
        // Automat automat3 = new Automat(10016, 10015);
    }

    public void ampelLadenViaJavaCode(){
        this.datenbank = new Datenbank();
        this.uebergangsFunktion = new UebergangsFunktion(this.datenbank);
        this.ausgabeFunktion = new AusgabeFunktion(this.datenbank);

        // Ausgabesymbole festlegen
        Symbol rotLeuchten = new Symbol("response", "Rotes Licht an");
        Symbol gelbLeuchten = new Symbol("response", "Gelbes Licht an");
        Symbol gruenLeuchten = new Symbol("response", "Gruenes Licht an");
        Symbol tick = new Symbol("request", "tick");
        datenbank.symbolTabelle.add(tick);
        datenbank.symbolTabelle.add(rotLeuchten);
        datenbank.symbolTabelle.add(gelbLeuchten);
        datenbank.symbolTabelle.add(gruenLeuchten);

        // Zustände festlegen
        Zustand zustandRot = new Zustand("zustandRot");
        Zustand zustandRotGelb = new Zustand("zustandRotGelb");
        Zustand zustandGruen = new Zustand("zustandGruen");
        Zustand zustandGelb = new Zustand("zustandGelb");
        datenbank.zustandsTabelle.add(zustandRot);
        datenbank.zustandsTabelle.add(zustandRotGelb);
        datenbank.zustandsTabelle.add(zustandGruen);
        datenbank.zustandsTabelle.add(zustandGelb);

        // Start-Zustand festlegen
        aktuellerZustand = zustandGruen;
        datenbank.startZustand = aktuellerZustand;

        // Start-Symbol festlegen
        startEingabeSymbol = tick;
        datenbank.startEingabeSymbol = startEingabeSymbol;

        // ZustandsuebergangsTabelle der Automat.UebergangsFunktion füllen
        // AktuellerZustand, EingabeZeichen, NachfolgenderZustand
        uebergangsFunktion.addToTabelle(zustandRot,       tick, zustandRotGelb);
        uebergangsFunktion.addToTabelle(zustandRotGelb,   tick, zustandGruen);
        uebergangsFunktion.addToTabelle(zustandGruen,     tick, zustandGelb);
        uebergangsFunktion.addToTabelle(zustandGelb,      tick, zustandRot);

        // AusgabeTabelle der Automat.AusgabeFunktion befüllen
        // AktuellerZustand, EingabeZeichen, AusgabeZeichen
        ausgabeFunktion.addToAusgabeTabelle(zustandRot,     tick, new Symbol[] { rotLeuchten, tick });
        ausgabeFunktion.addToAusgabeTabelle(zustandRotGelb, tick, new Symbol[] { gelbLeuchten, rotLeuchten, tick });
        ausgabeFunktion.addToAusgabeTabelle(zustandGelb,    tick, new Symbol[] { gelbLeuchten, tick });
        ausgabeFunktion.addToAusgabeTabelle(zustandGruen,   tick, new Symbol[] { gruenLeuchten, tick });
    }
}
