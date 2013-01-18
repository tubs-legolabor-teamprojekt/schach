package game;

import java.util.List;

public class Exporter {
    /**
     * Exportiert ein Spiel in das PGN-Format.
     * 
     * @param event
     *            Turnier o.ae. an dem das Spiel stattfand
     * @param site
     *            Ort des Spiels
     * @param date
     *            Datum des Spiels
     * @param namePlayerWhite
     *            Name des Spielers der weissen Figuren
     * @param namePlayerBlack
     *            Name des Spielers der schwarzen Figuren
     * @param result
     *            Ergebnis des Spiels (1-0, 0-1, 0.5-0.5)
     * @param moves
     *            Array von Move-Objekten des Spiels
     * @return String des Spiels im PGN-Format
     */
    public static String exportMovesToPGN(String event, String site, String date, String namePlayerWhite, String namePlayerBlack, String result,
            List<Move> moves) {
        String exportStr = "";

        exportStr += "[Event \"" + event + "\"]\n";
        exportStr += "[Site \"" + site + "\"]\n";
        exportStr += "[Date \"" + date + "\"]\n";
        exportStr += "[Round \"" + moves.size() + "\"]\n";
        exportStr += "[White \"" + namePlayerWhite + "\"]\n";
        exportStr += "[Black \"" + namePlayerBlack + "\"]\n";
        exportStr += "[Result \"" + result + "\"]\n\n";

        int moveCount = 0;
        for (int i = 0; i < moves.size(); i++) {
            if (i % 2 == 0)
                exportStr += "" + ++moveCount + ". ";

            exportStr += moves.get(i).getAlgebraicNotation() + " ";
        }
        exportStr += result;

        return exportStr;
    }
}
