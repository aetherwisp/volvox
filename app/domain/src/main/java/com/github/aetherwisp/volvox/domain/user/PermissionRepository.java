package com.github.aetherwisp.volvox.domain.user;

import org.springframework.lang.NonNull;
import com.github.aetherwisp.volvox.domain.Repository;
import com.github.aetherwisp.volvox.domain.user.PermissionRepository.PermissionFinder;

@Deprecated
public interface PermissionRepository extends Repository<Permission, Integer, PermissionFinder> {

    public static interface PermissionFinder extends Repository.Finder<Permission, Integer> {

        PermissionFinder filterByUserId(@NonNull Integer userId);

        PermissionFinder filterByRoleId(@NonNull Integer roleId);
    }
}
