
package com.shopping.userservice.entities;

import com.shopping.userservice.enums.ActiveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="Group")
public class Group implements Serializable {
    @Id
    @Column(name="GROUP_ID")
    private UUID groupId;

    @Column(name="NAME")
    private String name;

    @Column(name="DSC")
    private String description;

    @Column(name="LIMIT")
    @Builder.Default
    private Long limit = 500L;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    // unidirection
    @ManyToOne(optional = false)
    @JoinColumn(name="USER_ID",referencedColumnName = "USER_ID",nullable = false)
    private User owner;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    private List<UserGroup> userGroups;
}
