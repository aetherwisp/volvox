package com.github.aetherwisp.volvox.domain.user;

import org.springframework.lang.NonNull;
import com.github.aetherwisp.volvox.domain.Repository;
import com.github.aetherwisp.volvox.domain.user.RoleRepository.RoleFinder;

@Deprecated
public interface RoleRepository extends Repository<Role, Integer, RoleFinder> {

    public static interface RoleFinder extends Repository.Finder<Role, Integer> {

        RoleFinder filterByUserId(@NonNull Integer userId);

        RoleFinder filterByRoleId(@NonNull Integer roleId);
    }
}
