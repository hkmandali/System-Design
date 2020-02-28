package src.MachineCoding.ParkingLot.Parking;

import src.MachineCoding.ParkingLot.Helper.ParkingSpace;
import src.MachineCoding.ParkingLot.Helper.ParkingSpaceType;
import src.MachineCoding.ParkingLot.Vehicle.Vehicle;
import src.MachineCoding.ParkingLot.Vehicle.VehicleType;

import java.util.ArrayList;
import java.util.HashMap;

public class ParkingLot {
    private static ParkingLot obj = null; // making it singleton
    private static int capacity;
    public ArrayList<ParkingSpace> vehicleMap;
    private static int filledspots;
    private ParkingLot(){

    }

    public void addParkingSpace(ParkingSpace s) throws Exception
    {
        if(vehicleMap.size() == capacity)
        {
            System.out.println("cannot more parking spaces already filled up");
            throw new Exception("enough number of parking spaces already");
        }
        vehicleMap.add(s);
    }

    public static ParkingLot getInstance(){
        if(obj == null)
        {
            return new ParkingLot();
        }
        else
            return obj;
    }

    public void setCapacity(int x)
    {
        filledspots = 0;
        capacity =x;
        vehicleMap = new ArrayList<>(capacity);
    }

    public int getCapacity()
    {
        return capacity;
    }

    int getFilledspots()
    {
        return filledspots;
    }

    public void parkVehicle(Vehicle v) throws Exception// this method returns the location of the vehicle parked // parking type is not added as of now , can be added in the future
    {
        System.out.println("filledspots is "+filledspots +"  capacity is "+capacity);
        if(filledspots == getCapacity())
        {
            System.out.println(" parking lot is full , no more entries");
            throw new Exception("theres no more place");
        }
        boolean parked = false;
        for(ParkingSpace x: vehicleMap)
        {
            if(!x.isOccupied() && !parked) // found an empty parking space
            {
                parked = true;
                x.setOccupied(true); // making it to occupied
                x.setNum(v);
            }
        }
        filledspots++;
        return;
    }

    boolean retrieveVehicle(String pillarid) // retrieving based on the location
    {

        filledspots--;
        return false;
    }

    public String retrieveVehicle(Vehicle regNo) throws Exception // retrieving  based on the reg number of the vehicle
    {
        if(filledspots <= 0)
        {
            System.out.println(" parking lot empty");
            throw new Exception("theres no vehicle parked");
        }
        for(ParkingSpace x:vehicleMap)
        {
            if(x.isOccupied()) // checking only the occupied spots
            {
                if(x.getNum().equals(regNo))
                {
                    x.setOccupied(false);
                    String ret = x.getId();
                    x.getId();
                    System.out.println("found at "+ret);
                    return ret;
                }
            }
        }
        filledspots--;
        return null;
    }

    boolean findNearest(String type)
    {
        return false;
    }
}
