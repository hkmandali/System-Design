package SystemDesign.SnakeandLadder.HelperService;
// this class has the info reg the snakes
public class Snake {
    private int head;
    private int tail;
    public Snake(int start, int end)
    {
        head = start;
        tail = end;
    }

    public int getHead(){
        return head;
    }

    public int getTail(){
        return tail;
    }
}
