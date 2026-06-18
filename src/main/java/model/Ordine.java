package model;

import java.sql.Timestamp;

public class Ordine {
    private int id;
    private int utenteId;
    private int dvdId;
    private float prezzo;
    private Timestamp dataOrdine;
    private int quantita;
    private String seqId;

    public Ordine() {}

    public Ordine(int id, String seqId, int utenteId, int dvdId, float prezzo,
                  Timestamp dataOrdine, int quantita) {
        this.id         = id;
        this.seqId = seqId;
        this.utenteId   = utenteId;
        this.dvdId      = dvdId;
        this.prezzo     = prezzo;
        this.dataOrdine = dataOrdine;
        this.quantita   = quantita;
    }

    public int getId()                      { return id; }
    public void setId(int id)               { this.id = id; }

    public int getUtenteId()                { return utenteId; }
    public void setUtenteId(int utenteId)   { this.utenteId = utenteId; }

    public int getDvdId()                   { return dvdId; }
    public void setDvdId(int dvdId)         { this.dvdId = dvdId; }

    public float getPrezzo()                { return prezzo; }
    public void setPrezzo(float prezzo)     { this.prezzo = prezzo; }

    public Timestamp getDataOrdine()                { return dataOrdine; }
    public void setDataOrdine(Timestamp dataOrdine) { this.dataOrdine = dataOrdine; }

    public int getQuantita()                { return quantita; }
    public void setQuantita(int quantita)   { this.quantita = quantita; }
    
    public String getSeq() {
    	return seqId;
    }
    public void setSeq(String s) {
    	seqId = s;
    }
}