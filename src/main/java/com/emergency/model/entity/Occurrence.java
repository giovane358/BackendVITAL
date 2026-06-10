package com.emergency.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "occurrence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Occurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;

    private String description;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String priority;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Usuario citizen;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Usuario createdBy;
}