package com.cwa.crudspringboot.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TDDServiceTest {
    private  TDDService tddService = new TDDService();

    @Test
    void testCalculateFareBike(){
        //Arrange
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime = LocalDateTime.now().plusHours(2);

        //Act
        double fare = tddService.calculateFare(inTime,outTime, TDDService.VehicleType.BIKE);

        //Assert
        assertEquals(3,fare);
    }

    @Test
    void testCalculateFareCar(){
        //Arrange
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime = LocalDateTime.now().plusHours(2);

        //Act
        double fare = tddService.calculateFare(inTime,outTime, TDDService.VehicleType.CAR);

        //Assert
        assertEquals(6,fare);
    }

    @Test
    void testCalculateFareTRUCK(){
        //Arrange
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime = LocalDateTime.now().plusHours(2);

        //Act
        double fare = tddService.calculateFare(inTime,outTime, TDDService.VehicleType.TRUCK);

        //Assert
        assertEquals(10,fare);
    }

    @Test
    void testCalculateFareTRUCKUnder30minutes(){
        //Arrange
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime = LocalDateTime.now().plusMinutes(29);

        //Act
        double fare = tddService.calculateFare(inTime,outTime, TDDService.VehicleType.TRUCK);

        //Assert
        assertEquals(0,fare);
    }

    @Test
    void testCalculateFareTRUCKUpper30minutes(){
        //Arrange
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime = LocalDateTime.now().plusMinutes(45);

        //Act
        double fare = tddService.calculateFare(inTime,outTime, TDDService.VehicleType.TRUCK);

        //Assert
        assertEquals(3.75,fare);
    }

    @Test
    void testCalculateFareBike12hoursDiscount(){
        //Arrange
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime = LocalDateTime.now().plusHours(12);

        //Act
        double fare = tddService.calculateFare(inTime,outTime, TDDService.VehicleType.BIKE);

        //Assert
        assertEquals(17.1,fare);
    }

    @Test
    void testCalculateFareCAR24hoursDiscount(){
        //Arrange
        LocalDateTime inTime = LocalDateTime.now();
        LocalDateTime outTime = LocalDateTime.now().plusHours(24);

        //Act
        double fare = tddService.calculateFare(inTime,outTime, TDDService.VehicleType.CAR);

        //Assert
        assertEquals(64.8,fare);
    }

}