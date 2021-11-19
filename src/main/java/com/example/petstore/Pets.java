package com.example.petstore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pets")
public class Pets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private long petId;
    
    @Column(name = "name")
    private String name;

    @Column(name="age")
    private String age;

    @Column(name = "type")
    private String type;


//    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    @JoinTable(name = "petType", joinColumns = @JoinColumn(referencedColumnName = "pet_id", nullable = false),
//            inverseJoinColumns = @JoinColumn(referencedColumnName = "type_id", nullable = false))
//    Set<Type> types = new HashSet<>();

    public Pets() {
    }

    public Pets(String name, String age, String type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
