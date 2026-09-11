package com.example.carrental.service;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Dates;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
public class CarService {
    @Autowired
    private CarRentalService carRentalService;

    @GetMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Car> listOfCars() {
        return carRentalService.getAllCars();
    }

    @GetMapping("/view/cars")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String prettyPrint_listOfCars() {
        StringBuilder out = new StringBuilder("<a href='/' style='text-decoration: none;'>◀️ Back</a> <br> <h2>Available cars to rent :</h2>");
        for (Car car : carRentalService.getAllCars()) {
            out.append("<ul><a href='/view/cars/").append(car.getPlateNumber()).append("'>").append(car.getPlateNumber()).append("</a></ul>");
        }
        return out.toString();
    }

    @GetMapping("/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Car aCar(@PathVariable("plateNumber") String plateNumber) throws Exception {
        return carRentalService.getCarByPlateNumber(plateNumber);
    }

    @GetMapping("/view/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String prettyPrint_carInfo(@PathVariable("plateNumber") String plateNumber) {
        Car car;
        try {
            car = carRentalService.getCarByPlateNumber(plateNumber);
        } catch (Exception e) {
            return "<a href='/view/cars' style='text-decoration: none;'>◀️ Back</a> <br> <h3>" + e.getMessage() + "</h3>";
        }
        String rentInfo = "<br> Rented : " + car.isRented();
        if (car.isRented() && car.getRentedDates() != null) {
            rentInfo += "<br> From : " + car.getRentedDates().getBegin() + " To : " + car.getRentedDates().getEnd();
        }
        return "<a href='/view/cars' style='text-decoration: none;'>◀️ Back</a> <br> <h3>Car details</h3> Brand : " + car.getBrand() + "<br> Plate number : " + car.getPlateNumber() + "<br> Price / day : $" + car.getPricePerDay() + rentInfo;
    }

    @PutMapping(value = "/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void rent(
            @PathVariable("plateNumber") String plateNumber,
            @RequestParam(value="rent", required = true) boolean rent,
            @RequestBody(required = false) Dates dates) throws Exception {
        Car car = carRentalService.getCarByPlateNumber(plateNumber);
        car.setRented(rent);
        if (rent) {
            car.setRentedDates(dates);
        }
        carRentalService.addCar(car);
    }
}