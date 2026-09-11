package com.example.carrental.entity;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Entity
public class Car {
    @Id
    private String plateNumber;
    private int pricePerDay;
    private String brand;
    private boolean isRented;
    @Embedded
    private Dates rentedDates;

    @Transient
    final List<String> brands = Arrays.asList("Volvo", "Peugeot", "BMW", "Mercedes", "Toyota", "Volkswagen");
    @Transient
    final Random random = new Random();

    public Car() {
        this.plateNumber = this.generateRandomPlateNumber();
        this.pricePerDay = this.generateRandomPricePerDay();
        this.brand = this.getRandomBrand();
        this.isRented = false;
    }

    public String getPlateNumber() {
        return plateNumber;
    }
    public int getPricePerDay() {
        return pricePerDay;
    }
    public String getBrand() {
        return brand;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        this.isRented = rented;
        if (!rented) {
            this.rentedDates = null;
        }
    }

    public Dates getRentedDates() {
        return rentedDates;
    }

    public void setRentedDates(Dates rentedDates) {
        this.rentedDates = rentedDates;
    }

    private String generateRandomPlateNumber() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 90; // letter 'Z'
        int targetStringLength = 6;
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public int generateRandomPricePerDay() {
        int min = 50;
        int max = 200;
        return random.nextInt(min, max+1);
    }

    public String getRandomBrand() {
        return brands.get(random.nextInt(6));
    }
}