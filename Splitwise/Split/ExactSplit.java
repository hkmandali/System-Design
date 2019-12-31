package SystemDesign.Splitwise.Split;

import SystemDesign.Splitwise.User.User;

public class ExactSplit extends Split {
    public ExactSplit(User user, double amount)
    {
        super(user);
        this.setAmount(amount);
    }
}
