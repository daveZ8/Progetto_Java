package Interfaccia;
import javax.swing.*;
import java.util.*;
import Classi.*;
import java.awt.Color;
import java.awt.event.*;


/**
 * <p> Title:Frame </p>
 * <p> Description: sottoclasse della classe JFrame e implementa la classe astratta ActionListener.
 * Nella classe viene creato un Menu' con diversi item, tutti associati ad un evento, viene inserito 
 * anche un {@link Panel}, un {@link GestioneFile} e un {@link AutoSave}.  </p>
 * 
 * @author Davide Rivi
 * 
 */

public class Frame extends JFrame implements ActionListener{
	/**
	 * @serial versione
	 */
	private static final long serialVersionUID = 1L;
	
	/** elementi della barra del menu'*/	 
	private JMenuItem m1,m2,m3; 
	
	/** Label che comunica il nome del foglio*/
	private JLabel l1;
	
	/**Label per visualizzare il percorso del file temporaneo*/
	private JLabel l2;
	
	/** oggetto di classe {@link Panel} aggiunto al Frame*/	
	private	Panel p; 
	
	/** struttura dati di tipo Map per memorizzare i dati della tabella*/
	protected Map<String,Cella> map; 
	
	/** oggetto della classe {@link GestioneFile} utilizzato per le operazioni su file*/
	protected GestioneFile gf; 
	
	/** Thread utilizzato per il salvataggio automatico, inizializzato con classe {@link AutoSave}*/
	protected AutoSave autoSave; 
	
	
	/**
	 * Costruttore: metodo per impostare il Frame e inserire gli elementi dell'interfaccia.
	 * E' composto da un JPanel di tipo {@link Panel}, ed da un JMenu su cui sara' possibile scegliere diverse funzioni da svolgere.
	 * Viene definita ed inizializzata la struttra dati di tipo Map con implementazione
	 * HashMap per poi essere passata agli altri costruttori. 
	 * 
	 * @param s stringa per assegnare il titolo al pannello Frame.
	 */
	public Frame(String s)
	{
		super(s);
		setBounds(200,100,1200,650);	
		
		/**
		 * Variabile utilizzata nel progetto, per contenere la classe {@link Cella}.
		 * 
		 * viene implementata con un hashmap per la comodita' ed il tempo (O(1)) di accesso per ogni elemento, sia per modificarlo
		 * che per ottnerlo. Come chiave viene utilizzata una Stringa che corrisponde all'unione del nome della colonna + indice della riga,
		 * il valore e' la classe di tipo cella che poi diventera' una sua sottoclasse in base al tipo di dato inseriti nella tabella.
		 */
		map=new HashMap<String,Cella>();
		inizializza();
		
		p=new Panel(map);
		
		/**
		 * Oggetto JScrollPane utilizzato per usare gli scroll nel panel
		 */
		JScrollPane pane=new JScrollPane(p);
		add(pane);
		
		
		l1=new JLabel("Foglio non salvato");
		l1.setOpaque(true);
		l1.setForeground(Color.red);
		
		l2=new JLabel("");
		l2.setOpaque(true);
		l2.setForeground(Color.GRAY);
		
		
		m1=new JMenuItem("Salva");
		m2=new JMenuItem("Apri");
		m3=new JMenuItem("Nuovo");
		
		JMenuBar mb=new JMenuBar();
		JMenu mf=new JMenu("File");
		
		mf.add(m1);
		mf.add(m2);
		mf.add(m3);
		
		mb.add(mf);
		mb.add(l1);
		mb.add(new JLabel("    "));
		mb.add(l2);
	
		setJMenuBar(mb);
		
		m1.addActionListener(this);
		m2.addActionListener(this);
		m3.addActionListener(this);	
		
		gf=new GestioneFile();
		
		autoSave=new AutoSave(map);
		autoSave.start();	
		
		l2.setText("File tmp salvato in: "+autoSave.getTmp().getAbsoluteFile());
	}
	
