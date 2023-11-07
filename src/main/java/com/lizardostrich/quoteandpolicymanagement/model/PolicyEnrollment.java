package com.lizardostrich.quoteandpolicymanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name = "policy_enrollment")
public class PolicyEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_name")
    private String fullName;
//    @Column(name = "spouse_policies")
//    private List<Long> spousePolicies;
//    @Column(name = "dependent_policies")
//    private List<Long> dependentPolicies;
    @Column(name = "premium")
    private double premium;
    @Column(name = "payment_status")
    private Payment paymentStatus;
    @Column(name = "coverage_start_date")
    private Date startDate;
    @Column(name = "coverage_end_date")
    private Date endDate;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "primary_user_policies",
            joinColumns = @JoinColumn(name = "enrollment_id"),
            inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    private Set<Policy> primaryUserPolicies;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "spouse_policies",
            joinColumns = @JoinColumn(name = "enrollment_id"),
            inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    private Set<Policy> spousePolicies;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "dependent_policies",
            joinColumns = @JoinColumn(name = "enrollment_id"),
            inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    private Set<Policy> dependentPolicies;

    public PolicyEnrollment() {

    }
}
