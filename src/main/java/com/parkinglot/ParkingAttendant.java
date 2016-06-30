package com.parkinglot;

import com.parkinglot.exception.InvalidParkingTokenException;
import com.parkinglot.exception.SlotNotAvailableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajats on 6/29/16.
 */
public class ParkingAttendant {

    private Map<String,ParkingLot> assignedParkingLots = new HashMap<>();


    public ParkingToken acceptCar(Car car) throws SlotNotAvailableException {

        ParkingToken token = null;
        boolean isSpaceAvailable = false;
        for(ParkingLot parkingLot : assignedParkingLots.values()){
           if(!parkingLot.isFull()){
               isSpaceAvailable = true;
               token = parkingLot.park(car);
           }
        }

        if(!isSpaceAvailable){
            throw new SlotNotAvailableException("All Parking Lots are full");
        }

        return token;
    }

    public void assignParkingLot(ParkingLot parkingLot) {
        assignedParkingLots.put(parkingLot.getParkingLotNumber() , parkingLot);
    }

    public Car returnCar(ParkingToken parkingToken) throws InvalidParkingTokenException {
        if(assignedParkingLots.containsKey(parkingToken.getParkingLotNumber())){
            ParkingLot parkingLot = assignedParkingLots.get(parkingToken.getParkingLotNumber());
            return parkingLot.unPark(parkingToken);
        }else{
            throw new InvalidParkingTokenException("Token is not valid");
        }
    }
}
