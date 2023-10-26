package com.lizardostrich.quoteandpolicymanagement.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "dependent")
public class Dependent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dependentId;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private PolicyEnrollment policyEnrollment;
    @Column(name = "fullname")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "has_provincial_coverage")
    private Boolean hasProvincialCoverage;


}
