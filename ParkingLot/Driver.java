package src.MachineCoding.ParkingLot;
// this is the main program where the execution starts

import src.MachineCoding.ParkingLot.Helper.ParkingSpace;
import src.MachineCoding.ParkingLot.Helper.ParkingSpaceType;
import src.MachineCoding.ParkingLot.Parking.ParkingLot;
import src.MachineCoding.ParkingLot.Vehicle.Vehicle;
import src.MachineCoding.ParkingLot.Vehicle.VehicleType;

import java.util.Scanner;
import java.util.UUID;

// we create an instance of parking lot ,which is a singleton class
// once the instance is created we can maintain parking spaces and park / remove vehicles in that
public class Driver {
    public static void main(String[] args) throws Exception { // may throw exception in add and retrieving
        Vehicle[] vehiclearr = new Vehicle[30];
        for(int i=0;i<30;++i)
        {
            vehiclearr[i] = new Vehicle(VehicleType.values()[i%VehicleType.MAX.ordinal()],UUID.randomUUID().toString());
        }
        ParkingLot pl = ParkingLot.getInstance();
        pl.setCapacity(20); // initial capacity of the parking lot i.e number of parking spaces
        ParkingSpace[] ps = new ParkingSpace[20];
        for(int i=0;i<20;++i)
        {
            ps[i] = new ParkingSpace(ParkingSpaceType.values()[i%ParkingSpaceType.MAX.ordinal()]); // this gives us the values 1 by 1 i.e for each pstype it will populate
        }
        for(ParkingSpace x:ps)
        {
            try {
                pl.addParkingSpace(x);
            }
            catch (Exception e)
            {
                System.out.println("cannot add any more spaces");
            }

        }

        while(true)
        {
            System.out.println("Enter 1 to park a vehicle , 2 to retrieve vehicle");
            Scanner in = new Scanner(System.in);
            int input = in.nextInt();
            switch (input)
            {
                case 1:
                {
                    System.out.println("enter the car index which needs to be parked");
                    int carindex = in.nextInt();
                    pl.parkVehicle(vehiclearr[carindex]);
                    break  ;
                }
                case 2:
                {
                    System.out.println("enter the car index which needs to be retrieved");
                    int carindex = in.nextInt();
                    pl.retrieveVehicle(vehiclearr[carindex]);
                }
                default:
                    break;
            }
        }

    }
}
