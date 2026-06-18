package model;

public class Indirizzo {
    private int id;
    private String orderID;
    private int userID;
    private String nome;
    private String cognome;
    private String indirizzo;
    private String citta;
    private String cap;
    private String paese;

    public Indirizzo() {}

    public Indirizzo(int id, String orderID, int userID, String nome, String cognome,
                     String indirizzo, String citta, String cap, String paese) {
        this.id        = id;
        this.orderID   = orderID;
        this.userID    = userID;
        this.nome      = nome;
        this.cognome   = cognome;
        this.indirizzo = indirizzo;
        this.citta     = citta;
        this.cap       = cap;
        this.paese     = paese;
    }

    public int getId()              { return id; }
    public void setId(int id)       { this.id = id; }

    public String getOrderID()             { return orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }

    public int getUserID()            { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getNome()             { return nome; }
    public void setNome(String nome)    { this.nome = nome; }

    public String getCognome()                { return cognome; }
    public void setCognome(String cognome)    { this.cognome = cognome; }

    public String getIndirizzo()                  { return indirizzo; }
    public void setIndirizzo(String indirizzo)    { this.indirizzo = indirizzo; }

    public String getCitta()              { return citta; }
    public void setCitta(String citta)    { this.citta = citta; }

    public String getCap()            { return cap; }
    public void setCap(String cap)    { this.cap = cap; }

    public String getPaese()              { return paese; }
    public void setPaese(String paese)    { this.paese = paese; }
}	