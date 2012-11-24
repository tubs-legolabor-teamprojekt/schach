package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
				a.add(fieldnumber);
				System.out.println(this.counter);
		} else {
			System.out.println("hahah geht nicht");
		}
	}
	
	public int getRow() 
	{
		return this.row;
	}
	
	public int getColumn()
	{
		return this.column;
	}
}

