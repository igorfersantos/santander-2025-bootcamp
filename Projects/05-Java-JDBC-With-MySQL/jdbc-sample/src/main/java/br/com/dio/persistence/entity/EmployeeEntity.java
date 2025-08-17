package br.com.dio.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Class that represents an Employee Entity.
 */
@Data
public class EmployeeEntity {
    private long id;

    private String name;

    private OffsetDateTime birthday;

    private BigDecimal salary;
}
