package com.shopping.userservice.respositories;

import com.shopping.userservice.entities.Group;
import com.shopping.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    Optional<Group> findByName(String name);
    List<Group> findByOwner(User owner);
}
