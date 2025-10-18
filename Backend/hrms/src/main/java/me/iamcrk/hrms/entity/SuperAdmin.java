package me.iamcrk.hrms.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class SuperAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "admin_id", nullable = false, unique = true)
    private Admin admin;

    private Date assignedDate;
    private Date endDate;
    private boolean active;
}
