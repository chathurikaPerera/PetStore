package com.example.petstore;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PetRepository extends CrudRepository<Pets, Long> {

     Optional<Pets> findByName(String name);

     List<Pets> findByType(String petType);


}
