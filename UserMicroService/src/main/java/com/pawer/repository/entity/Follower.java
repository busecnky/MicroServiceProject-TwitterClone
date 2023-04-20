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
@Table(name = "tbl_follower")

// kullanıcıya gelen takip istekleri
public class Follower extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId; // benim ıd'm
    private Long followerId; //bana takip isteği atan insanlar
    private int statee; // 0_ bana istek atmamış 1_ bana istek atmış 2_ bana attığı isteği onaylamışım ->




}
