package com.emergency.dto.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeamDTo(
        String name,
        String vehicle,
        Double currentLat,
        Double currentLng

) {}