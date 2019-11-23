package SystemDesign.DesignPatterns.ObserverPattern.Subscriber;

public interface Observer {
    void updateInterest(float personal,float home);
    void display();
}
