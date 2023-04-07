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
@Table(name = "tbl_follow")
//benim takip ettiklerim
public class Follow extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId; // benim ıd'm
    private Long followId; //benim takip ettiğim insanların idsi
    private int followRequest ; // 0 takipleşmiyor 1_ istek attı bekliyor 2_ takip ediyor.



}
