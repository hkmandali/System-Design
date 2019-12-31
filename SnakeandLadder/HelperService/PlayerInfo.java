package SystemDesign.SnakeandLadder.HelperService;
// this class the info reg the players
import java.util.UUID;
public class PlayerInfo {
    private String userid;
    private String name;
    public PlayerInfo(String playername)
    {
        userid = UUID.randomUUID().toString();
        name = playername;
    }

    public String getUserid(){
        return userid;
    }

    public String getName(){
        return name;
    }
}
