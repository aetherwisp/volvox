package com.github.aetherwisp.volvox.domain.user;

import org.springframework.lang.NonNull;
import com.github.aetherwisp.volvox.domain.Repository;
import com.github.aetherwisp.volvox.domain.user.UserRepository.UserFinder;

public interface UserRepository extends Repository<User, Integer, UserFinder> {

    public static interface UserFinder extends Repository.Finder<User, Integer> {

        UserFinder filterById(@NonNull Integer id);

        UserFinder filterByName(@NonNull String name);
    }
}
