package com.lizardostrich.quoteandpolicymanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "policy")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "level")
    @Convert(converter = LevelConverter.class)
    @Enumerated(EnumType.STRING)
    private Level level;
    @Column(name = "description")
    private String description;
    @Column(name = "coverage")
    private double coverage;
    @Column(name = "premium")
    private double premium;

    public Policy(){}
    public Policy(String title, Level level, String description, double coverage, double premium) {
        this.title = title;
        this.level = level;
        this.description = description;
        this.coverage = coverage;
        this.premium = premium;
    }
}
