package com.pawer.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Resim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    byte[] resim;
    String resimname;
}
