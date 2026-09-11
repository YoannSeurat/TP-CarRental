package com.example.carrental.service;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Contract;
import com.example.carrental.entity.Dates;
import com.example.carrental.entity.Person;
import com.example.carrental.repository.ContractRepository;
import com.example.carrental.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PersonRentalService {
    PersonRepository personRepository;
    ContractRepository contractRepository;
    CarRentalService carRentalService;

    @Autowired
    public PersonRentalService(PersonRepository personRepository, ContractRepository contractRepository, CarRentalService carRentalService) {
        this.personRepository = personRepository;
        this.contractRepository = contractRepository;
        this.carRentalService = carRentalService;
    }

    public void addRandomPersons(int amount) {
        for (int i = 0; i < amount; i++) {
            addPerson(new Person());
        }
    }

    public void addPerson(Person p) {
        personRepository.save(p);
    }

    public List<Person> getAllPersons() {
        return (List<Person>) personRepository.findAll();
    }

    public Person getPersonById(String name) throws Exception {
        Optional<Person> p = personRepository.findById(name);
        if (p.isPresent()) return p.get();
        throw new IllegalArgumentException("There is no person with such name");
    }

    public Contract rentCar(String personId, String plateNumber, Dates dates) throws Exception {
        Person person = getPersonById(personId);
        Car car = carRentalService.getCarByPlateNumber(plateNumber);
        if (car.isRented()) {
            throw new IllegalArgumentException("Car is already rented");
        }
        car.setRented(true);
        car.setRentedDates(dates);
        carRentalService.addCar(car);
        Contract contract = new Contract(person, car, dates);
        contractRepository.save(contract);
        // keep person aware of contract in memory and persistence via cascade on Person side
        person.addContract(contract);
        personRepository.save(person);
        return contract;
    }
}
