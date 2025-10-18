package me.iamcrk.hrms.entity;

import jakarta.persistence.*;

@Entity
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;
    private String occasion;
    private Double amount;

}
