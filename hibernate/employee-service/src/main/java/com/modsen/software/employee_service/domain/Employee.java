package com.modsen.software.employee_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;

@Entity
//@OptimisticLocking(type = OptimisticLockType.DIRTY)
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    //    @OptimisticLock(excluded = true)
    private String name;

    @Column(name = "salary")
    private BigDecimal salary;

    // TODO: NOTE
    // The important part. Add this attribute to DB and locally.
    @Version
    private Integer version;
}
