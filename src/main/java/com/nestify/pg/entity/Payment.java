package com.nestify.pg.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_name")
    private String tenantName;

    @Column(name = "room_number")
    private String roomNumber;

    private double amount;

    @Column(name = "payment_date")
    private String paymentDate;

    @Column(name = "due_date")
    private String dueDate;

    private String status;
}