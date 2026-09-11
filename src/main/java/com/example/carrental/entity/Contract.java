package com.example.carrental.entity;

import jakarta.persistence.*;

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "car_plate")
    private Car car;

    @Embedded
    private Dates dates;

    private boolean active = true;

    public Contract() {}

    public Contract(Person person, Car car, Dates dates) {
        this.person = person;
        this.car = car;
        this.dates = dates;
    }

    public Long getId() { return id; }
    public Person getPerson() { return person; }
    public Car getCar() { return car; }
    public Dates getDates() { return dates; }
    public boolean isActive() { return active; }

    public void setId(Long id) { this.id = id; }
    public void setPerson(Person person) { this.person = person; }
    public void setCar(Car car) { this.car = car; }
    public void setDates(Dates dates) { this.dates = dates; }
    public void setActive(boolean active) { this.active = active; }
}
