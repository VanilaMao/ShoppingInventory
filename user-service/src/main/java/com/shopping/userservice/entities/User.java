package com.shopping.userservice.entities;

import com.shopping.userservice.domains.BaseUser;
import com.shopping.userservice.enums.ActiveStatus;
import com.shopping.userservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name="USER_TABLE")
public class User extends BaseUser implements Serializable {

    @Column(name="ZIP_CODE")
    private  String zipcode;

    @Column(name="USER_NAME")
    private String name;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    // ElementCollections are always cascaded.
    @ElementCollection
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name="ROLE")

    private Set<Role> roles;
}
