package com.example.carrental.service;

import com.example.carrental.repository.CarRepository;
import com.example.carrental.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarRentalService {
    CarRepository carRepository;

    @Autowired
    public CarRentalService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void addRandomCars(int amount) {
        for (int i = 0; i < amount; i++) {
            addCar(new Car());
        }
    }
    public void addCar(Car car) {
        carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return (List<Car>) carRepository.findAll();
    }

    public Car getCarByPlateNumber(String plateNumber) throws Exception {
        Optional<Car> car = carRepository.findById(plateNumber);
        if(car.isPresent()) {
            return car.get();
        }
        throw new IllegalArgumentException("There is no car with such plate number");
    }
}