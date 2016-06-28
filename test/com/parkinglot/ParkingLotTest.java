package com.parkinglot;

import com.parkinglot.exception.SlotsNotAvailableException;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by rajats on 6/28/16.
 */
public class ParkingLotTest {


    @Test
    public void shouldParkMyCarWhenSlotsAvailable() throws SlotsNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(2);

        Object parkingToken1 = parkingLot.park();

        Assert.assertNotNull(parkingToken1);

        Object parkingToken2 = parkingLot.park();

        assertNotNull(parkingToken2);
    }



    @Test(expected = SlotsNotAvailableException.class)
    public void shouldNotParkMyCarIfSpaceUnavailable() throws SlotsNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(1);

        Object parkingTokenA = parkingLot.park();
        assertNotNull(parkingTokenA);

        parkingLot.park();
    }

    @Test
    public void shouldUnparkMyCarWithValidToken() throws SlotsNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(1);
        Object parkingToken = parkingLot.park();
        boolean isUnparked = parkingLot.unPark(parkingToken);
        assertTrue(isUnparked);
    }

    @Test
    public void shouldNotUnparkMyCarWithWrongToken() throws SlotsNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park();
        boolean isUnparked = parkingLot.unPark(new Object());
        assertFalse(isUnparked);
    }

    @Test
    public void shouldNotUnparkMyCarIfTokenAlreadyUsed() throws SlotsNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(1);
        Object parkingToken = parkingLot.park();

        boolean isUnparked = parkingLot.unPark(parkingToken);
        assertTrue(isUnparked);

        isUnparked = parkingLot.unPark(parkingToken);
        Assert.assertFalse(isUnparked);

    }

}
