package com.github.aetherwisp.volvox.domain.user;

import org.springframework.lang.NonNull;
import com.github.aetherwisp.volvox.domain.Repository;
import com.github.aetherwisp.volvox.domain.user.PasswordRepository.PasswordFinder;

public interface PasswordRepository extends Repository<Password, Integer, PasswordFinder> {

    public static interface PasswordFinder extends Repository.Finder<Password, Integer> {

        PasswordFinder filterById(@NonNull Integer id);

        PasswordFinder filterByUserId(@NonNull Integer userId);
    }
}
