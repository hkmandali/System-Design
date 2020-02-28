package src.MachineCoding.CoinFlipping.Player;

import src.MachineCoding.CoinFlipping.Coin.CoinOption;

public class PlayerInfo {
    String name;
    CoinOption option;
    public PlayerInfo(String s)
    {
        name =s;
        option = null;
    }
    public void setName(String s)
    {
        name =s;
    }
    public void setoption(CoinOption x)
    {
        option = x;
    }
    public void setoption(int x)
    {
        option = CoinOption.values()[x];
    }

    public String getName()
    {
        return name;
    }

    public CoinOption getOption()
    {
        return option;
    }


}
