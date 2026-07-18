package com.nestify.pg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room number is required")
    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;

    @NotBlank(message = "Room type is required")
    @Column(name = "room_type")
    private String roomType;

    @Positive(message = "Rent must be greater than 0")
    private double rent;

    private boolean available;
}