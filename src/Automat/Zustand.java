package Automat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 846995
 */
@XmlRootElement(name="zustand")
public class Zustand {

    private static int id_counter = 0;

    @XmlElement(name="id")
    private int id;

    @XmlElement(name="beschreibung")
    private String beschreibung;

    public Zustand(String name){
        this.beschreibung = name;
        this.id = id_counter++;
    }

    public Zustand(){
        this.beschreibung = "none";
        this.id = id_counter++;
    }

    public String getBeschreibung(){
        return this.beschreibung;
    }

    public int getId(){
        return this.id;
    }
}
