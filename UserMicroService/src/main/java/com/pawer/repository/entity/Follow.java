package com.pawer.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
//benim takip ettiklerim
public class Follow extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId; // benim ıd'm
    private Long followId; //benim takip ettiğim insanların idsi
    @Builder.Default
    private int followRequest = 0; // 0 takipleşmiyor 1_ istek attı bekliyor 2_ takip ediyor.



}
