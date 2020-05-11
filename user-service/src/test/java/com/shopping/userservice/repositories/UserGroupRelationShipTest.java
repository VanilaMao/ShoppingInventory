// https://stackoverflow.com/questions/7428089/unsupportedoperationexception-merge-saving-many-to-many-relation-with-hibernate

package com.shopping.userservice.repositories;

import com.shopping.userservice.entities.Group;
import com.shopping.userservice.entities.User;
import com.shopping.userservice.entities.UserGroup;
import com.shopping.userservice.entities.compositeKeys.UserGroupId;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

        userRepository.saveAndFlush(owner);
        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);

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
        Group savedGroup = groupRepository.saveAndFlush(group);



        UserGroup userGroup1 = UserGroup.builder()
                .id(UserGroupId.builder().groupId(savedGroup.getGroupId()).userId(user1.getId()).build())
                .group(savedGroup)
                .user(user1)
                .status(GroupUserStatus.Approved).build();

        UserGroup userGroup2 = UserGroup.builder()
                .id(UserGroupId.builder().groupId(savedGroup.getGroupId()).userId(user2.getId()).build())
                .group(savedGroup)
                .user(user2)
                .status(GroupUserStatus.Declined).build();
        List<UserGroup> group1UserGroupList = new ArrayList<>();
        group1UserGroupList.add(userGroup1);
        group1UserGroupList.add(userGroup2);
        savedGroup.setUserGroups(group1UserGroupList);
        userGroupRepository.saveAndFlush(userGroup1);
        userGroupRepository.saveAndFlush(userGroup2);


        groupRepository.saveAndFlush(savedGroup);
        Group verifiedGroup = groupRepository.findById(savedGroup.getGroupId()).get();
        assertThat(verifiedGroup.getUserGroups().get(0).getStatus()).isEqualTo(GroupUserStatus.Approved);
        assertThat(verifiedGroup.getUserGroups().get(1).getStatus()).isEqualTo(GroupUserStatus.Declined);


        Group group1 = Group.builder()
                .description("silly group")
                .limit(500L)
                .owner(user1)
                .name("Group1")
                .status(ActiveStatus.Active)
                .build();
        Group savedGroup1 = groupRepository.saveAndFlush(group1);

        UserGroup userGroup3 = UserGroup.builder()
                .id(UserGroupId.builder().groupId(savedGroup1.getGroupId()).userId(user2.getId()).build())
                .group(savedGroup1)
                .user(user2)
                .status(GroupUserStatus.Declined).build();
        List<UserGroup> group1UserGroupList1 = new ArrayList<>();
        group1UserGroupList1.add(userGroup3);
        savedGroup1.setUserGroups(group1UserGroupList1);


        userGroupRepository.saveAndFlush(userGroup3);
        groupRepository.saveAndFlush(savedGroup1);
        Group updatedGroup1 = groupRepository.findById(savedGroup1.getGroupId()).get();

        // update
        UserGroup repoUserGroup = updatedGroup1.getUserGroups().get(0);
        repoUserGroup.setStatus(GroupUserStatus.Approved);
        userGroupRepository.saveAndFlush(repoUserGroup);

        Group verifiedGroup1 = groupRepository.findById(savedGroup1.getGroupId()).get();
        assertThat(verifiedGroup1.getUserGroups().get(0).getStatus()).isEqualTo(GroupUserStatus.Approved);

    }
}
