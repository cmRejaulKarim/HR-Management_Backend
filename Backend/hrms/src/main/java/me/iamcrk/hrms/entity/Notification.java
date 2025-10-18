package me.iamcrk.hrms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Who will receive the notification
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private String type;   // e.g., SALARY, LEAVE, ADVANCE_REQUEST
    private String message;
    private boolean isRead = false;  // to track if employee saw it
    private LocalDateTime createdAt = LocalDateTime.now();
}
