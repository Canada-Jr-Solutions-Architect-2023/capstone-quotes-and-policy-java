package com.lizardostrich.quoteandpolicymanagement.repository;

import com.lizardostrich.quoteandpolicymanagement.model.PolicyEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<PolicyEnrollment,Long> {
    List<PolicyEnrollment> findByCustomerEmail(String s);
}
