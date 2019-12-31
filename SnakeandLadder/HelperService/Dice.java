package SystemDesign.SnakeandLadder.HelperService;

import java.util.Random;

// this class generates a random number between 1-6 and serves as a dice
public class Dice {
    public static int roll()
    {
        return new Random().nextInt(6) +1; // this gives a number from 1 to 6
    }
}
