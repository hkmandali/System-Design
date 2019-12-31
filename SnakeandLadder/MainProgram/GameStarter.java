package SystemDesign.SnakeandLadder.MainProgram;

import SystemDesign.SnakeandLadder.HelperService.Ladder;
import SystemDesign.SnakeandLadder.HelperService.PlayerInfo;
import SystemDesign.SnakeandLadder.HelperService.Snake;
import SystemDesign.SnakeandLadder.MainService.SnakeandLadderService;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
// this class starts the game and only this has the main method
/*
Number of snakes (s) followed by s lines each containing 2 numbers denoting the head and tail positions of the snake.
Number of ladders (l) followed by l lines each containing 2 numbers denoting the start and end positions of the ladder.
Number of players (p) followed by p lines each containing a name
 */
// this model has been implemented by checking from work at tech
public class GameStarter {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("enter num of snakes");
        int num_snakes = in.nextInt();
        List<Snake> snakes = new LinkedList<>();
        for(int i=0;i<num_snakes;++i) // this has the head and the tail of the snake
        {
            snakes.add(new Snake(in.nextInt(),in.nextInt()));// head and tail
        }
        System.out.println("enter num of ladders");
        int num_ladders = in.nextInt();
        List<Ladder> ladders = new LinkedList<>();
        for(int i=0;i<num_ladders;++i)// this has the bottom and top of the ladder
        {
            ladders.add(new Ladder(in.nextInt(),in.nextInt()));// bottom and top
        }

        System.out.println("enter num of players");
        int num_players = in.nextInt();// this has the player info
        List<PlayerInfo> players = new LinkedList<>();
        for(int i=0;i<num_players;++i)
        {
            players.add(new PlayerInfo(in.next()));
        }

        SnakeandLadderService service = new SnakeandLadderService(100);
        service.setLadders(ladders);
        service.setPlayers(players);
        service.setSnakes(snakes);

        service.startGame();
    }
}

