package com.parkinglot;

import com.parkinglot.exception.InvalidParkingTokenException;
import com.parkinglot.exception.SlotNotAvailableException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by rajats on 6/28/16.
 */
public class ParkingLotTest {

    public static final String CAR_REGISTRATION_NUMBER = "xyz1234";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ParkingLotObserver parkingLotObserver;

    private Car car ;

    @Before
    public void setup(){
        parkingLotObserver = mock(ParkingLotObserver.class);
        car = mock(Car.class);
        when(car.getRegistrationNumber()).thenReturn(CAR_REGISTRATION_NUMBER);
    }



    @Test
    public void shouldParkMyCarWhenSlotsAvailable() throws SlotNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(2);

        Object parkingToken1 = parkingLot.park(car);

        Assert.assertNotNull(parkingToken1);

        Object parkingToken2 = parkingLot.park(car);

        assertNotNull(parkingToken2);
    }


    @Test
    public void shouldNotParkMyCarIfSpaceUnavailable() throws SlotNotAvailableException {


        ParkingLot parkingLot = new ParkingLot(1);

        Object parkingTokenA = parkingLot.park(car);
        assertNotNull(parkingTokenA);

        expectedException.expect(SlotNotAvailableException.class);
        expectedException.expectMessage(ParkingLot.SLOT_UNAVAILABLE_EXCEPTION_MSG);

        parkingLot.park(car);

    }

    @Test
    public void shouldUnparkMyCarWithValidToken() throws SlotNotAvailableException, InvalidParkingTokenException {
        ParkingLot parkingLot = new ParkingLot(1);
        ParkingToken parkingToken = parkingLot.park(car);
        Car unparkedCar = parkingLot.unPark(parkingToken);
        Assert.assertEquals(car,unparkedCar);
        Assert.assertEquals(CAR_REGISTRATION_NUMBER,parkingToken.getVehicleNumber());
    }

    @Test
    public void shouldNotUnparkMyCarWithWrongToken() throws SlotNotAvailableException, InvalidParkingTokenException {
        expectedException.expect(InvalidParkingTokenException.class);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park(car);
        parkingLot.unPark(new Object());
    }

    @Test
    public void shouldNotUnparkMyCarIfTokenAlreadyUsed() throws SlotNotAvailableException, InvalidParkingTokenException {

        expectedException.expect(InvalidParkingTokenException.class);

        ParkingLot parkingLot = new ParkingLot(1);
        Object parkingToken = parkingLot.park(car);

        parkingLot.unPark(parkingToken);

        parkingLot.unPark(parkingToken);

    }

    @Test
    public void shouldNotifyProductOwnerWhenTheParkingLotIsFull() throws SlotNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addObserver(parkingLotObserver);
        Object parkingToken = parkingLot.park(car);
        verify(parkingLotObserver, times(1)).onParkingSpaceFull();
    }


    @Test
    public void shouldNotNotifyProductOwnerWhenTheParkingLotIsNotFull() throws SlotNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(parkingLotObserver);

        Object parkingToken = parkingLot.park(car);

        verify(parkingLotObserver, times(0)).onParkingSpaceFull();
    }

    @Test
    public void shouldNotifySecurityPersonnelWhenTheParkingLotIsFull() throws SlotNotAvailableException {

        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addObserver(parkingLotObserver);

        Object parkingToken = parkingLot.park(car);

        verify(parkingLotObserver, times(1)).onParkingSpaceFull();
    }

    @Test
    public void shouldNotNotifySecurityPersonnelWhenTheParkingLotIsNotFull() throws SlotNotAvailableException {
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(parkingLotObserver);
        Object parkingToken = parkingLot.park(car);
        verify(parkingLotObserver, times(0)).onParkingSpaceFull();
    }


    @Test
    public void shouldNotifyObserverWhenUnparkAndParkingSlotWasFull() throws SlotNotAvailableException, InvalidParkingTokenException {

        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addObserver(parkingLotObserver);
        Object parkingToken = parkingLot.park(car);

        parkingLot.unPark(parkingToken);

        verify(parkingLotObserver, times(1)).onParkingSpaceAvailable();
    }


    @Test
    public void shouldNotNotifyObserverWhenUnparkAndParkingSlotWasNotFull() throws SlotNotAvailableException, InvalidParkingTokenException {

        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(parkingLotObserver);
        Object parkingToken = parkingLot.park(car);

        parkingLot.unPark(parkingToken);

        verify(parkingLotObserver, times(0)).onParkingSpaceAvailable();
    }


}
