package SystemDesign.SnakeandLadder.HelperService;
// this class has the info reg the ladders
public class Ladder {
    private int bottom;
    private int top;
    public Ladder(int start, int end)
    {
        bottom = start;
        top = end;
    }

    public int getBottom(){
        return bottom;
    }

    public int getTop(){
        return top;
    }
}