/*
sample output



enter num of snakes
2
56 12
76 23
enter num of ladders
2 14 78
25 75
enter num of players
2
hemu
teja
 current players are  2   initital players are 2
value of dice roll 3
player polled is hemu  and size is 1
hemu   rolled  3  positions  and moved from 0  to 3
 current players are  2   initital players are 2
value of dice roll 5
player polled is teja  and size is 1
teja   rolled  5  positions  and moved from 0  to 5
 current players are  2   initital players are 2
value of dice roll 4
player polled is hemu  and size is 1
hemu   rolled  4  positions  and moved from 3  to 7
 current players are  2   initital players are 2
value of dice roll 3
player polled is teja  and size is 1
teja   rolled  3  positions  and moved from 5  to 8
 current players are  2   initital players are 2
value of dice roll 1
player polled is hemu  and size is 1
hemu   rolled  1  positions  and moved from 7  to 8
 current players are  2   initital players are 2
value of dice roll 4
player polled is teja  and size is 1
teja   rolled  4  positions  and moved from 8  to 12
 current players are  2   initital players are 2
value of dice roll 6
player polled is hemu  and size is 1
hemu   rolled  6  positions  and moved from 8  to 78
 current players are  2   initital players are 2
value of dice roll 5
player polled is teja  and size is 1
teja   rolled  5  positions  and moved from 12  to 17
 current players are  2   initital players are 2
value of dice roll 5
player polled is hemu  and size is 1
hemu   rolled  5  positions  and moved from 78  to 83
 current players are  2   initital players are 2
value of dice roll 2
player polled is teja  and size is 1
teja   rolled  2  positions  and moved from 17  to 19
 current players are  2   initital players are 2
value of dice roll 1
player polled is hemu  and size is 1
hemu   rolled  1  positions  and moved from 83  to 84
 current players are  2   initital players are 2
value of dice roll 5
player polled is teja  and size is 1
teja   rolled  5  positions  and moved from 19  to 24
 current players are  2   initital players are 2
value of dice roll 1
player polled is hemu  and size is 1
hemu   rolled  1  positions  and moved from 84  to 85
 current players are  2   initital players are 2
value of dice roll 4
player polled is teja  and size is 1
teja   rolled  4  positions  and moved from 24  to 28
 current players are  2   initital players are 2
value of dice roll 6
player polled is hemu  and size is 1
hemu   rolled  6  positions  and moved from 85  to 91
 current players are  2   initital players are 2
value of dice roll 3
player polled is teja  and size is 1
teja   rolled  3  positions  and moved from 28  to 31
 current players are  2   initital players are 2
value of dice roll 5
player polled is hemu  and size is 1
hemu   rolled  5  positions  and moved from 91  to 96
 current players are  2   initital players are 2
value of dice roll 4
player polled is teja  and size is 1
teja   rolled  4  positions  and moved from 31  to 35
 current players are  2   initital players are 2
value of dice roll 3
player polled is hemu  and size is 1
hemu   rolled  3  positions  and moved from 96  to 99
 current players are  2   initital players are 2
value of dice roll 2
player polled is teja  and size is 1
teja   rolled  2  positions  and moved from 35  to 37
 current players are  2   initital players are 2
value of dice roll 3
player polled is hemu  and size is 1
hemu   rolled  3  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 1
player polled is teja  and size is 1
teja   rolled  1  positions  and moved from 37  to 38
 current players are  2   initital players are 2
value of dice roll 3
player polled is hemu  and size is 1
hemu   rolled  3  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 5
player polled is teja  and size is 1
teja   rolled  5  positions  and moved from 38  to 43
 current players are  2   initital players are 2
value of dice roll 3
player polled is hemu  and size is 1
hemu   rolled  3  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 2
player polled is teja  and size is 1
teja   rolled  2  positions  and moved from 43  to 45
 current players are  2   initital players are 2
value of dice roll 3
player polled is hemu  and size is 1
hemu   rolled  3  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 3
player polled is teja  and size is 1
teja   rolled  3  positions  and moved from 45  to 48
 current players are  2   initital players are 2
value of dice roll 3
player polled is hemu  and size is 1
hemu   rolled  3  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 5
player polled is teja  and size is 1
teja   rolled  5  positions  and moved from 48  to 53
 current players are  2   initital players are 2
value of dice roll 5
player polled is hemu  and size is 1
hemu   rolled  5  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 6
player polled is teja  and size is 1
teja   rolled  6  positions  and moved from 53  to 59
 current players are  2   initital players are 2
value of dice roll 3
player polled is hemu  and size is 1
hemu   rolled  3  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 6
player polled is teja  and size is 1
teja   rolled  6  positions  and moved from 59  to 65
 current players are  2   initital players are 2
value of dice roll 6
player polled is hemu  and size is 1
hemu   rolled  6  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 3
player polled is teja  and size is 1
teja   rolled  3  positions  and moved from 65  to 68
 current players are  2   initital players are 2
value of dice roll 3
player polled is hemu  and size is 1
hemu   rolled  3  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 5
player polled is teja  and size is 1
teja   rolled  5  positions  and moved from 68  to 73
 current players are  2   initital players are 2
value of dice roll 5
player polled is hemu  and size is 1
hemu   rolled  5  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 6
player polled is teja  and size is 1
teja   rolled  6  positions  and moved from 73  to 79
 current players are  2   initital players are 2
value of dice roll 5
player polled is hemu  and size is 1
hemu   rolled  5  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 5
player polled is teja  and size is 1
teja   rolled  5  positions  and moved from 79  to 84
 current players are  2   initital players are 2
value of dice roll 6
player polled is hemu  and size is 1
hemu   rolled  6  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 3
player polled is teja  and size is 1
teja   rolled  3  positions  and moved from 84  to 87
 current players are  2   initital players are 2
value of dice roll 5
player polled is hemu  and size is 1
hemu   rolled  5  positions  and moved from 99  to 99
 current players are  2   initital players are 2
value of dice roll 4
player polled is teja  and size is 1
teja   rolled  4  positions  and moved from 87  to 91
 current players are  2   initital players are 2
value of dice roll 1
player polled is hemu  and size is 1
hemu   rolled  1  positions  and moved from 99  to 100
hemu  ---------------------------------------------------------has won the game
 current players are  1   initital players are 2
 */
