package Interfaccia;
import Classi.*;

import java.awt.Color;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 * <p> Title:MyTableModel </p>
 * <p> Description: Classe per la gestione della tabella, estende la classe astratta AbstractTableModel.
 * Oltre ai metodi ereditati, implementa {@link #key} e 4 attributi:{@link #MAXROW}, {@link #nameCol}, {@link #formula}, {@link #m }</p>
 * 
 * @author Davide Rivi
 * 
 */
public class MyTableModel extends AbstractTableModel {
	/**
	 * @serial versione
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Array di stringhe utilizzato per la nominazione delle colonne
	 */
	static final String[] nameCol= {"//","A","B","C","D","E","F","G","H","I","J","K","L",
			"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	/** numero massimo di righe+1(perche' inizia da zero)*/
	static final int MAXROW=30+1; 
	
	/** valore passato del {@link Panel} in base a quale JRadioButton e' selezionato,
	 *  per la visualizzazione delle formule o dei risultati*/
	private boolean formula; 
	
	/** struttura dati di tipo Map per memorizzare i dati della tabella*/
	private Map<String,Cella> m; 
	
	
	
	/**
	 * MyTableModel: costruttore.
	 * Imposta il valore di {@link #m} e di {@link #formula} in base ai valori passati dai parametri.
	 * 
	 * @param m oggetto Map
	 * @param f variabile di tipo bolean
	 */
	public MyTableModel(Map<String, Cella> m, boolean f)
	{
		this.m=m;
		formula=f;
	}
	
	/**
	 * getRowCount: imposta il numero di righe.
	 * 
	 * @return numero di righe ottenute dalla dimensione della map, diviso il numero di colonne+1 
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return m.size()/nameCol.length+1;
	}

	/**
	 * getColumnCount: imposta il numero di colonne.
	 * 
	 * @return dimensione di {@link #nameCol}
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return nameCol.length;
	}

	/**
	 * getValueAt: metodo che stampa i valori della {@link #m} nelle celle.
	 * Se e' una cella {@link Testo} o {@link Numero} stampa il valore.
	 * Se e' una cella {@link Formule} o {@link Formule2} stampa in base al valore di {@link #formula}: o la formula o il risultato.
	 * 
	 * @param rowIndex indice di riga
	 * @param columnIndex indice di colonna
	 * @return Object per impostare il valore di una cella
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
		String key=key(columnIndex,rowIndex);
	
		
		if(rowIndex>=0 && columnIndex==0)
			return rowIndex+1;
		else
		{
			if(m.get(key) instanceof Formule2 || m.get(key) instanceof Formule)
				if(!formula)
					return ((Formule) m.get(key(columnIndex,rowIndex))).risultato();		
				else
					return ((Formule) m.get(key)).toString();
			else
				return m.get(key).getVal();
			
		}
	}
	
	/**
	 * isCellEditable: definisce quali celle sono editabili e quali no.
	 * 
	 * @param row indice di riga
	 * @param col indice di colonna
	 * @return true se una cella e' editabile false altrimenti
	 */
	@Override
	public boolean isCellEditable(int row,int col)
	{
		if(col==0)
			return false;
		else
			return true;
	}
	
	/**
	 * getColumnName: metodo che ritorna il nome della colonna in base all'indice.
	 * 
	 * @param col indice di colonna
	 * @return lettera che rappresenta la colonna
	 */
	@Override
	public String getColumnName(int col) 
	{
		return nameCol[col];
	}
	
	
	/**
	 * setValueAt: metodo che imposta i valori della cella nell'oggetto {@link #m}.
	 * Se il primo valore della cella e' un carattere definisce il valore di {@link #m} all'opportuna key come cella {@link Testo}.
	 * Se il primo valore della cella e' un numero o i caratteri '+' o '-' definisce il valore di {@link #m} all'opportuna key come cella {@link Numero}.
	 * Se il primo valore della cella e' '=' definisce il valore di {@link #m} all'opportuna key come cella {@link Formule} se i 2 valori separati dall'operando sono entrambi numeri
	 * e soddisfano le condizioni di {@link Numero}, se i 2 valori sono entrambi indici delle celle o sono uno numero e l'altro indice o viceversa allora istanza il value a {@link Formule2}
	 * gestendo la key se esiste oppure meno e se la cella selezionata nella formula e' valida per l'operazione altrimenti imposta i valori a NaN.
	 * Aggiorna la tabella ad ogni modifica grazie al metodo fireTableDataChanged.
	 * 
	 * 
	 * @param value valore della cella
	 * @param row indice di riga
	 * @param col indice di colonna
	 */
	@Override
	public void setValueAt(Object value, int row, int col)
	{		
		
		try
		{
			if(!value.toString().isEmpty() || value!=null)
			{
				if(Character.isLetter(((String)value).charAt(0)))
				{
					m.replace(key(col,row), new Testo(((String)value)));
				}
				else if(Character.isDigit(((String)value).charAt(0)) || ((String)value).charAt(0)=='-' || ((String)value).charAt(0)=='+' )
				{
					m.replace(key(col,row), new Numero(Double.parseDouble((String)value)));
				}
				else if(((String)value).charAt(0)=='=')
				{
					char op=' ';
					StringBuffer sb1=new StringBuffer();
					StringBuffer sb2=new StringBuffer();
					
					
					int i=1;
					if(((String)value).charAt(i)=='+' || ((String)value).charAt(i)=='-')
					{	
						sb1.append(((String)value).charAt(i));
						i++;
					}
			
						
					while(Character.isLetterOrDigit(((String)value).charAt(i)) || ((String)value).charAt(i)=='.')
					{
						sb1.append(((String)value).charAt(i));
						i++;
					}
					op=((String)value).charAt(i);
					i++;
					
					if(((String)value).charAt(i)=='+' || ((String)value).charAt(i)=='-')
					{
						sb2.append(((String)value).charAt(i));
						i++;
					}
			
					while(i<((String)value).length() && (Character.isLetterOrDigit(((String)value).charAt(i)) || ((String)value).charAt(i)=='.'))
					{
						sb2.append(((String)value).charAt(i));
						i++;
					}
				
					
					i=0;
					while(Character.isLetter(sb1.toString().charAt(i)))
					{
						sb1.setCharAt(i,Character.toUpperCase(sb1.charAt(i)));
						i++;
					}
					
					i=0;
					while(Character.isLetter(sb2.toString().charAt(i)))
					{
						sb2.setCharAt(i,Character.toUpperCase(sb2.charAt(i)));
						i++;
					}
					
					String val1=sb1.toString();
					String val2=sb2.toString();
					
					if(Character.isLetter(val1.charAt(0)) && Character.isLetter(val2.charAt(0)))
					{
						
						if(m.containsKey(val1) && m.containsKey(val2) && m.get(val1).perFormula() && m.get(val2).perFormula())
							m.replace(key(col,row), new Formule2(m,op,val1,val2));
						else
						{
							JOptionPane.showMessageDialog(null, "Tipo cella non corretto", "Errore", JOptionPane.ERROR_MESSAGE);
							m.replace(key(col,row), new Cella());
						}
						
					}					
					else if(Character.isLetter(val1.charAt(0)) && (((val2.charAt(0)=='-' || val2.charAt(0)=='+') && (Character.isDigit(val2.charAt(1)))) || Character.isDigit(val2.charAt(0))))
						m.replace(key(col,row), new Formule2(m,op,val1,Double.parseDouble(val2)));
					else if(Character.isLetter(val2.charAt(0)) && (((val1.charAt(0)=='-' || val1.charAt(0)=='+') && (Character.isDigit(val1.charAt(1)))) || Character.isDigit(val1.charAt(0))))
						m.replace(key(col,row), new Formule2(m,op,Double.parseDouble(val1),val2));
					else if(((val1.charAt(0)=='-' || val1.charAt(0)=='+') && (Character.isDigit(val1.charAt(1)))) || ((val2.charAt(0)=='-' || val2.charAt(0)=='+') && (Character.isDigit(val2.charAt(1)))) || (Character.isDigit(val1.charAt(0)) || Character.isDigit(val2.charAt(0))))
						m.replace(key(col,row), new Formule(Double.parseDouble(val1),Double.parseDouble(val2),op));
					else
						m.replace(key(col,row), new Cella());
				}
				else
					m.replace(key(col,row), new Cella());
			}
			else
				m.replace(key(col,row), new Cella());
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Formato non valido!","Errore",JOptionPane.ERROR_MESSAGE);
			m.replace(key(col,row), new Cella<Double>());
		}
		
		
		Panel.getL1().setForeground(Color.black);
		fireTableDataChanged();
	}
	
	
	
	/**
	 * key: metodo che mi genera la key di un elemento di map.
	 * Unisce il valore della array {@link #nameCol} corrispondente al valore di col concatenato all'indice di row+1.
	 * 
	 * @param c valore della colonna
	 * @param r valore della riga
	 * @return chiave corrispondente
	 */
	static final String key(int c,int r)
	{	
		return nameCol[c]+(r+1);		
	}

	/**
	 * @return the formula
	 */
	public boolean isFormula() {
		return formula;
	}

	/**
	 * @param formula the formula to set
	 */
	public void setFormula(boolean formula) {
		this.formula = formula;
	}

	/**
	 * @return the m
	 */
	public Map<String, Cella> getM() {
		return m;
	}

	/**
	 * @param m the m to set
	 */
	public void setM(Map<String, Cella> m) {
		this.m = m;
	}

}
