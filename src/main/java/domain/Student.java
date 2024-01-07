package domain;

import java.sql.Date;

public class Student extends BaseEntityStudent{

    private String vorname;
    private String nachname;
    private Date geburtstag;


    public Student(Long id, String vorname, String nachname, Date geburtstag) {
        super(id);
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtstag = geburtstag;
    }

    public Student(String vorname, String nachname, Date geburtstag) {
        super(null);
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtstag = geburtstag;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        if (vorname != null && vorname.length() > 2) {
            this.vorname= vorname;
        } else {
            throw new InvalidValueException("Vorname muss mindestens 3 Zeichen lang sein!");
        }
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        if (nachname != null && nachname.length() > 2) {
            this.nachname = nachname;
        } else {
            throw new InvalidValueException("Nachname muss mindestens 3 Zeichen lang sein!");
        }
    }

    public Date getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(Date geburtstag) {
        if(geburtstag != null){
            this.geburtstag = geburtstag;
        }else{
            throw new InvalidValueException("Das Geburtstag sarf nicht null/leer sein!");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", geburtstag=" + geburtstag +
                '}';
    }
}
