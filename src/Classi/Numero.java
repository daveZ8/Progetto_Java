package Classi;

/**
* <p> Title:Numero </p>
* <p> Description: Sottoclasse di {@link Cella} utilizzata per i dati numerici di tipo double.
*  Utilizzabile nelle formule.  </p>
*  
*  @author Davide Rivi
*/
public class Numero extends Cella<Double>{
	/**
	 * @serial versione
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Numero: costruttore.
	 * Richiama il costruttore della classe padre,
	 * 
	 * @param s valore di tipo Double
	 */
	public Numero(Double s)
	{
		super(s);
	}
	
	@Override
	public boolean perFormula()
	{
		return true;
	}
}
