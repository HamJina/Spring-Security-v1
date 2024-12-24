package com.example.security_1.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String role; //ROLE_USER, ROLE_ADMIN

    private LocalDateTime createDate;

    @PrePersist
    public void onCreate() {
        this.createDate = LocalDateTime.now(); // 자동으로 현재 시간 설정
    }

}
