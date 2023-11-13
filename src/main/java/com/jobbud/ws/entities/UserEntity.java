package com.jobbud.ws.entities;

import com.jobbud.ws.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
/*
@Table annotation specifies the table name on database
 */
@Data
/*
 * @Data is a annotation of lombok dependency that creates getter and setter methods for us
 */
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //auto is for auto increment, we could be had been using different something UUID(universal unique id) or else.
    private long id;

    @Column(nullable = false, length = 50)
    //column annotation manages the column properties in table
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;



}
