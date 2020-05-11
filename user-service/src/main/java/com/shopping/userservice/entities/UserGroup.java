
// https://www.codejava.net/frameworks/hibernate/hibernate-many-to-many-association-with-extra-columns-in-join-table-example
// https://thoughts-on-java.org/hibernate-tip-many-to-many-association-with-additional-attributes/
package com.shopping.userservice.entities;

import com.shopping.userservice.entities.compositeKeys.UserGroupId;
import com.shopping.userservice.enums.GroupUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="USER_GROUP")
public class UserGroup implements Serializable {

    @EmbeddedId
    private UserGroupId id;

    @ManyToOne(fetch =FetchType.LAZY)
    @MapsId("USER_ID")
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne(fetch =FetchType.LAZY)
    @MapsId("GROUP_ID")
    @JoinColumn(name="GROUP_ID")
    private Group group;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private GroupUserStatus status;

    //expire time  XXXX
}
