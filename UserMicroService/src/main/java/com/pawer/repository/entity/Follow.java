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
// kullanıcının gönderdiği takip isteği
public class Follow extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId; // benim ıd'm
    private Long followId; //takip isteği attığım insanların ıd'si
    private int followRequest ; // 0_istek atmamışım 1_ istek attım bekliyor 2_ takip ediyorum.



}
