package com.emergency.dto.occurrence;

import java.math.BigDecimal;

public record OccurrenceDTo(

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