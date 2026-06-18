package model;

public class DVDInCart extends DVD{
	private int quantity;
	
	  public DVDInCart(int id, String nome, int durata, String regista, float prezzo, boolean inCatalogo, int quantity) {
	        super(id, nome, durata, regista, prezzo, inCatalogo);
	        this.quantity = quantity;
	    }
	
	public void setQuantity(int q) {
		quantity = q;
	}
	
	public int getQuantity() {
		return quantity;
	}
}