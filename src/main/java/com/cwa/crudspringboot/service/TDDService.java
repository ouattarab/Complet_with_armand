package com.cwa.crudspringboot.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TDDService {
    private static  final double BIKE_RATE = 1.5;
    private static  final double CAR_RATE = 3;
    private static  final double TRUCK_RATE = 5;

    public enum VehicleType {
        BIKE, CAR, TRUCK
    }
    /*
     * Write a methode to calculate fare for a parking slot
     * according to in time, out time and vehicle type (BIKE,CAR,TRUCK)
     *Each vehicle has its own rate: BIKE -> 1,5 euro/h, CAR -> 3euro/h, TRUCK -> 5 euro/h
     *The first 30 minutes are free for all vehicles
     *After 12 hours, a discount of 5 % should be applied
     *After 24 hours, a discount of 10 % should be applied
     */

    public double calculateFare(LocalDateTime inTime, LocalDateTime outTime, VehicleType vehicleType) {
        double duration = inTime.until(outTime, ChronoUnit.MINUTES);

        if(duration <= 30) return 0;

        double fare = switch (vehicleType) {
            case BIKE -> (duration * BIKE_RATE) / 60;
            case CAR -> (duration * CAR_RATE) / 60;
            case TRUCK ->(duration * TRUCK_RATE) / 60;
        };

        if (duration  >= 720 && duration < 1440){
            return fare - (fare * 0.05);
        }
        if ( duration >=  1440){
            return fare - (fare * 0.1);
        }

        return fare;


    }




}
