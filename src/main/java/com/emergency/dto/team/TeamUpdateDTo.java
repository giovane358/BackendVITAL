package com.emergency.dto.team;

public record TeamUpdateDTo(
        Integer id,
        String name,
        String vehicle,
        Double available,
        Double currentLat,
        Double currentLng
) {}