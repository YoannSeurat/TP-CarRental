package com.example.carrental;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CarRepository extends CrudRepository<Car, String> { // Changement en <Car, String>[cite: 5]
    List<Car> findByPlateNumber(String plateNumber);
}