// Let us create a banking application with the publisher as RBI and observer as some other bank , whenever RBI changes interest rates ,
// the observer bank interest rates should change accordingly
package SystemDesign.DesignPatterns.ObserverPattern.Publisher;
import SystemDesign.DesignPatterns.ObserverPattern.Subscriber.*;

import java.util.ArrayList;
import java.util.Iterator;

public class RBI implements Publisher {
    float personalloaninterest = 11.9f;
    float homeloaninterest = 9;
    ArrayList<Observer> subscriber = new ArrayList<Observer>(); // as the Observers may be anyone i.e any bank ,
    // we create an interface observer which every other class(observer or bank) should implement
    public void AddBank(Observer x){  // to add any new bank into our list
        subscriber.add(x);
    }
    public void DeleteBank(Observer x){  // to remove any new bank from our list
        subscriber.remove(x);
    }
    public void NotifyBanks(float updatedpersonal,float updatedhome)
    {
        Iterator<Observer> itr = subscriber.iterator();
        while(itr.hasNext())
        {
            itr.next().updateInterest(updatedpersonal,updatedhome);
        }
    }
    public void ChangeInterestRate(float updatedpersonal,float updatedhome)
    {
        personalloaninterest = updatedpersonal;
        homeloaninterest = updatedhome;
        System.out.println(" changing the RBI interest rate to personal -- " + updatedpersonal + "  home -- " +updatedhome);
        NotifyBanks(updatedpersonal,updatedhome);
    }

    public void DisplayBankInterestRate()
    {
        Iterator<Observer> itr = subscriber.iterator();
        while(itr.hasNext())
        {
            itr.next().display();
        }
    }

    public static void main(String[] args)
    {
        RBI obj = new RBI();
        System.out.println(" the current interest rates of RBI are personal -- "+obj.personalloaninterest +"  home -- "+obj.homeloaninterest);
        HFCD hobj = new HFCD();
        IFCD iobj = new IFCD();
        Aisx aobj = new Aisx();
        CICIC cobj = new CICIC();
        obj.AddBank(hobj);
        obj.AddBank(iobj);
        obj.AddBank(aobj);
        obj.AddBank(cobj);
        obj.DisplayBankInterestRate(); // displays the current interest rates of all banks registered with RBI
        obj.ChangeInterestRate(7.8f,8.2f); // once this is changed , it will notify all the subscribers registered with it and they will change accordingly
        obj.DisplayBankInterestRate(); // displays the current interest rates of all banks registered with RBI
        obj.DeleteBank(cobj); // deleting the cicic from our subscribers
        obj.DisplayBankInterestRate(); // displays the current interest rates of all banks registered with RBI
    }
}
