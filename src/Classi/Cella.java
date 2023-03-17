package Classi;

import java.io.Serializable;

/**
 * <p> Title:Cella </p>
 * <p> Description: Classe Cella, oggetto utilizzato per gestire i dati della tabella.
 *	Contiene un attributo di tipo Generics per gestire il valore delle celle nella JTable, presenta diversi metodi come:
 * 2 costruttori, uno che inizializza a null ({@link #Cella()}) e l'altro che ricevendo un parametro in ingresso imposta il valore dell'attributo
 * {@link #val} a quel valore ({@link #Cella(E v)}), poi presenta i metodi set,get toString e {@link #perFormula}.
 * </p>
 * 
 * @author Davide Rivi
 *
 * @param <E> tipo di dato gestito nella cella
 */
public class Cella<E> implements Serializable{
	/**
	 * @serial versione
	 */
	private static final long serialVersionUID = 1L;
	
	/** attributo di tipo generics, corrisponde al valore nella cella*/
	private E val; 
	
	/**
	 * Cella: costruttore.
	 * 
	 * Inizializza {@link #val} a null.
	 */
	public Cella() 
	{
		val=null;
	};
	
	/**
	 * Cella: costruttore.
	 * 
	 * Inizializza {@link #val} al valore passato.
	 * 
	 * @param v valore che viene passato dai chiamanti
	 */
	public Cella(E v)
	{
		setVal(v);
	}
	
	
	
	/**
	 * @return the val
	 */
	public E getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(E val) {
		this.val = val;
	}
	
	/**
	 * toString: metodo che stampa il valore della cella.
	 * 
	 * @return stampa di {@link #val}
	 */
	@Override
	public String toString()
	{
		return val+"";
	}
	
	/**
	 * perFormula: metodo per la gestione delle celle nelle formule.
	 * Ritorna true o false se la cella e' istanza di una classe che e' utilizzabile o meno 
	 * nella classe {@link Formule2}.
	 * 
	 * @return se una classe e' utilizzabile o meno nelle formule.
	 */
	public boolean perFormula()
	{
		return false;
	}
	
}
