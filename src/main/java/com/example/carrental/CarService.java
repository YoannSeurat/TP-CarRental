package com.example.carrental;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class CarService {
    private List<Car> cars = this.getListOfCars();

    @GetMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String prettyPrint_listOfCars() {
        StringBuilder out = new StringBuilder("<a href='/' style='text-decoration: none;'>◀️ Back</a> <br> <h2>Available cars to rent :</h2>");
        for (Car car : this.cars) {
            out.append("<ul><a href='/cars/"+car.getPlateNumber()+"'>").append(car.getPlateNumber()).append("</a></ul>");
        }
        return out.toString();
    }

    public List<Car> getListOfCars() {
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            cars.add(new Car());
        }
        return cars;
    }

    @GetMapping("/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String prettyPrint_carInfo(@PathVariable("plateNumber") String plateNumber) {
        Car car;
        try {
            car = getCarFromPlateNumber(plateNumber);
        } catch (Exception e) {
            return "<a href='/cars' style='text-decoration: none;'>◀️ Back</a> <br> <h3>" + e.getMessage() + "</h3>";
        }
        return "<a href='/cars' style='text-decoration: none;'>◀️ Back</a> <br> <h3>Car details</h3> Brand : " + car.getBrand() + "<br> Plate number : " + car.getPlateNumber() + "<br> Price / day : $" + car.getPricePerDay();
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
    public void rent(
            @PathVariable("plateNumber") String plateNumber,
            @RequestParam(value="rent", required = true) boolean rent,
            @RequestBody Dates dates) {

    }
}