package me.iamcrk.hrms.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "rated_by", nullable = false)
    private Employee ratedBy; // Department Head

    private LocalDate date;
    private Integer score; // 1–5
    private String comments;
}
