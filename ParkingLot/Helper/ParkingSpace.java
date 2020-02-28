package src.MachineCoding.ParkingLot.Helper;
import src.MachineCoding.ParkingLot.Vehicle.Vehicle;

import java.util.UUID;
public class ParkingSpace {
    boolean occupied;
    Vehicle num; // vehicle details reg no of the car present in the parking lot
    String id; // id of the parking location vix pillar no
    ParkingSpaceType type; // type of the parking location

    public ParkingSpace(ParkingSpaceType psType)
    {
        type =psType;
        occupied = false;
        id = UUID.randomUUID().toString();
        num = null;
    }
    public void setNum(Vehicle number)
    {
        this.num = number;
    }
    public Vehicle getNum() // number of the car in the parking space
    {
        return num;
    }

    public boolean isOccupied() // if occupied or not
    {
        return occupied;
    }

    public void setId(String location)
    {
        id = location;
    }
    public void setOccupied(boolean value) // sets to occupied or not
    {
        this.occupied = value;
    }

    public ParkingSpaceType getType() // type of the parking space
    {
        return type;
    }

    public String getId() // location of the parking space
    {
        return id;
    }

}
