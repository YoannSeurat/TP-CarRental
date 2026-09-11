package com.example.carrental.service;

import com.example.carrental.entity.Contract;
import com.example.carrental.entity.Dates;
import com.example.carrental.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class PersonService {
    @Autowired
    private PersonRentalService personRentalService;

    @GetMapping("/persons")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Person> listOfPersons() {
        return personRentalService.getAllPersons();
    }

    @PostMapping("/persons/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public void generatePersons(@RequestParam(value = "amount", required = false, defaultValue = "5") int amount) {
        personRentalService.addRandomPersons(amount);
    }

    @GetMapping("/view/persons")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String prettyPrint_listOfPersons() {
        StringBuilder out = new StringBuilder("<a href='/' style='text-decoration: none;'>◀️ Back</a> <br> <h2>Registered persons :</h2>");
        for (Person p : personRentalService.getAllPersons()) {
            out.append("<ul><a href='/view/persons/").append(p.getName()).append("'>").append(p.getName()).append("</a></ul>");
        }
        return out.toString();
    }

    @GetMapping("/persons/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Person personInfo(@PathVariable("name") String name) throws Exception {
        return personRentalService.getPersonById(name);
    }

    @GetMapping("/view/persons/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String prettyPrint_personInfo(@PathVariable("name") String name) {
        Person person;
        try {
            person = personRentalService.getPersonById(name);
        } catch (Exception e) {
            return "<a href='/view/persons' style='text-decoration: none;'>◀️ Back</a> <br> <h3>" + e.getMessage() + "</h3>";
        }
        StringBuilder out = new StringBuilder("<a href='/view/persons' style='text-decoration: none;'>◀️ Back</a> <br> <h3>Person details</h3>");
        out.append("Name: ").append(person.getName()).append("<br>");
        out.append("Email: ").append(person.getEmail()).append("<br>");
        out.append("Age: ").append(person.getAge()).append("<br>");
        out.append("<h4>Contracts:</h4>");
        if (person.getContracts() == null || person.getContracts().isEmpty()) {
            out.append("No contracts yet");
        } else {
            out.append("<ul>");
            for (Contract c : person.getContracts()) {
                out.append("<li><a href='/view/cars/").append(c.getCar().getPlateNumber()).append("'>").append(c.getCar().getPlateNumber()).append("</a>");
                if (c.getDates() != null) {
                    out.append(" - From: ").append(c.getDates().getBegin()).append(" to ").append(c.getDates().getEnd());
                }
                out.append(" - Active: ").append(c.isActive()).append("</li>");
            }
            out.append("</ul>");
        }
        return out.toString();
    }

    @PutMapping(value = "/persons/{name}/rent")
    @ResponseStatus(HttpStatus.OK)
    public String rent(
            @PathVariable("name") String name,
            @RequestParam(value = "plate", required = true) String plate,
            @RequestBody(required = false) Dates dates) throws Exception {
        try {
            personRentalService.rentCar(name, plate, dates);
            return "Successfully rented " + plate + " for " + name;
        } catch (Exception e) {
            return "Error, could not rent " + plate + " for " + name + " : " + e.getMessage();
        }
    }
}
