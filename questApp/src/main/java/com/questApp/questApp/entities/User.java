package com.questApp.questApp.entities;


import lombok.Data;

import javax.persistence.*;

// TODO: 1.10.2022 Add those 2 lines below later for id to be uniquely generated and incremented 
/* @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)*/

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    Long id;

    String userName;
    String password;

}
