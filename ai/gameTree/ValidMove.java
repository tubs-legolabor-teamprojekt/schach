package gameTree;

import java.util.LinkedList;

import components.Field;


import util.ChessfigureConstants;
import util.ExtractInformationFromBinary;

public class ValidMove {
	
	/**
	 * Erstellt ein 64er-Array.
	 * Der Index (0-63) entspricht der Feldnummer (1-64).
	 * Der Wert ist entweder die Farbe (0, 1) oder ein leeres Feld (-1)
	 * @param situation
	 * @return
	 */
	public static byte[] getUsedFields(short[] situation)
	{
		/*
		 *  Alle 64 Felder des Schachfelds, mit folgender Information:
		 *  -1=leer, byte-Wert=entsprechende Farbe
		 */
		byte[] fieldsUsed = new byte[64];
		for (int i = 0; i < fieldsUsed.length; i++)
			fieldsUsed[i] = -1;
		
		for (short sh : situation) {
			// Farbe der gelesenen Figur
			byte color = ExtractInformationFromBinary.getColor(sh);
			byte xPos = ExtractInformationFromBinary.getXPosition(sh);
			byte yPos = ExtractInformationFromBinary.getYPosition(sh);
			int pos = Field.getFieldNumber(xPos, yPos); // (1-64)
			
			fieldsUsed[--pos] = color;
		}
		
		return fieldsUsed;
	}
	
	
	public static LinkedList<short[]> pawnCheck(short[] situation){
		
		byte[] usedFields = getUsedFields(situation);
		
		LinkedList<short[]> childSituation = new LinkedList<short[]>();
		
		// Schleife über alle gefundenen Bauern im short[] situation
		for (int j = 0; j < situation.length; j++) {
			
			// Figur zwischenspeichern
			short figure = situation[j];
			
			// Für jeden gefundenen Bauern
			if (ExtractInformationFromBinary.getFigure(figure) == ChessfigureConstants.PAWN) {
				
				// Information auslesen und speichern? (Position <X,Y> , Farbe)
				byte xPos = ExtractInformationFromBinary.getXPosition(figure); // (1-8)
				byte yPos = ExtractInformationFromBinary.getYPosition(figure); // (1-8)
				byte color = ExtractInformationFromBinary.getColor(figure);
				byte xPosNew = 0;
				byte yPosNew = 0;
				
				// Alle vier Möglichkeiten, den Bauern zu versetzen
				for(int i = 0 ; i < 4 ; i++ ){
					// TODO Oben rechts/links nur wenn Gegner auf entsprechendem Feld
					
					// Neue Situation ---> short[] newSituation = new short[32] <--- erstellen. 32 Felder angenommenes Maximum.
					short[] newSituation = situation.clone();
					
					// Bauer versetzen (ausgelesene Position verändern, neuen Short-Wert erstellen)
					int pos;
					switch(i){
					case 0 : // Bauer geht nach oben links
						//TODO nur, wenn auf den Feldern ein Gegner steht
						xPosNew = (byte) (xPos-1);
						yPosNew = (byte )(yPos+1);
						pos = (Field.getFieldNumber(xPosNew, yPosNew)-1);
						System.out.println(usedFields[pos]);
						if (	!(
									(color == 0 && usedFields[pos] == 1) ||
									(color == 1 && usedFields[pos] == 0)
								)
							) {
							continue;
						}
						// Geschmissene Figur entfernen
						break;
					case 1 : // Bauer geht eins nach oben
						xPosNew = xPos;
						yPosNew = (byte) (yPos+1);
						break;
					case 2 : // Bauer geht nach oben rechts
						//TODO nur, wenn auf den Feldern ein Gegner steht
						xPosNew = (byte) (xPos+1);
						yPosNew = (byte )(yPos+1);
						pos = (Field.getFieldNumber(xPosNew, yPosNew)-1);
						if (	!(
									(color == 0 && usedFields[pos] == 1) ||
									(color == 1 && usedFields[pos] == 0)
								)
							) {
							continue;
						}
						// Geschmissene Figur entfernen
						break;
					case 3 : // Bauer geht zwei nach vorne
						//Falls xPosition == 2
						if(yPos == 2){
							xPosNew = xPos;
							yPosNew = (byte) (yPos+2);
						}else{ 
							continue; 
						}
						break;
					}
					// War der Zug legal? Feldgrenzen beachten
					if( xPosNew < 1 || xPosNew > 8 || yPosNew < 1 || yPosNew > 8){
						continue;
					}
					
					// versetzten Bauern in newSituation einbinden (alten Bauern ersetzen)
					short newPawn = ChessfigureConstants.makeFigureShort(color, ChessfigureConstants.PAWN, xPosNew, yPosNew);
					newSituation[j]= newPawn;
					
					// newSituation an LinkedList hinten anfügen
					childSituation.add(newSituation);
				}
			}
			
		}
		
		
		return childSituation;
		
	}
		
}
