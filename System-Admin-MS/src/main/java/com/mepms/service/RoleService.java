package com.mepms.service;

import com.mepms.entity.RoleEO;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    RoleEO createRole(RoleEO role);

    List<RoleEO> getAllRoles();

    Optional<RoleEO> getRoleById(String id);

    RoleEO updateRole(String id, RoleEO role);
    
    void deleteRoleId(String id);
}
