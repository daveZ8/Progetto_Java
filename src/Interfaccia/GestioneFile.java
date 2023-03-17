package Interfaccia;

import java.io.*;
import java.util.*;
import javax.swing.*;
import Classi.*;
import javax.swing.filechooser.FileFilter;

/**
 * <p> Title:GestioneFile </p>
 * <p> Description: sottoclasse della classe FileFilter.
 * 	Utilizzata per la gestione degli oggetti di tipo File che vengono usati per fare salvataggi e caricamenti.
 *  Implementa un FileChooser per la scelta del file nella directory impostata e un FileFilter per la selezione 
 *  dell'estensione del file </p>
 * @author Davide Rivi
 *
 */
public class GestioneFile extends FileFilter{

	/** nome del file utilizzato per il salvataggo e l'apertura*/
	private String nome;
	
	/** elemento JFileChooser utilizzato per la selezione del file nel salvataggio e apertura*/
	private JFileChooser fileC; 
	
	/** nome del file selezionato tramite {@link #fileC}*/
	private File fileName;  
	
	
	
	/**
	 * GestioneFile: costruttore.
	 * Imposta il percorso della directory e il filtro per il {@link #fileC}. 
	 */
	public GestioneFile()
	{
		File file=new File("");
		fileC=new JFileChooser();
		fileC.setCurrentDirectory(file.getAbsoluteFile());
		fileC.setFileFilter(this);
	}
	
	/**
	 * accept: metodo che permette di selezionare i tipi di file da poter usare.
	 */
	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		if(file.isDirectory())
			return true;
		String fname=file.getName().toLowerCase();
		return fname.endsWith("dat") || fname.endsWith("tmp");
	}

	/**
	 * getDescription: metodo che ritorna la descrizione del tipo di file selezionabile.
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "File biniario";
	}
	
	
	/**
	 * salva: metodo che salva la tabella in un file binario.
	 * Prende in ingresso un JFrame su cui aprire il {@link #fileC}, per selezionare il file su cui salvare la struttura dati, 
	 * e un oggetto di tipo Map in cui ci sono i dati della tabella.
	 * Tramire il {@link #fileC}, se il nome del file e' gia' esistente allora viene chiesto se si desidera sovrascrivere,
	 * se il nome del file inserito non esiste, salva creando un nuovo file.
	 * 
	 * @param f oggetto di tipo {@link Frame}
	 * @param m struttura dati Map in cui sono presenti i valori da salvare su file
	 */
	public void salva(JFrame f, Map<String, Cella> m)
	{
		fileC.setDialogTitle("Salva");
		int n=fileC.showSaveDialog(f);
		
		if(n==JFileChooser.APPROVE_OPTION)
		{
			fileName=fileC.getSelectedFile();
			setFileName(fileName.getName());
			
			FileOutputStream fo=null;
			
			if(fileName.exists())
			{
				int x=JOptionPane.showConfirmDialog(f, "File gia' esistente, Vuoi sovraschiverlo?");
				if(x==JOptionPane.YES_OPTION)
				{
					try {
						fo=new FileOutputStream(fileName);
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Nome del file non corretto","Errore",JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
					
					ObjectOutputStream os=null;
					
					try {
						os=new ObjectOutputStream(fo);
						os.writeObject(m);
						os.flush();
						os.close();
						JOptionPane.showMessageDialog(null, "Salvataggio effettuato");
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Salvataggio del file non eseguito","Errore",JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}else if(x==JOptionPane.NO_OPTION)
				{
					salva(f,m);
				}
				else
				{
					setFileName("");
				}
			}
			else
			{
				try {
					fo=new FileOutputStream(fileName);
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Nome del file non corretto","Errore",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				
				ObjectOutputStream os=null;
				
				try {
					os=new ObjectOutputStream(fo);
					os.writeObject(m);
					os.flush();
					os.close();
					JOptionPane.showMessageDialog(null, "Salvataggio effettuato");
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Salvataggio del file non effettuato","Errore",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}else if(n==JFileChooser.ERROR_OPTION)
		{
			JOptionPane.showMessageDialog(null, JFileChooser.ERROR_OPTION,"Errore",JOptionPane.ERROR_MESSAGE);
		}else
			setFileName("");
	}
	
	
	/**
	 * importa: metodo che mi permette di aprire un foglio di calcolo.
	 * Il foglio e' stato salvato in precedenza su un file binario, viene selezionato 
	 * tramite il {@link #fileC} e importato nel {@link Frame}.
	 * E' possibile anche importare da file tmp, seguendo il percorso nella barra del menu' per il file 
	 * temporaneo.
	 * 
	 * @param f oggetto di tipo {@link Frame}
	 * @param m struttura dati di tipo Map utilizzata nel caso non venga importata da nessun file
	 * @return struttura dati di tipo Map con cui e' possibile l'uso del foglio
	 */
	public Map<String,Cella> importa(JFrame f, Map<String, Cella> m)
	{
		fileC.setDialogTitle("Apri");
		int n=fileC.showOpenDialog(f);
		
		if(n==JFileChooser.APPROVE_OPTION)
		{			
			fileName=fileC.getSelectedFile();
			setFileName(fileName.getName());
			
			FileInputStream fi=null;
			
			try {
				fi=new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Nome del file non corretto","Errore",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
			
			ObjectInputStream is=null;
			
			try{
				is=new ObjectInputStream(fi);
				Map<String, Cella> m2=null;
				m2=(Map<String,Cella>)(is.readObject());
				is.close();
				return m2;			
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Apertura del file non effettuata","Errore",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return null;
			}
		}else if(n==JFileChooser.ERROR_OPTION)
		{
			return null;
		}
		else
		{
			return m;
		}
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return nome;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		nome = fileName;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the fileC
	 */
	public JFileChooser getFileC() {
		return fileC;
	}

	/**
	 * @param fileC the fileC to set
	 */
	public void setFileC(JFileChooser fileC) {
		this.fileC = fileC;
	}
}
