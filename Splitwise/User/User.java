package SystemDesign.Splitwise.User;

public class User {
    private String userid;
    private String name;
    private String phone;

    public User(String id,String name, String phone)
    {
        this.userid = id;
        this.name = name;
        this.phone = phone;
    }

    public String getUserid()
    {
        return userid;
    }

    public void setUserid(String id)
    {
        this.userid = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

}
