package src.MachineCoding.CoinFlipping.MainProgram;

import src.MachineCoding.CoinFlipping.Coin.CoinOption;
import src.MachineCoding.CoinFlipping.CoinGame.CoinGame;
import src.MachineCoding.CoinFlipping.Player.PlayerInfo;

import java.util.Scanner;

// this is the start of the program
public class Driver {
    public static void main(String[] args) {

        // by this everything is abstracted from the end user
        CoinGame cg = new CoinGame();
        cg.addPlayer(new PlayerInfo("hemu"));
        cg.addPlayer(new PlayerInfo("teja"));
        cg.startGame();
    }
}
