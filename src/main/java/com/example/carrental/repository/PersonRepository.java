package com.example.carrental.repository;

import com.example.carrental.entity.Person;
import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<Person, String> {
}
