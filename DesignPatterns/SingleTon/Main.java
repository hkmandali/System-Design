package src.DesignPatterns.SingleTon;
import src.DesignPatterns.SingleTon.Singleton;

public class Main {
    public static void main(String[] args)
    {
        Singleton obj = Singleton.getInstance();
        System.out.println("the value pof obj test is "+obj.test);
        obj.test = 2;
        Singleton obj2 = Singleton.getInstance();
        obj2.test = 12;
        System.out.println("obj is "+obj+"   obj2 is "+obj2); // both of them hold the same reference

        System.out.println("the value pof obj2 test is "+obj2.test);
        System.out.println("the value pof obj test is "+obj.test);

    }
}
