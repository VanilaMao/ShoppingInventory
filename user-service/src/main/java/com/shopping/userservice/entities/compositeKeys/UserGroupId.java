package com.shopping.userservice.entities.compositeKeys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class UserGroupId implements Serializable {

    @Column(name="USER_ID")
    private UUID userId;

    @Column(name="GROUP_ID")
    private UUID groupId;

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof UserGroupId))
            return false;
        UserGroupId castOther = (UserGroupId) other;
        return userId.equals(castOther.userId) && groupId.equals(castOther.groupId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.userId.hashCode();
        hash = hash * prime + this.groupId.hashCode();
        return hash;
    }
}
