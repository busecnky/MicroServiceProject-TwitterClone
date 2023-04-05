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
//benim takip ettiklerim
public class Follow extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId; // benim ıd'm
    private Long followerId; // beni takip edenlerin id'si
    //private int state; // 0 eklidegil 1_ istek attı 2_ takip ediyor.
}
