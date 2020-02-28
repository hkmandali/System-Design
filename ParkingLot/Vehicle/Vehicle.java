package src.MachineCoding.ParkingLot.Vehicle;

public class Vehicle {
    VehicleType type;
    String regNo; // regNo of the car
    public Vehicle(VehicleType type, String num)
    {
        this.regNo=num;
        this.type = type;
    }
    public VehicleType getType()
    {
        return type;
    }
    public String getRegNo()
    {
        return regNo;
    }
    public void setType(VehicleType vType)
    {
        this.type =  vType;
    }
    public void setRegNo(String number)
    {
       this.regNo = number;
    }
}
