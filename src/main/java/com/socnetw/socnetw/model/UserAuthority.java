package com.socnetw.socnetw.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "authority")
public class UserAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private UserAuthorityType name;
}
