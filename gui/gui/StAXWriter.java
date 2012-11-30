package gui;

import game.Exporter;
import game.GameCoordinator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Klasse, die das aktuelle Spiel bei Bedarf in eine .txt-Datei abspeichert. 
 * @author Tabea
 *
 */
public class StAXWriter
{
	
	/**
	 * Methode, die das aktuelle Spiel bzw. dessen PGN-Format 
	 * in eine .txt-Datei abspeichert. Um die einzelnen Dateien zu unterscheiden
	 * wird ein Zeitstempel mit in den Dateinamen gebaut.
	 * @return true, wenn die Datei abgespeichert wurde, ansonsten false
	 */
	public boolean makeFile() {
		
		// Datum in richtiges Format bringen
		SimpleDateFormat sdf = new SimpleDateFormat();
		SimpleDateFormat sdf_wTime = new SimpleDateFormat();
	    sdf.applyPattern( "dd.MM.yyyy" );
	    sdf_wTime.applyPattern( "dd-MMM-yyyy_HH-mm-ss" );
		
		try {
			// Inhalt der .txt-Datei
			String text = Exporter.exportMovesToPGN("Teamprojekt", "Legolabor", sdf.format(new Date()),
					StartWindow.getInstance().getUsername(), "Legoroboter", "result", 
					GameCoordinator.getInstance(false).getAllMoves());
			// Name der Datei
			File file = new File("saveGame/chess" + sdf_wTime.format(new Date()) + ".txt");
			FileWriter fw = new FileWriter(file);
			
			fw.write(text);
			fw.flush();
			fw.close();
			
			return true;
			
		} catch (IOException e) {}
		
		return false;
	}
}
