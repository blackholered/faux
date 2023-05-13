package com.it355.backend.repository;

import com.it355.backend.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogRepository extends JpaRepository<Log, Integer>, JpaSpecificationExecutor<Log> {
}
