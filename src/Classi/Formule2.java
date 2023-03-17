package Classi;

import java.awt.Color;
import java.util.Map;
import Interfaccia.Panel;

/**
* <p> Title:Formule2 </p>
* <p> Description: Sottoclasse di {@link Formule} utilizzata per le formule che hanno come operando delle celle.
* Presenta 3 attributi e sfrutta quello della classe padre per memorizzare i valori delle celle.
* Utilizzabile nelle formule  </p>
* 
* @author Davide Rivi
*/
public class Formule2 extends Formule{
	/**
	 * @serial versione
	 */
	private static final long serialVersionUID = 1L;
	
	/** stringhe per memorizzare le chiavi delle celle interessate presenti nella Map*/
	private String k1,k2; 
	
	/** struttura dati di tipo Map per memorizzare i dati della tabella*/
	private Map<String,Cella> m; 
	
	
	
	/**
	 * Formule2: costruttore1.
	 * Richiama {@link Formule#Formule(double, double, char)} per impostare i valori ricavati da {@link #k1} {@link #k2} e l'operazione. 
	 * Imposta anche le chiavi delle due celle utilizzate nell'espressione.
	 * 
	 * @param map struttura dati corrente
	 * @param op operazione eseguita
	 * @param k1 chiave prima cella
	 * @param k2 chiave seconda cella
	 */
	public Formule2(Map<String,Cella> map, char op, String k1, String k2)
	{
		super((double)map.get(k1).getVal(),(double)map.get(k2).getVal(),op);
		m=map;
		setK1(k1);
		setK2(k2);
	}
	
	/**
	 * Formule2: costruttore2.
	 * Richiama {@link Formule#Formule(double, double, char)} per impostare i valori ricavati da {@link #k2} v1 e l'operazione. 
	 * Imposta anche la chiave della cella utilizzata come secondo operando e il valore della prima vuota.
	 *
	 * @param map struttura dati corrente
	 * @param op operazione eseguita
	 * @param v1 valore numerico primo operando
	 * @param k2 chiave seconda cella
	 */
	public Formule2(Map<String,Cella> map, char op, double v1, String k2)
	{
		super(v1,(double)map.get(k2).getVal(),op);
		m=map;
		k1="";
		setK2(k2);
	}
	
	/**
	 * Formule2: costruttore3.
	 * Richiama {@link Formule#Formule(double, double, char)} per impostare i valori ricavati da {@link #k1} v2 e l'operazione. 
	 * Imposta anche la chiave della cella utilizzata come primo operando e il valore della seconda vuota.
	 *
	 * @param map struttura dati corrente
	 * @param op operazione eseguita
	 * @param k1 chiave prima cella
	 * @param v2 valore numerico secondo operando
	 */
	public Formule2(Map<String,Cella> map, char op, String k1, double v2)
	{
		super((double)map.get(k1).getVal(),v2,op);
		m=map;
		setK1(k1);
		k2="";
	}
		
	/**
	 * @return the k1
	 */
	public String getK1() {
		return k1;
	}

	/**
	 * @param k1 the k1 to set
	 */
	public void setK1(String k1) {
		this.k1 = k1;
	}

	/**
	 * @return the k2
	 */
	public String getK2() {
		return k2;
	}

	/**
	 * @param k2 the k2 to set
	 */
	public void setK2(String k2) {
		this.k2 = k2;
	}

