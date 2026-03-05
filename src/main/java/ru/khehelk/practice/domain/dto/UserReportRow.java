package ru.khehelk.practice.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserReportRow(
    Long id,
    String email,
    BigDecimal balance,
    LocalDateTime createdAt
) {}