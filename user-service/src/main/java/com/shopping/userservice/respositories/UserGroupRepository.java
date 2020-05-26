package com.shopping.userservice.respositories;

import com.shopping.userservice.entities.User;
import com.shopping.userservice.entities.UserGroup;
import com.shopping.userservice.entities.compositeKeys.UserGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {
    List<UserGroup> findByUser(User user);
}