	/**
	 * risultato: metodo che calcola il risultato della formula.
	 * In caso si utilizzi una classe {@link Formule} o {@link Formule2}
	 * invece che il valore si utilizza il risultato della formula della cella selezionata, se {@link #k1} o {@link #k2} sono nulli uno degli operandi e' un numero e non un altra cella.
	 * In caso di operando sbagliato restituisce null. Nel caso venga modificato un valore di una cella presente
	 * in una formula, il risultato diventa NaN come il valore di quella cella e la cella viene segnalata da {@link Panel#getL1()} in rosso.
	 * 
	 * @return risultato operazione.
	 */
	@Override
	public double risultato ()
	{
		try{
			Panel.getL1().setForeground(Color.black);
			if(super.getOp()=='+')
			{
				if(k1.equals(""))
				{
					if(m.get(k2) instanceof Formule || m.get(k2) instanceof Formule2)
						return (super.getVal()+((double)((Formule) m.get(k2)).risultato()));
					else
						return (super.getVal()+((double)m.get(k2).getVal()));
				}
				else if(k2.equals(""))
				{
					if(m.get(k1) instanceof Formule || m.get(k1) instanceof Formule2)
						return (((double)((Formule) m.get(k1)).risultato())+super.getVal2());
					else
						return (((double)(m.get(k1)).getVal())+super.getVal2());
				}
				else if((m.get(k1) instanceof Formule || m.get(k1) instanceof Formule2) && (m.get(k2) instanceof Formule || m.get(k2) instanceof Formule2))
					return ((double)((Formule) m.get(k1)).risultato()+(double)((Formule) m.get(k2)).risultato());
				else if(m.get(k1) instanceof Formule || m.get(k1) instanceof Formule2)
					return ((double)((Formule) m.get(k1)).risultato())+((double)m.get(k2).getVal());
				else if(m.get(k2) instanceof Formule || m.get(k2) instanceof Formule2)
					return ((double)(m.get(k1)).getVal()+(double)((Formule) m.get(k2)).risultato());
				else
					return ((double)m.get(k1).getVal())+((double)m.get(k2).getVal());
			}
			else if(super.getOp()=='-')
			{
				if(k1.equals(""))
				{
					if(m.get(k2) instanceof Formule || m.get(k2) instanceof Formule2)
						return (super.getVal()-((double)((Formule) m.get(k2)).risultato()));
					else
						return (super.getVal()-((double)m.get(k2).getVal()));
				}
				else if(k2.equals(""))
				{
					if(m.get(k1) instanceof Formule || m.get(k1) instanceof Formule2)
						return (((double)((Formule) m.get(k1)).risultato())-super.getVal2());
					else
						return (((double)( m.get(k1)).getVal())-super.getVal2());
				}
				else if((m.get(k1) instanceof Formule || m.get(k1) instanceof Formule2) && (m.get(k2) instanceof Formule || m.get(k2) instanceof Formule2))
					return ((double)((Formule) m.get(k1)).risultato()-(double)((Formule) m.get(k2)).risultato());
				else if(m.get(k1) instanceof Formule  || m.get(k1) instanceof Formule2)
					return ((double)((Formule) m.get(k1)).risultato())-((double)m.get(k2).getVal());
				else if(m.get(k2) instanceof Formule || m.get(k2) instanceof Formule2)
					return ((double)(m.get(k1)).getVal()-(double)((Formule) m.get(k2)).risultato());
				else				
					return ((double)m.get(k1).getVal())-((double)m.get(k2).getVal());
			}
			else
				return Double.NaN;
		}catch(Exception e)
		{
			Panel.getL1().setForeground(Color.red);
			if(!k1.equals("") && !m.get(k1).perFormula())
			{
				m.replace(k1, new Numero(Double.NaN));	
				Panel.getL1().setText(k1);
			}
			if(!k2.equals("") && !m.get(k2).perFormula())
			{
				m.replace(k2, new Numero(Double.NaN));
				Panel.getL1().setText(k2);
			}
			
			return  Double.NaN;
		}
			
	}
	
	/**
	 * toString: stampa la formula sotto forma delle chiavi e non dei valori, se {@link #k1} o {@link #k2} sono nulli
	 * stampa il valore numerico al posto della chiave corrispondente.
	 * 
	 */
	@Override
	public String toString()
	{
		if(k1.equals(""))
			return '='+super.getVal().toString()+super.getOp()+k2;
		else if(k2.equals(""))
			return '='+k1+super.getOp()+super.getVal2().toString();
		else
			return '='+k1+super.getOp()+k2;
	}
	
	@Override
	public boolean perFormula()
	{
		return true;
	}
	
}
