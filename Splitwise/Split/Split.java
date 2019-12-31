package SystemDesign.Splitwise.Split;

import SystemDesign.Splitwise.User.User;

public class Split {
    private User user;
    private double amount;

    public Split(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return this.user;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public double getAmount()
    {
        return this.amount;
    }
}
