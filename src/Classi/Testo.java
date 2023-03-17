package Classi;
/**
* <p> Title:Testo </p>
* <p> Description: Sottoclasse di {@link Cella} utilizzata per i dati testuali.
* Non utilizzabile nelle formule.  </p>
* 
* @author Davide Rivi
*/
public class Testo extends Cella<String>{
	/**
	 * @serial versione
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Testo: costruttore.
	 * 
	 * Richiama il costruttore della classe padre
	 * 
	 * @param s valore di tipo String
	 */
	public Testo(String s)
	{
		super (s);
	}
	
	@Override
	public boolean perFormula()
	{
		return false;
	}
}
