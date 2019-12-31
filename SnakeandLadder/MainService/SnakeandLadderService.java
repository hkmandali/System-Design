package SystemDesign.SnakeandLadder.MainService;

import SystemDesign.SnakeandLadder.HelperService.*;

import java.util.*;

// this is the main service which is responsible for the game , our main method will call this service and this takes care of everything
public class SnakeandLadderService {
    private Board snakeandLadderBoard;
    private int initialplayers;
    private Queue<PlayerInfo> players; // this will make sure that the players are restricted to the service and not to the board

    private boolean gamefinished;

    private int numDices;
    private boolean shouldgamecontinuetillLastPlayer;
    private boolean shouldAllowMultipleDicesatSix;

    private static final int DEFAULT_BOARD_SIZE =100;
    private static final int DEFAULT_NUM_DICES =1;

    public SnakeandLadderService(int boardsize)
    {
        this.snakeandLadderBoard = new Board(boardsize);
        this.players = new LinkedList<PlayerInfo>();
        this.numDices = DEFAULT_NUM_DICES;
    }

    public SnakeandLadderService(){
        this(DEFAULT_BOARD_SIZE);
    }


    public void setNumDices(int numDice)
    {
        this.numDices = numDice;
    }

    public void setShouldgamecontinuetillLastPlayer(boolean shouldallow)
    {
        this.shouldAllowMultipleDicesatSix = shouldallow;
    }

    public void setShouldAllowMultipleDicesatSix(boolean shouldAllow)
    {
        this.shouldAllowMultipleDicesatSix = shouldAllow;
    }

    public void setPlayers(List<PlayerInfo> players)
    {
        this.players = new LinkedList<>();
        this.initialplayers = players.size();
        Map<String, Integer> playerPieces = new HashMap<String, Integer>();
        for (PlayerInfo player : players) {
            this.players.add(player);
            playerPieces.put(player.getUserid(), 0); //Each player has a piece which is initially kept outside the board (i.e., at position 0).
        }
        snakeandLadderBoard.setPlayers(playerPieces); //  Add pieces to board

    }

    public void setSnakes(List<Snake> snakes)
    {
        snakeandLadderBoard.setSnakes(snakes);
    }

    public void setLadders(List<Ladder> ladders)
    {
        snakeandLadderBoard.setLadders(ladders);
    }

    // this method checks for snakes or ladders and checks them accordingly
    private int getNewPositionthroughsnakesorLadders(int newpos)
    {
        int prevpos;
        do{
            prevpos = newpos;
            for(Snake snake : snakeandLadderBoard.getSnakes())
            {
                if(snake.getHead() == newpos)
                    newpos = snake.getTail();
            }

            for(Ladder l: snakeandLadderBoard.getLadders())
            {
                if(l.getBottom() == newpos)
                    newpos = l.getTop();
            }
        }
        while (newpos != prevpos); // because this needs to be checked atleast once
        return newpos;

    }

    public void move(PlayerInfo player,int dicevalue) // this will move the player with the dicevalue
    {
        int oldpos = snakeandLadderBoard.getPlayers().get(player.getUserid()); // this gives us the name of the user which in turn gives the pos at which he currently is
        int newpos = oldpos + dicevalue;

        int maxsizeofboard = snakeandLadderBoard.getSize();

        if(newpos > maxsizeofboard)
            newpos = oldpos;
        else
        {
            newpos = getNewPositionthroughsnakesorLadders(newpos);
        }
        snakeandLadderBoard.getPlayers().put(player.getUserid(),newpos);
        System.out.println(player.getName()+"   rolled  "+dicevalue+"  positions  and moved from "+oldpos+"  to "+newpos);

    }

    private int dicerollvalue(){
        return Dice.roll();
    }

    private boolean hasPlayerWon(PlayerInfo s)
    {
        int playerpos = snakeandLadderBoard.getPlayers().get(s.getUserid());
        int winningIndex = snakeandLadderBoard.getSize();
        if(playerpos == winningIndex)
            return true;
        return false;

    }

    private boolean isGamefinished(){
        int curr_players = players.size();
        //System.out.println(" current players are  "+curr_players +"   initital players are "+initialplayers);
        return curr_players < initialplayers;
    }

    public void startGame()
    {
        while(!isGamefinished()) // game will be finished if there are less number of players than the original ,and this can be seen be line 141
            // it wont get executed it a player has won
        {
            int diceValue = dicerollvalue();
           // System.out.println("value of dice roll "+diceValue);
            PlayerInfo currentplayer = players.poll(); // this will remove the player from the queue and add him to the last
            //System.out.println("player polled is "+currentplayer.getName() +"  and size is "+players.size());
            move(currentplayer,diceValue);
            if(hasPlayerWon(currentplayer))
            {
                System.out.println(currentplayer.getName()+"  ---------------------------------------------------------has won the game");
                snakeandLadderBoard.getPlayers().remove(currentplayer.getName());
            }
            else
                players.add(currentplayer);
        }
    }


}
