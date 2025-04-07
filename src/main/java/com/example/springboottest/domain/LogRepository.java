package com.example.springboottest.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntry, Long> {

    boolean existsByMessage(String message);
}
