package com.shopping.userservice.entities;

import com.shopping.userservice.domains.BaseUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name="User")
public class User extends BaseUser {

    @Column(name="ZIP_CODE")
    private  String zipcode;

//    @Column(name="USER_NAME")
//    private String userName;
//
//    @Column(name="EMAIL")
//    private String email;
//
//    private String street;
//
//    private String state;
//
//    private String phone;
//
//    // leaving, vocation, sick
//    private String status;
//
//    private String mitbbsId;
//
//    //readonly
//    private String role;  //admin, doctor, nurse, carrier, accounting







}
