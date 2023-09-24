package com.lizardostrich.quoteandpolicymanagement.model;

import jakarta.persistence.*;
import lombok.Data;

enum Level{
    STARTER,
    ESSENTIAL,
    ADVANCED
}

@Data
@Entity
@Table(name = "policy")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "level", columnDefinition = "STARTER")
    private Level level;
    @Column(name = "description")
    private String description;
    @Column(name = "coverage")
    private double coverage;
    @Column(name = "premium")
    private double premium;
}
