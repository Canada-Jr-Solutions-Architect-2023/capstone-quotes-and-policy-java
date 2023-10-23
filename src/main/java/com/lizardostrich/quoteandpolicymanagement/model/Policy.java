package com.lizardostrich.quoteandpolicymanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

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

// NOTE: Use this when required to figure out how many primary enrollments use the current policy
//    @ManyToMany(mappedBy = "primaryUserPolicies")
//    private Set<PolicyEnrollment> primaryUserPolicyEnrollments;


    public Policy(){}
    public Policy(String title, Level level, String description, double coverage, double premium) {
        this.title = title;
        this.level = level;
        this.description = description;
        this.coverage = coverage;
        this.premium = premium;
    }

    public Policy(Long id, String title, Level level, String description, double coverage, double premium) {
        this.id = id;
        this.title = title;
        this.level = level;
        this.description = description;
        this.coverage = coverage;
        this.premium = premium;
    }
}
