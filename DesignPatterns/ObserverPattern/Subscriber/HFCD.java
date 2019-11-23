package SystemDesign.DesignPatterns.ObserverPattern.Subscriber;

public class HFCD implements Observer {
    float personalloan = 20.6f;
    float homeloan = 15.6f;
    public void updateInterest(float personal, float home)
    {
        this.personalloan = this.personalloan - personal; // once rbi pushes the changes , we update our interest rates as we are subscribed to it
        this.homeloan = this.homeloan - home;
    }
    public void display()
    {
        System.out.println(" the HDFC interest rates are personal -- "+personalloan +"   home -- "+homeloan);
    }
}
