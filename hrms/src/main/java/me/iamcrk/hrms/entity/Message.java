package me.iamcrk.hrms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Employee sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Employee receiver;

    @Column(nullable = false, length = 1000)
    private String content;

    private LocalDateTime sentAt;

    private LocalDateTime deleteAt; // auto after 10 days
}
