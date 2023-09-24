package com.lizardostrich.quoteandpolicymanagement.repository;

import com.lizardostrich.quoteandpolicymanagement.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
