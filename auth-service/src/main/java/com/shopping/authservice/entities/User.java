package com.shopping.authservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="User")
public class User {
    @Column(name="USER_ID")
    @Id
    private UUID id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name="PASSWORD")
    private String password;
}
