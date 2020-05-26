package com.shopping.userservice.responses;

import com.shopping.userservice.enums.ActiveStatus;
import com.shopping.userservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    private String name;
    private ActiveStatus status;
    private List<Role> roles;
    private List<GroupResponse> doctorGroups;
    private List<GroupResponse> userGroups;
}
