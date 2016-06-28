package com.parkinglot;

import com.parkinglot.exception.SlotsNotAvailableException;

import java.util.*;

/**
 * Created by rajats on 6/28/16.
 */
public class ParkingLot {

    private List<Object> issuedTokens = new LinkedList<>();

    private int capacity;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public Object park() throws SlotsNotAvailableException {
        if (issuedTokens.size() == capacity) {
            throw new SlotsNotAvailableException("Slot not available");
        } else {
            Object token = new Object();
            issuedTokens.add(token);
            return token;
        }
    }

    public boolean unPark(Object parkingToken) {
        return issuedTokens.remove(parkingToken);
    }
}
