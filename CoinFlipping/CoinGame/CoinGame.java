package src.MachineCoding.CoinFlipping.CoinGame;

import src.MachineCoding.CoinFlipping.Coin.CoinInfo;
import src.MachineCoding.CoinFlipping.Coin.CoinOption;
import src.MachineCoding.CoinFlipping.Player.PlayerInfo;

import java.util.*;

public class CoinGame {
    List<PlayerInfo> players;
    CoinInfo coin;
    public CoinGame()
    {
        players = new LinkedList<>();
        coin = new CoinInfo();
    }

    public void addPlayer(PlayerInfo x)
    {
        players.add(x);
    }

    public void startGame() throws IllegalArgumentException
    {
        while(true)
        {
            System.out.println(" which player wants to choose the option and what is the option ");
            // here the player is expected to choose the option properly be it heads or tails , if he doesnt choose from these two the program will not run correctly
            Scanner s= new Scanner(System.in);
            String name = s.next();
            String option = s.next();
            Iterator itr = players.listIterator();
            boolean playerfound = false;
            while(itr.hasNext())
            {
                PlayerInfo p = (PlayerInfo) itr.next();
                if(p.getName().equalsIgnoreCase(name))
                {
                    p.setoption(CoinOption.valueOf(option.toUpperCase()));
                    playerfound = true; // breaking once we find the player
                    break;
                }
            }

            if(!playerfound)
            {
                System.out.println("entered player not in the list of players");
                throw new IllegalArgumentException(" player not specified properly");
            }

            int outcome = new Random().nextInt(2); // this will return some value from 0 to 2[upperbound] i.e equal probabilities of zero and 1
            System.out.println("outcome of coin toss is "+ CoinOption.values()[outcome].toString());
            if(CoinOption.values()[outcome] ==  CoinOption.valueOf(option.toUpperCase()))
            {
                System.out.println(name +"  has won by choosing "+option);
            }
            else
            {
                System.out.println(name +"  has lost  by choosing "+option);
            }

        }
    }


}
