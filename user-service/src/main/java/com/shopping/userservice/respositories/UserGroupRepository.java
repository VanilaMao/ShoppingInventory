package com.shopping.userservice.respositories;

import com.shopping.userservice.entities.UserGroup;
import com.shopping.userservice.entities.compositeKeys.UserGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {
}
