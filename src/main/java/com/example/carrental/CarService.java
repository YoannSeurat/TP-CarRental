package com.example.carrental;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class CarService {
    private List<Car> cars = this.generateListOfCars();

    @GetMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Car> listOfCars() {
        return this.cars;
    }

    @GetMapping("/view/cars")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String prettyPrint_listOfCars() {
        StringBuilder out = new StringBuilder("<a href='/' style='text-decoration: none;'>◀️ Back</a> <br> <h2>Available cars to rent :</h2>");
        for (Car car : this.cars) {
            out.append("<ul><a href='/view/cars/").append(car.getPlateNumber()).append("'>").append(car.getPlateNumber()).append("</a></ul>");
        }
        return out.toString();
    }

    public List<Car> generateListOfCars() {
        List<Car> listofcars = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            listofcars.add(new Car());
        }
        return listofcars;
    }

    @GetMapping("/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Car aCar(@PathVariable("plateNumber") String plateNumber) throws Exception {
        return getCarFromPlateNumber(plateNumber);
    }

    @GetMapping("/view/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String prettyPrint_carInfo(@PathVariable("plateNumber") String plateNumber) {
        Car car;
        try {
            car = getCarFromPlateNumber(plateNumber);
        } catch (Exception e) {
            return "<a href='/view/cars' style='text-decoration: none;'>◀️ Back</a> <br> <h3>" + e.getMessage() + "</h3>";
        }
        String rentInfo = "<br> Rented : " + car.isRented();
        if (car.isRented() && car.getRentedDates() != null) {
            rentInfo += "<br> From : " + car.getRentedDates().getBegin() + " To : " + car.getRentedDates().getEnd();
        }
        return "<a href='/view/cars' style='text-decoration: none;'>◀️ Back</a> <br> <h3>Car details</h3> Brand : " + car.getBrand() + "<br> Plate number : " + car.getPlateNumber() + "<br> Price / day : $" + car.getPricePerDay() + rentInfo;
    }

    public Car getCarFromPlateNumber(String plateNumber) throws Exception{
        for (Car car : this.cars) {
            if (Objects.equals(car.getPlateNumber(), plateNumber)) {
                return car;
            }
        }
        throw new IllegalArgumentException("There is no car with such plate number");
    }

    @PutMapping(value = "/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void rent(
            @PathVariable("plateNumber") String plateNumber,
            @RequestParam(value="rent", required = true) boolean rent,
            @RequestBody(required = false) Dates dates) throws Exception {
        Car car = getCarFromPlateNumber(plateNumber);
        car.setRented(rent);
        if (rent) {
            car.setRentedDates(dates);
        }
    }
}