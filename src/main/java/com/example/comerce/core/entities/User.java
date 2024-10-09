package com.example.comerce.core.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity

@Getter
@Setter
public class User {

    @Id
    private UUID id;
}
