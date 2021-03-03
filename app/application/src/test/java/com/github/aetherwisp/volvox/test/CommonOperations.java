package com.github.aetherwisp.volvox.test;

import static com.ninja_squad.dbsetup.Operations.*;
import com.ninja_squad.dbsetup.operation.Operation;

public class CommonOperations {

    /** 全ての管理テーブルのデータを削除 */
    public static final Operation DELETE_ALL_TABLES =
            deleteAllFrom("role_permissions", "user_roles", "user_permissions", "role", "permission", "user_password", "user");

}
