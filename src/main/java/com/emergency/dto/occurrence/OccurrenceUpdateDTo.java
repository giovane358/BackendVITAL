package com.emergency.dto.occurrence;

import java.math.BigDecimal;

public record OccurrenceUpdateDTo(

        Integer id,

        String type,
        String description,
        BigDecimal latitude,
        BigDecimal longitude,
        String priority,

        Integer statusId,
        Integer citizenId,
        Integer teamId

) {
}