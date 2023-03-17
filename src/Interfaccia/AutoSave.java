package Interfaccia;

import java.io.*;
import java.util.Map;
import javax.swing.JOptionPane;

import Classi.*;


/**
 * <p> Title:AutoSave </p>
 * <p> Description: classe per le gestione del salvataggio automatico.
 * Sottoclasse di Thread. 
 * Nella classe viene eseguito ogni 5 minuti il salvataggio automatico del lavoro su un file
 * temporaneo, che verra' eliminato una volta chiuso il programma.
 * Presenta 2 attributi:
 * 	-{@link #m}
 *  -{@link #tmp}
 * 
 * </p>
 * @author Davide Rivi
 *
 */
public class AutoSave extends Thread{
	
	/** struttura dati di tipo Map per memorizzare i dati della tabella*/
	private Map<String, Cella> m;
	
	/** file temporaneo per il salvataggio automatico*/
	private File tmp;
	

	/**
	 * AutoSave: costruttore.
	 * Inizializza {@link #m} alla Map del chiamante, e crea un nuovo file temporaneo con prefisso autoS e estensione .tmp.
	 * 
	 * @param m Struttura dati di tipo Map passata dal chiamante.
	 */
	public AutoSave(Map<String,Cella> m)
	{
		this.m=m;
		try {

			tmp=File.createTempFile("autoS",".tmp");
			tmp.deleteOnExit();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * run: esegue di continuo, salva {@link #m} in un file temporaneo {@link #tmp} e poi addormenta il thread per 5 minuti.
	 */
	@Override
	public void run()
	{
			
		while(true)
		{
			try {
				synchronized(this) {
					FileOutputStream fo=new FileOutputStream(tmp);
					ObjectOutputStream os=new ObjectOutputStream(fo);
					os.writeObject(m);
					os.flush();
					os.close();
				}	
				
				sleep(5*1000*60);
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Salvataggio automatico non effettuato","Errore",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * @return the tmp
	 */
	public File getTmp() {
		return tmp;
	}

	/**
	 * @param tmp the tmp to set
	 */
	public void setTmp(File tmp) {
		this.tmp = tmp;
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
