package SystemDesign.DesignPatterns.ObserverPattern.Publisher;

import SystemDesign.DesignPatterns.ObserverPattern.Subscriber.Observer;

public interface Publisher {
    void AddBank(Observer x);
    void DeleteBank(Observer x);
    void NotifyBanks(float updatedpersonal,float updatedhome);
    void ChangeInterestRate(float updatedpersonal,float updatedhome);
}
