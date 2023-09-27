package com.lizardostrich.quoteandpolicymanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_policy")
public class UserPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "policy_id")
    private int policyId;
    @Column(name = "coverage")
    private String coverage;

    @Column(name = "premium")
    private double premium;
}
