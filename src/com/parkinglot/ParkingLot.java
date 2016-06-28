package com.parkinglot;

import com.parkinglot.exception.SlotNotAvailableException;

import java.util.*;

/**
 * Created by rajats on 6/28/16.
 */
public class ParkingLot extends Observable{



    private List<Object> issuedTokens = new LinkedList<>();

    private int capacity;

    public static String SLOT_UNAVAILABLE_EXCEPTION_MSG = "Slot not available";

    public ParkingLot(int capacity) {

        this.capacity = capacity;
    }

    public Object park() throws SlotNotAvailableException {
        if (issuedTokens.size() == capacity) {

            throw new SlotNotAvailableException(SLOT_UNAVAILABLE_EXCEPTION_MSG);
        } else {
            Object token = new Object();
            issuedTokens.add(token);
            if (issuedTokens.size() == capacity) {
                setChanged();
                notifyObservers("Parking lot is full");
            }
            return token;
        }
    }

    public boolean unPark(Object parkingToken) {
        return issuedTokens.remove(parkingToken);
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
}
