package SystemDesign.Splitwise.Split;

import SystemDesign.Splitwise.User.User;

public class PercentSplit extends Split {
    double percent;

    public PercentSplit(User user, double percent)
    {
        super(user);
        this.percent = percent;
    }

    public double getPercent()
    {
        return this.percent;
    }

    public void setPercent(double percent)
    {
        this.percent = percent;
    }
}
