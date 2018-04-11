package Automat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 846995
 */
@XmlRootElement(name="symbol")
public class Symbol {

    private static int id_counter = 0;

    @XmlElement(name="id")
    private int id;

    @XmlElement(name="typ")
    private String typ;

    @XmlElement(name="payload")
    private String payload;

    public Symbol(String typ, String name){
        this.typ = typ;
        this.payload = name;
        this.id = id_counter++;
    }

    public Symbol(){
        this.typ = "none";
        this.payload = "none";
        this.id = id_counter++;
    }

    public String getPayload(){
        return this.payload;
    }

    public String getTyp(){
        return this.typ;
    }

    public int getId(){
        return this.id;
    }
}
