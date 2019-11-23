package src.DesignPatterns.SingleTon;

/*
public class Singleton {
    int test; // we'll be checking the value of this num
    private static Singleton obj;
    private Singleton() {
    }
    public static  Singleton getInstance() // synchronized , so that only one of them can access at a time
    {
        if(obj == null)
            obj = new Singleton();
        return obj;
    }

}
*/
// second approach , make the getInstance method synchronized so that only one thread can access at a time

/*
public class Singleton {
    int test; // we'll be checking the value of this num
    private static Singleton obj;
    private Singleton() {
    }
    public static synchronized Singleton getInstance() // synchronized , so that only one of them can access at a time
    {
        if(obj == null)
            obj = new Singleton();
        return obj;
    }

}
*/
// third approach , instantiate obj directly so that there is no need to check thread synchronization and JVM will directly load it as it is static

public class Singleton {
    int test =1; // we'll be checking the value of this num
    private static Singleton obj= new Singleton();
    private Singleton() {
    }
    public static Singleton getInstance()
    {
        return obj;
    }

}

