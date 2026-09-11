package com.example.carrental.repository;

import com.example.carrental.entity.Car;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CarRepository extends CrudRepository<Car, String> { // Changement en <Car, String>[cite: 5]
    List<Car> findByPlateNumber(String plateNumber);
}