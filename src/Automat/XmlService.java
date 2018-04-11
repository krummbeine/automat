package Automat;

import Automat.Automat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * JAXB Marshall - Unmarshall
 * Füllt die Datenbank der Mealy-Maschine mit Symbolen, Zuständen, der
 * ZustandsÜbergangsFunktion und AusgabeFunktion
 * @author 846995
 */
public class XmlService {
    private Automat automat;

    public XmlService(Automat automat){
        this.automat = automat;
    }

    public void speichernAlsXmlDatei() throws IOException, JAXBException {
        FileOutputStream file = new FileOutputStream(new File("datenbank.xml"));
        JAXBContext ctx = JAXBContext.newInstance(Datenbank.class);
        Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(automat.datenbank, file);
        file.close();
    }

    public void ladenAusXmlDatei() throws IOException, JAXBException {
        FileInputStream file = new FileInputStream( new File("datenbank.xml"));
        JAXBContext ctx = JAXBContext.newInstance(Datenbank.class);
        Unmarshaller u = ctx.createUnmarshaller();
        automat.datenbank = (Datenbank) u.unmarshal(file);
        automat.uebergangsFunktion = new UebergangsFunktion(automat.datenbank);
        automat.ausgabeFunktion = new AusgabeFunktion(automat.datenbank);
    }
}
