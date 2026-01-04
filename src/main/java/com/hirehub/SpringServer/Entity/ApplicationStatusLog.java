package com.hirehub.SpringServer.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "applicationStatusLogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "applicationId")
    private Application application;

    private String fromStatus;
    private String toStatus;

    private Timestamp updatedAt;
}
