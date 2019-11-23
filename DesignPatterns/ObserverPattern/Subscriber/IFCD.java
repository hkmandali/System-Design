package SystemDesign.DesignPatterns.ObserverPattern.Subscriber;

public class IFCD implements Observer {
    float personalloan = 19.6f;
    float homeloan = 16.6f;
    public void updateInterest(float personal, float home)
    {
        this.personalloan = this.personalloan - personal; // once rbi pushes the changes , we update our interest rates as we are subscribed to it
        this.homeloan = this.homeloan - home;
    }
    public void display()
    {
        System.out.println(" the IFCD interest rates are personal -- "+personalloan +"   home -- "+homeloan);
    }
}
