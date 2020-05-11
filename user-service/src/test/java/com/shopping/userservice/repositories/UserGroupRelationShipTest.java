package com.shopping.userservice.repositories;

import com.shopping.userservice.entities.Group;
import com.shopping.userservice.entities.User;
import com.shopping.userservice.entities.UserGroup;
import com.shopping.userservice.enums.ActiveStatus;
import com.shopping.userservice.enums.GroupUserStatus;
import com.shopping.userservice.enums.Role;
import com.shopping.userservice.respositories.GroupRepository;
import com.shopping.userservice.respositories.UserGroupRepository;
import com.shopping.userservice.respositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserGroupRelationShipTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Test
    void canSaveAndReadUserGroups(){
        User owner = User.builder()
                .name("owner")
                .zipcode("48197")
                .status(ActiveStatus.Active)
                .roles(new HashSet<Role>(Arrays.asList(Role.Doctor,Role.Admin)))
                .build();

        User user1 = User.builder()
                .name("user1")
                .zipcode("48197")
                .status(ActiveStatus.Active)
                .roles(new HashSet(Arrays.asList(Role.Doctor)))
                .build();

        User user2 = User.builder()
                .name("user2")
                .zipcode("48197")
                .status(ActiveStatus.Pending)
                .roles(new HashSet(Arrays.asList(Role.Nurse)))
                .build();

        userRepository.save(owner);
        userRepository.save(user1);
        userRepository.save(user2);

        User savedOwner = userRepository.findById(owner.getId()).get();
        assertThat(savedOwner.getRoles().size()).isEqualTo(2);
        assertThat(savedOwner.getStatus()).isEqualTo(ActiveStatus.Active);
        assertThat(savedOwner.getRoles().contains(Role.Doctor)).isTrue();
        assertThat(savedOwner.getRoles().contains(Role.Cashier)).isFalse();


        // group
        Group group = Group.builder()
                .description("great group")
                .limit(400L)
                .owner(owner)
                .name("Group")
                .status(ActiveStatus.Active)
                .build();

        Group group1 = Group.builder()
                .description("silly group")
                .limit(500L)
                .owner(user1)
                .name("Group1")
                .status(ActiveStatus.Active)
                .build();


        UserGroup userGroup1 = UserGroup.builder()
                //.id(UserGroupId.builder().groupId(group.getGroupId()).userId(user1.getId()).build())
                .group(group)
                .user(user1)
                .status(GroupUserStatus.Approved).build();

        UserGroup userGroup2 = UserGroup.builder()
                //.id(UserGroupId.builder().groupId(group.getGroupId()).userId(user2.getId()).build())
                .group(group)
                .user(user2)
                .status(GroupUserStatus.Declined).build();

        UserGroup userGroup3 = UserGroup.builder()
                .group(group)
                .user(user2)
                .status(GroupUserStatus.Declined).build();

        group.setUserGroups(Arrays.asList(userGroup1,userGroup2));

        group1.setUserGroups(Arrays.asList(userGroup3));
        groupRepository.save(group);
        groupRepository.save(group1);


        Group savedGroup = groupRepository.findById(group.getGroupId()).get();
        Group savedGroup1 = groupRepository.findById(group1.getGroupId()).get();

        assertThat(savedGroup1.getUserGroups().size()).isEqualTo(1);

        assertThat(savedGroup.getUserGroups().get(0).getStatus()).isEqualTo(GroupUserStatus.Approved);
        assertThat(savedGroup.getUserGroups().get(1).getStatus()).isEqualTo(GroupUserStatus.Declined);
    }
}