	/**
	 * actionPerformed: metodo che permette l'utilizzo di eventi svolti nel frame.
	 * In base al JMenuItem selezionato svolge l'operazione di salvataggio({@link #m1}), aprire un file esistente({@link #m2})
	 * , aprire un nuovo file({@link #m3}), imposta il testo di {@link #l1} in base al nome del foglio, 
	 * il colore in base allo stato: se il testo e' verde il foglio e' salvato su file, se e' rosso non e' salvato, se e' arancione si sta utilizzando
	 * un foglio salvato su file temporaneo. 
	 * 
	 * @param e parametro che ascolta gli eventi nel frame
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==m1)
		{	
			gf.salva(this,map);
			if(gf.getFileName().equals(""))
			{
				l1.setText("Foglio non salvato");
				l1.setForeground(Color.red);
			}
			else
			{
				l1.setText(gf.getFileName());
				l1.setForeground(Color.GREEN);
			}
				
		}
		if(e.getSource()==m2)
		{
			Map<String,Cella> map2=(gf.importa(this,map));
			if(map2!=null && !map2.equals(map))
			{
				aggiorna(map2);
				p.getT().setModel(new MyTableModel(map,false));
				Panel.getL1().setText("Fx: ");
				p.updateUI();
				p.getT().updateUI();
				l1.setText(gf.getFileName());
				if(l1.getText().endsWith(".tmp"))
					l1.setForeground(Color.ORANGE);
				else
					l1.setForeground(Color.GREEN);
			}					
		}
		if(e.getSource()==m3)
		{
			map.clear();
			inizializza();
			p.getT().setModel(new MyTableModel(map,false));
			Panel.getL1().setText("Fx: ");
			p.updateUI();
			p.getT().updateUI();
			l1.setText("Foglio non salvato");
			l1.setForeground(Color.red);

		}
				
	}
	
	
	/**
	 * inizializza: metodo che inizializza la struttura dati {@link #map} vuota.
	 */
	public void inizializza()
	{
		for(int i=1; i<MyTableModel.nameCol.length;i++)
		{
			for(int j=0;j<MyTableModel.MAXROW;j++)
				map.put(MyTableModel.key(i,j), new Cella(null));
		}
	}

	
	/**
	 * aggiorna: metodo che aggiorna la struttura dati.
	 * 
	 * Sostituisce i valori della {@link #map} attuale con quella importata dal file aperto.
	 * Nel caso di formule tra celle reistanzia quelle celle.
	 * 
	 * 
	 * @param m struttura dati di tipo Map importata da un file aperto.
	 */
	public void aggiorna(Map<String,Cella> m)
	{
		for(int i=1; i<MyTableModel.nameCol.length;i++)
		{
			for(int j=0;j<MyTableModel.MAXROW;j++)
			{
				String key=MyTableModel.key(i, j);

				if(m.get(key) instanceof Formule2)
				{
					String formula=m.get(key).toString();
					p.getT().setValueAt(formula, j, i);
				}
				else
					map.replace(key, m.get(key));
			}		
		}
	}

	/**
	 * @return the m1
	 */
	public JMenuItem getM1() {
		return m1;
	}

	/**
	 * @param m1 the m1 to set
	 */
	public void setM1(JMenuItem m1) {
		this.m1 = m1;
	}

	/**
	 * @return the m2
	 */
	public JMenuItem getM2() {
		return m2;
	}

	/**
	 * @param m2 the m2 to set
	 */
	public void setM2(JMenuItem m2) {
		this.m2 = m2;
	}

	/**
	 * @return the m3
	 */
	public JMenuItem getM3() {
		return m3;
	}

	/**
	 * @param m3 the m3 to set
	 */
	public void setM3(JMenuItem m3) {
		this.m3 = m3;
	}

	/**
	 * @return the l1
	 */
	public JLabel getL1() {
		return l1;
	}

	/**
	 * @param l1 the l1 to set
	 */
	public void setL1(JLabel l1) {
		this.l1 = l1;
	}

	/**
	 * @return the l2
	 */
	public JLabel getL2() {
		return l2;
	}

	/**
	 * @param l2 the l2 to set
	 */
	public void setL2(JLabel l2) {
		this.l2 = l2;
	}

	/**
	 * @return the p
	 */
	public Panel getP() {
		return p;
	}

	/**
	 * @param p the p to set
	 */
	public void setP(Panel p) {
		this.p = p;
	}

	/**
	 * @return the map
	 */
	public Map<String, Cella> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, Cella> map) {
		this.map = map;
	}

	/**
	 * @return the gf
	 */
	public GestioneFile getGf() {
		return gf;
	}

	/**
	 * @param gf the gf to set
	 */
	public void setGf(GestioneFile gf) {
		this.gf = gf;
	}

	/**
	 * @return the autoSave
	 */
	public AutoSave getAutoSave() {
		return autoSave;
	}

	/**
	 * @param autoSave the autoSave to set
	 */
	public void setAutoSave(AutoSave autoSave) {
		this.autoSave = autoSave;
	}
}
