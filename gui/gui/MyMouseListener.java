package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

public class MyMouseListener extends MouseAdapter
{
	
	private JTable grid;
	
	private ArrayList<Integer> a = new ArrayList<Integer>();
	
	private int row = 0,
				column = 0,
				counter = 0;
	
	public MyMouseListener(JTable grid) 
	{
		this.grid = grid;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		this.counter++;

		if (Checkerboard.getInstance().isManualMove() && this.counter < 3) {
				this.row = this.grid.rowAtPoint(e.getPoint());
				this.column = this.grid.columnAtPoint(e.getPoint());
				int fieldnumber = Checkerboard.convertIntoFieldNumber(this.row, this.column);
				this.a.add(fieldnumber);
				System.out.println(this.counter);
				if (this.counter == 2) {
					int reply = javax.swing.JOptionPane.showConfirmDialog(Gui.getInstance(),"Sind folgende Angaben " +
							"korrekt?\nFeld von: " + this.a.get(0) + "\nFeld nach: " + 
							this.a.get(1), "Normaler Zug", JOptionPane.YES_NO_OPTION);
					this.counter = 0;
					if (reply == JOptionPane.YES_OPTION) {
						Checkerboard.getInstance().setArrayList(this.a);
						Checkerboard.getInstance().setManualMove(false);
					} else {
						this.a.clear();
						javax.swing.JOptionPane.showMessageDialog(Gui.getInstance(),"Bitte erneute Zueingabe!", 
								"Normaler Zug", JOptionPane.INFORMATION_MESSAGE);
					}
					
					
					
				}
		} else {
			System.out.println("hahah geht nicht");
		}
	}
}

