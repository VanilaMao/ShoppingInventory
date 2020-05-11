// https://stackoverflow.com/questions/45635827/how-do-i-stop-spring-data-jpa-from-doing-a-select-before-a-save
package com.shopping.userservice.entities;

import com.shopping.userservice.enums.ActiveStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="GROUP_TABLE")
public class Group implements Serializable {
    @Id
    @Column(name="GROUP_ID")
    private UUID groupId;

    @Column(name="NAME")
    private String name;

    @Column(name="DSC")
    private String description;

    @Column(name="CAPACITY")
    @Builder.Default
    private Long limit = 500L;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    // unidirection
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="OWNER_ID",referencedColumnName = "USER_ID",nullable = false)
    private User owner;


    //CascadeType.ALL does not work on unit test because the in memory database share the memory
    // between model and repository record, when try to return a record, it just returns the saved model directly
    @ToString.Exclude
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private List<UserGroup> userGroups;
}
