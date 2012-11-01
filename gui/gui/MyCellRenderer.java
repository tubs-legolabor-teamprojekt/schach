package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyCellRenderer extends DefaultTableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
		
		JLabel c = (JLabel)super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
		c.setFont(new Font("Arial", Font.BOLD, 20));
		c.setForeground(Color.black);
        c.setVerticalAlignment(JLabel.CENTER);
        c.setHorizontalAlignment(JLabel.CENTER);
        return c;
    }
}
