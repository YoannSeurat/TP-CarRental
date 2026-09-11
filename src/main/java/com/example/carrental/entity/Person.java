package com.example.carrental.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Entity
public class Person {
    @Id
    private String name;
    private String email;
    private int age;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Contract> contracts = new ArrayList<>();

    @Transient
    final List<String> sampleNames = Arrays.asList("Yoann", "Mathieu", "Daif", "Rayane", "Sidney", "Tonio", "Jeremy", "Paul", "Srab", "Clayton");
    @Transient
    final Random random = new Random();

    public Person() {
        this.name = sampleNames.get(random.nextInt(sampleNames.size()));
        this.email = this.name.toLowerCase() + "@efrei.net";
        this.age = 18 + random.nextInt(50);
    }

    public Person(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public int getAge() {
        return age;
    }

    public List<Contract> getContracts() { return contracts; }

    public void addContract(Contract c) {
        this.contracts.add(c);
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
