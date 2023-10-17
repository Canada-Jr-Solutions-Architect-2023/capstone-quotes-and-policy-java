package com.lizardostrich.quoteandpolicymanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id"/*, nullable = true, columnDefinition="tinyint(1) default 1"*/)
    private Long customerId;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column(name = "gender")
    private String gender;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    /*@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
            @JoinTable(name = "user_policy_info",
            joinColumns = @JoinColumn(name = "customer_id"*//*, nullable = true, columnDefinition="tinyint(1) default 1"*//*),
            inverseJoinColumns = @JoinColumn(name = "policy_id"))
    private List<Policy> policies;*/
    /*@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "user_policy",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id") )
    private List<UserPolicy> user_policies;*/
    /*@OneToOne(fetch=FetchType.EAGER, optional=true, cascade=CascadeType.ALL)
    private UserPolicy user_policy;*/
    public Customer() {

    }

    public Customer(String firstName, String lastName, String dateOfBirth, String gender, String phoneNumber, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}