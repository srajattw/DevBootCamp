package com.parkinglot;

import com.parkinglot.exception.InvalidParkingTokenException;
import com.parkinglot.exception.SlotNotAvailableException;

import java.util.*;

/**
 * Created by rajats on 6/28/16.
 */
public class ParkingLot {

    public static String SLOT_UNAVAILABLE_EXCEPTION_MSG = "Slot not available";

    private final int capacity;

    private final String parkingLotNumber ;

    private Map<ParkingToken,Car> issuedTokens = new HashMap<>();

    private List<ParkingLotObserver> observers = new ArrayList<>();

    public String getParkingLotNumber() {
        return parkingLotNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingLot that = (ParkingLot) o;

        return parkingLotNumber != null ? parkingLotNumber.equals(that.parkingLotNumber) : that.parkingLotNumber == null;

    }

    @Override
    public int hashCode() {
        return parkingLotNumber != null ? parkingLotNumber.hashCode() : 0;
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.parkingLotNumber = "#"+System.nanoTime();
    }

    public ParkingToken park(Car car) throws SlotNotAvailableException {
        if (issuedTokens.size() == capacity) {
            throw new SlotNotAvailableException(SLOT_UNAVAILABLE_EXCEPTION_MSG);
        } else {

            ParkingToken token = new ParkingToken(car.getRegistrationNumber(),parkingLotNumber);
            issuedTokens.put(token,car);
            if (issuedTokens.size() == capacity) {
               notifyParkingSpaceFull();
            }
            return token;
        }
    }



    private void notifyParkingSpaceFull() {

        for (ParkingLotObserver observer : observers) {
            observer.onParkingSpaceFull();
        }

    }

    private void notifyParkingSpaceAvailable() {

        for (ParkingLotObserver observer : observers) {
            observer.onParkingSpaceAvailable();
        }

    }

    public Car unPark(Object parkingToken) throws InvalidParkingTokenException {

        boolean wasFullBeforeUnPark = isFull();
        boolean isValidToken =  issuedTokens.containsKey(parkingToken);
        if (!isValidToken) {
            throw new InvalidParkingTokenException("Parking Token is invalid");
        } else {
            Car car = issuedTokens.remove(parkingToken);
            if (wasFullBeforeUnPark) {
                notifyParkingSpaceAvailable();
            }
            return car;
        }
    }

    public boolean isFull() {
        return capacity == issuedTokens.size();
    }


    public synchronized void addObserver(ParkingLotObserver parkingLotObserver) {
        observers.add(parkingLotObserver);
    }


}
