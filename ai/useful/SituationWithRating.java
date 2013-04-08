package useful;

import java.util.HashMap;
import java.io.Serializable;

public class SituationWithRating implements Serializable, Comparable<SituationWithRating>{

    private HashMap<Integer, Byte> map;
    private int figureRating;
    private int positionRating;

    /*
     * Wrapped eine HashMap und ihre Bewertung
     * 
     * @param map Hashmap(Schachfeld)
     * 
     * @param rating Bewertung des Schachfeldes
     */
    public SituationWithRating(HashMap<Integer, Byte> map, int figureRating, int positionRating) {
        this.map = map;
        this.figureRating = figureRating;
        this.positionRating = positionRating;
    }

    public HashMap<Integer, Byte> getMap()
    {
        return this.map;
    }

    public int getFigureRating()
    {
        return this.figureRating;
    }
    
    public int getPositionRating()
    {
        return this.figureRating;
    }

    public void setFigureRating(int figureRating)
    {
        this.figureRating = figureRating;
    }
    
    public void setPositionRating(int positionRating)
    {
        this.positionRating = positionRating;
    }
    
    public int compareTo(SituationWithRating o){
        if(this.figureRating == o.getFigureRating()) {
            return (this.positionRating - o.getPositionRating());
        }
        else {
            return (this.figureRating - o.getFigureRating());
        }
    }

    public SituationWithRating clone()
    {
        return new SituationWithRating((HashMap<Integer, Byte>) this.map.clone(), this.figureRating, this.positionRating);
    }

}
