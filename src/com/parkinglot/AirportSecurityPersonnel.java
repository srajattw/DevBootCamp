package com.parkinglot;


import java.util.Observable;
import java.util.Observer;

/**
 * Created by rajats on 6/28/16.
 */
public class AirportSecurityPersonnel implements Observer{
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Security Personnel notified, message="+arg);
    }
}
