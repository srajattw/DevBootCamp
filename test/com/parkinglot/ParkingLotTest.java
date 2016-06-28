package com.parkinglot;

import com.parkinglot.exception.SlotNotAvailableException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Observer;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by rajats on 6/28/16.
 */
public class ParkingLotTest {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldParkMyCarWhenSlotsAvailable() throws SlotNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(2);

        Object parkingToken1 = parkingLot.park();

        Assert.assertNotNull(parkingToken1);

        Object parkingToken2 = parkingLot.park();

        assertNotNull(parkingToken2);
    }


    @Test
    public void shouldNotParkMyCarIfSpaceUnavailable() throws SlotNotAvailableException {


        ParkingLot parkingLot = new ParkingLot(1);

        Object parkingTokenA = parkingLot.park();
        assertNotNull(parkingTokenA);

        exception.expect(SlotNotAvailableException.class);
        exception.expectMessage(ParkingLot.SLOT_UNAVAILABLE_EXCEPTION_MSG);

        parkingLot.park();

    }

    @Test
    public void shouldUnparkMyCarWithValidToken() throws SlotNotAvailableException {
        ParkingLot parkingLot = new ParkingLot(1);
        Object parkingToken = parkingLot.park();
        boolean isUnparked = parkingLot.unPark(parkingToken);
        assertTrue(isUnparked);
    }

    @Test
    public void shouldNotUnparkMyCarWithWrongToken() throws SlotNotAvailableException {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park();
        boolean isUnparked = parkingLot.unPark(new Object());
        assertFalse(isUnparked);
    }

    @Test
    public void shouldNotUnparkMyCarIfTokenAlreadyUsed() throws SlotNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(1);
        Object parkingToken = parkingLot.park();

        boolean isUnparked = parkingLot.unPark(parkingToken);
        assertTrue(isUnparked);

        isUnparked = parkingLot.unPark(parkingToken);
        Assert.assertFalse(isUnparked);

    }

    @Test
    public void shouldNotifyProductOwnerWhenTheParkingLotIsFull() throws SlotNotAvailableException {
        Observer parkingOwner = mock(Observer.class);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addObserver(parkingOwner);
        Object parkingToken = parkingLot.park();
        verify(parkingOwner, times(1)).update(parkingLot,"Parking lot is full");
    }


    @Test
    public void shouldNotNotifyProductOwnerWhenTheParkingLotIsNotFull() throws SlotNotAvailableException {
        Observer parkingOwner = mock(Observer.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(parkingOwner);

        Object parkingToken = parkingLot.park();

        verify(parkingOwner, times(0)).update(parkingLot,"");
    }

    @Test
    public void shouldNotifySecurityPersonnelWhenTheParkingLotIsFull() throws SlotNotAvailableException {
        Observer airportSecurityPersonnel = mock(Observer.class);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addObserver(airportSecurityPersonnel);

        Object parkingToken = parkingLot.park();

        verify(airportSecurityPersonnel, times(1)).update(parkingLot,"Parking lot is full");
    }

    @Test
    public void shouldNotNotifySecurityPersonnelWhenTheParkingLotIsNotFull() throws SlotNotAvailableException {
        Observer airpostSecurityPersonnel = mock(Observer.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(airpostSecurityPersonnel);
        Object parkingToken = parkingLot.park();
        verify(airpostSecurityPersonnel, times(0)).update(parkingLot,"");
    }
}
