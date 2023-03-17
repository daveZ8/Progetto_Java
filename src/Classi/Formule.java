package Classi;

/**
* <p> Title:Formule </p>
* <p> Description: Sottoclasse di {@link Cella} utilizzata per le formule solo numeriche.
* Utilizzabile nelle formule.  </p>
* 
* @author Davide Rivi
*/
public class Formule extends Cella<Double>{
	/**
	 * @serial versione
	 */
	private static final long serialVersionUID = 1L;
	
	/** attributo per il secondo valore dell'operazione*/
	private double val2; 
	/** attributo per definere l'operazione da svolgere*/
	private char op; 
	
	
	/**
	 * Formule: costruttore.
	 * Richiama il costruttore {@link Cella#Cella(Object)}, inizializza {@link #val2} a v2 e {@link #op} a op.
	 *
	 * @param v1 primo valore
	 * @param v2 secondo valore
	 * @param op operazione scelta
	 */
	public Formule(double v1, double v2, char op)
	{
		super(v1);
		setVal2(v2);
		setOp(op);
	}

	

	/**
	 * @return the val2
	 */
	public Double getVal2() {
		return val2;
	}

	/**
	 * @param val2 the val2 to set
	 */
	public void setVal2(double val2) {
		this.val2 = val2;
	}

	/**
	 * @return the op
	 */
	public char getOp() {
		return op;
	}

	/**
	 * @param op the op to set
	 */
	public void setOp(char op) {
		this.op = op;
	}

	/**
	 * Risultato: metodo che calcola il risultato tra i due valori numerici inseriti.
	 * In caso di operazione non ammessa restituisce un valore null.
	 * 
	 * @return risultato della formula
	 */
	public double risultato()
	{
		
		if(op=='+')
			return super.getVal()+val2;
		else if(op=='-')
			return super.getVal()-val2;
		else
			return Double.NaN;
	}
		
	/**
	 * 	toString: metodo che stampa la formula in formato testuale.
	 * 
	 * @return formula corrente
	 */	
	@Override
	public String toString()
	{
		return '='+super.toString()+op+val2;
	}
	
	@Override
	public boolean perFormula()
	{
		return true;
	}
	
}
