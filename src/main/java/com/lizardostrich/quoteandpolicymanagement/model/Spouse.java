package com.lizardostrich.quoteandpolicymanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

@Data
@Entity
@Table(name = "spouse")
public class Spouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spouseId;

    @OneToOne
    @JoinColumn(name = "enrollment_id")
    private PolicyEnrollment policyEnrollment;
    @Column(name = "fullname")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "has_provincial_coverage")
    private Boolean hasProvincialCoverage;


}
