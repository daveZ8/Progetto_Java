package Interfaccia;
import Classi.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


/**
 * <p> Title:Panel </p>
 * <p> Description: sottoclasse della classe JPanel e implementa la classe astratta ActionListener e MouseListener.
 * Nella classe vengono inseriti diversi oggetti della GUI per le varie operazioni,
 * con alcuni associati ad un evento.
 * Viene settato con il Layout GroupLayout per la disposizione degli oggetti  </p>
 * 
 * @author Davide Rivi
 * 
 */
public class Panel extends JPanel implements ActionListener,MouseListener{
	/**
	 * @serial versione
	 */
	private static final long serialVersionUID = 1L;
	
	/** Bottoni per scegliere se visualizzare le formule o il risultato */
	private JRadioButton b1,b2; 
	
	 /** Casella di testo usata per inserimento dei valori delle celle*/
	private JTextField txt1;
	
	/** Etichetta che indica quale cella e' selezionata, inizialmente ha il valore fx.
	 * definita static per modificarne il colore in caso di eccezione in {@link Formule2#risultato()} 
	 * e segnalare la cella con errore.
	 */
	private static JLabel l1; 
	
	/** struttura dati di tipo Map per memorizzare i dati della tabella*/
	private Map<String,Cella> m;
	
	/** Modello per la costruzione della tabella*/
	private MyTableModel dataModel; 
	
	/** Tabella di tipo JTable*/
	private JTable t; 
	
	/** Header di {@link #t} */
	private JTableHeader th; 
	
	
	/**
	 * Panel: costruttore.
	 * Viene passato un oggetto Map.
	 * Inizializza e crea il JPanel.
	 * 
	 * @param m: oggetto tipo Map inizializzato con HashMap
	 */
	public Panel(Map<String,Cella> m)
	{
		super();
		
		GroupLayout gl=new GroupLayout(this);
		setLayout(gl);
		
		
		this.m=m;
		
		l1=new JLabel("Fx: ");
		add(l1);
		l1.setOpaque(true);
		
		txt1=new JTextField(30);
		txt1.setMaximumSize(new Dimension(350,25));
		txt1.addActionListener(this);
		add(txt1);
		
		
		b1=new JRadioButton("Risultato");
		b2=new JRadioButton("Formule");
		ButtonGroup grp=new ButtonGroup();
		b1.setSelected(true);
		grp.add(b1); 
		grp.add(b2);
		b1.addActionListener(this);
		b2.addActionListener(this);
		add(b1); 
		add(b2);
		
		dataModel=new MyTableModel(m,false);
		t=new JTable(dataModel);
		th=new JTableHeader(t.getColumnModel());		
		t.addMouseListener(this);
		add(th);
		add(t);		
				
		
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);
		
		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(l1).addComponent(txt1).addComponent(b1).addComponent(b2)
				).addComponent(th).addComponent(t));
		
		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(gl.createSequentialGroup().addComponent(l1).addComponent(txt1)
				.addComponent(b1).addComponent(b2))
				.addComponent(th).addComponent(t)));
	}
	

	/**
	 * actionPerformed: metodo per gestire gli eventi nel {@link Panel}.
	 * 
	 * Se gli eventi sono generati da {@link #b1} o {@link #b2} cambia il {@link #dataModel} della tabella e visualizza
	 *  o i valori o le formule delle celle interessate, se l'evento e' generato da {@link #txt1} imposta il valore del testo nella cella selezionata col mouse.
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		// TODO Auto-generated method stub
		if(e.getSource()==b1)
		{
			dataModel=new MyTableModel(m,false);
			t.setModel(dataModel);
		}
		else if(b2.isSelected())
		{
			dataModel=new MyTableModel(m,true);
			t.setModel(dataModel);
		}		
		
		
		if(e.getSource()==txt1)
		{
			try {
				if(t.getSelectedColumn()>0)	
				{
					dataModel.setValueAt(txt1.getText(),t.getSelectedRow(),t.getSelectedColumn());
					t.updateUI();
					this.updateUI();
				}
				txt1.setText("");
			}catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Errore ActionEvent","Errore",JOptionPane.ERROR_MESSAGE);	
			}
			
		}
	}
	
	/**
	 * mouseClicked: metodo che imposta il testo di {@link #txt1} al valore della cella selezionata.
	 * 
	 * In caso di una cella di classe {@link Formule} o {@link Formule2} stampa la formula e non il risultato.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		try 
		{
			int row=t.rowAtPoint(e.getPoint());
			int col=t.columnAtPoint(e.getPoint());
			String testo="";
			if(e.getSource()==t && col>0)
			{
				testo=m.get(MyTableModel.key(col,row)).toString();
				l1.setText(MyTableModel.key(col, row));
			}
			
			if(col>0)
			{
				if(testo.equals("null"))
					txt1.setText("");
				else
					txt1.setText(testo);
			}
					
					
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Errore MouseEvent","Errore",JOptionPane.ERROR_MESSAGE);
		}	
		
	}
	
	
	/**
	 * @return the l1
	 */
	public static JLabel getL1() {
		return l1;
	}

	/**
	 * @param l1 the l1 to set
	 */
	public static void setL1(JLabel l1) {
		Panel.l1 = l1;
	}

	/**
	 * @return the t
	 */
	public JTable getT() {
		return t;
	}


	/**
	 * @param t the t to set
	 */
	public void setT(JTable t) {
		this.t = t;
	}
	

	/**
	 * @return the b1
	 */
	public JRadioButton getB1() {
		return b1;
	}


	/**
	 * @param b1 the b1 to set
	 */
	public void setB1(JRadioButton b1) {
		this.b1 = b1;
	}


	/**
	 * @return the b2
	 */
	public JRadioButton getB2() {
		return b2;
	}


	/**
	 * @param b2 the b2 to set
	 */
	public void setB2(JRadioButton b2) {
		this.b2 = b2;
	}


	/**
	 * @return the txt1
	 */
	public JTextField getTxt1() {
		return txt1;
	}


	/**
	 * @param txt1 the txt1 to set
	 */
	public void setTxt1(JTextField txt1) {
		this.txt1 = txt1;
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


	/**
	 * @return the dataModel
	 */
	public MyTableModel getDataModel() {
		return dataModel;
	}


	/**
	 * @param dataModel the dataModel to set
	 */
	public void setDataModel(MyTableModel dataModel) {
		this.dataModel = dataModel;
	}


	/**
	 * @return the th
	 */
	public JTableHeader getTh() {
		return th;
	}


	/**
	 * @param th the th to set
	 */
	public void setTh(JTableHeader th) {
		this.th = th;
	}

	
/*-----------------------------------------------------------------------------------------------------------------------
 * -----------------------------------------------metododi inutilizzati--------------------------------------------------
 -----------------------------------------------------------------------------------------------------------------------*/
	


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
