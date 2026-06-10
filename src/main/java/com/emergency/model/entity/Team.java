package com.emergency.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false, length = 7)
    private String vehicle;

    @Column(nullable = false)
    private Boolean available = true;

    @Column(name = "current_lat", nullable = false)
    private Double currentLat;

    @Column(name = "current_lng", nullable = false)
    private Double currentLng;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Usuario createdBy;
}