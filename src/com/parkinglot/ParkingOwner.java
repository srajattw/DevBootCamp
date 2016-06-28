package com.parkinglot;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by rajats on 6/28/16.
 */
public class ParkingOwner implements Observer{

    @Override
    public void update(Observable o, Object arg) {
        // display
        System.out.println("Parking owner notified , message="+arg);
    }
}
