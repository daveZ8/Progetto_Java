package Interfaccia;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import javax.swing.JOptionPane;

/**
 * <p> Title:Terminator </p>
 * <p> Description: classe che implementa la classe WindowListener. 
 * Utilizzata per la gestione della chiusura del {@link Frame}.
 * All'apertura della finestra apre un pop-up introduttivo.
 * Nella classe viene svolto un azione di chiusura in base allo stato del thread {@link AutoSave} che se 
 * e' in stato di waiting chiede all'utente se intende salvare altrimenti termina l'applicazione salvando
 * il contenuto del file temporaneo nel file del foglio(se esiste).  </p>
 * 
 * @author Davide Rivi
 *
 */
public class Terminator implements WindowListener{
	
	/**  {@link Frame} corrente */
	private Frame f; 
	
	/**
	 * Terminator: costruttore.
	 * 
	 * @param f {@link Frame} chiamante il metodo
	 */
	public Terminator(Frame f)
	{
		this.f=f;
	}

	/**
	 * @return the f
	 */
	public Frame getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 */
	public void setF(Frame f) {
		this.f = f;
	}

	/**
	 * windowClosing: metodo che se il thread {@link AutoSave} sta eseguendo e non e' terminato chiede se si desira salvare, 
	 * altrimenti termina il programma, aggiornando il file di salvataggio.
	 * 
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		if(f.autoSave.getState()==Thread.State.TIMED_WAITING)
		{
			int x=JOptionPane.showConfirmDialog(f, "AutoSalvataggio non completato, vuoi salvare?","Chiusura",JOptionPane.YES_NO_OPTION);
			if(x==JOptionPane.YES_OPTION)
			{
				f.gf.salva(f,f.map);
				System.exit(0);
			}else if(x==JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
		}
		else 
		{
			FileOutputStream fo=null;
			try {
				fo=new FileOutputStream(f.gf.getFileName());
			}catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Nome del file non corretto","Errore",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			
			
			try {
				File src=f.autoSave.getTmp();
				Files.copy(src.toPath(), fo);
				fo.close();
				System.exit(0);
			}catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Salvataggio del file non effettuato","Errore",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			
		}
			
	}
	
	/**
	 * windowClosing: metodo che se il thread {@link AutoSave} sta eseguendo e non e' terminato chiede se si desira salvare, 
	 * altrimenti termina il programma, aggiornando il file di salvataggio.
	 * 
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		if(f.autoSave.getState()==Thread.State.TIMED_WAITING)
		{
			int x=JOptionPane.showConfirmDialog(f, "AutoSalvataggio non completato, vuoi salvare?","Chiusura",JOptionPane.YES_NO_OPTION);
			if(x==JOptionPane.YES_OPTION)
			{
				f.gf.salva(f, f.map);
				System.exit(0);
			}else if(x==JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
		}
		else 
		{
			FileOutputStream fo=null;
			try {
				fo=new FileOutputStream(f.gf.getFileName());
			}catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Nome del file non corretto","Errore",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			
			
			try {
				File src=f.autoSave.getTmp();
				Files.copy(src.toPath(), fo);
				fo.close();
				System.exit(0);
			}catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Salvataggio del file non effettuato","Errore",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			
		}
	}

	/**
	 * windowOpened: metodo che apre un pop-up introduttivo all'apertura della finestra
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub	
		String s="Nelle celle e' possibile inserire del testo, dei numeri e delle formule (inserendo il carattere '=' e poi l'espressione),\r\n"+
				"le formule possono avere come operandi: numeri o indici delle celle o entrambi\r\n"+
				"Massimo 2 operandi \r\nOperazione possibili: addizione e sottrazione.";
		JOptionPane.showMessageDialog(f, s, "Foglio di Calcolo", JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub	
	}
}
