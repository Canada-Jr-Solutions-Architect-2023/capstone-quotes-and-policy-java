package com.lizardostrich.quoteandpolicymanagement.repository;

import com.lizardostrich.quoteandpolicymanagement.model.PolicyEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<PolicyEnrollment,Long> {
}
