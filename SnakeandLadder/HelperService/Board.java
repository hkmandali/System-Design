package SystemDesign.SnakeandLadder.HelperService;
// this class has all the info reg the snake and ladder board

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private int size;
    private List<Snake> snakes;
    private List<Ladder> ladders;
    private Map<String,Integer> players;

    public Board(int size){
        this.size = size;
        this.ladders = new ArrayList<>();
        this.players = new HashMap<>();
        this.snakes = new ArrayList<>();
    }
    // getters and setters
    public int getSize(){
        return this.size;
    }

    public List<Snake> getSnakes(){
        return this.snakes;
    }

    public List<Ladder> getLadders(){
        return this.ladders;
    }

    public Map<String,Integer> getPlayers(){
        return this.players;
    }

    public void setSnakes(List<Snake> s)
    {
        this.snakes =s;
    }

    public void setLadders(List<Ladder> l)
    {
        this.ladders = l;
    }

    public void setPlayers(Map<String,Integer> p) // this has the info reg the current position of the players
    {
        this.players= p;
    }
}
