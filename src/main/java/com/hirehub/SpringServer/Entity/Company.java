package com.hirehub.SpringServer.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String address;
    private String industry;
    private String companySize;

    private Boolean isBlocked;
    private String website;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Timestamp createdAt;
}
