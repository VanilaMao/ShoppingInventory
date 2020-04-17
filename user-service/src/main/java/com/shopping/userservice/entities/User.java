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

    @Column(name="ZipCode")
    private  String zipcode;
}
