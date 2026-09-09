package com.anapaula.breathebackend.repository;

import com.anapaula.breathebackend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Message findRandomMessage();

}