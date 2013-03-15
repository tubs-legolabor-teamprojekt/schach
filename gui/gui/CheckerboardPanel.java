package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import components.Figure;

/**
 * Klasse, die eine Tabellenzelle darstellt.
 * 
 * @author Tabea
 * 
 */
public class CheckerboardPanel extends JPanel
{

    public JLabel label = new JLabel();
    private boolean isFigure = false;

    /**
     * Konstruktor, der eine neue Tabellenzelle erstellt und die entsprechenden
     * Icons anzeigt.
     */
    public CheckerboardPanel() {
        this.add(this.label);
    }

    /**
     * Methode, die das entsprechende Icon anzeigt.
     * @param figure Figure-Objekt
     */
    public void showIcon(Figure figure) {
        this.label.setIcon(figure.getIcon());
        this.label.setVisible(true);
        
        this.isFigure = true;
    }
    
    /**
     * Gett für die boolsche Variable isFigure, die angibt, ob eine Figur
     * auf dem Feld steht. 
     * @return isFigure
     */
    public boolean isFigure() 
    {
        return this.isFigure;
    }
}