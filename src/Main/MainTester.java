package Main;
import Interfaccia.*;
import Classi.*;

/**
 * <p> Title:MainTester </p>
 * <p> Description: al lancio del metodo, si crea un {@link Frame} il quale contiene un {@link Panel}, che permettono 
 *  l'utilizzo di un foglio di calcolo. Il foglio contiene una tabella con un numero prefissato di celle (modificabile),
 *  i valori delle celle sono gestiti tramite un HashMap con chiave String e valore la classe {@link Cella} o sottoclassi
 *  in base ai valori inseriti (guardare package Classi). Nelle celle e' possibile inserire del testo, dei numeri e delle formule (inserendo il carattere '=' e poi l'espressione),
 *  le quali possono avere come operandi: numeri o indici delle celle o entrambi, con max 2 operandi ed operazione possibili: addizione e sottrazione. E' possibile salvare il foglio in un file
 *  binario, anche aprire un foglio salvato in precedenza sempre in un file binario. E' presente anche il salvataggio automatico su 
 *  un file temporaneo, che permette anche di aprirlo attraverso il percorso indicato. E' anche possibile generare un nuovo foglio vuoto. </p>
 * 
 * @author Davide Rivi
 *
 */

public class MainTester{
	/**
	 * main: metodo main.
	 * Serve per creare un oggetto di tipo {@link Frame}, renderlo visibile e impostargli un {@link Terminator}
	 *  
	 * @param args: Argomenti passati da riga di comando (non utilizzato)
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		Frame f=new Frame("Foglio di calcolo");
		f.setVisible(true);
		f.addWindowListener(new Terminator(f));
	}
}
