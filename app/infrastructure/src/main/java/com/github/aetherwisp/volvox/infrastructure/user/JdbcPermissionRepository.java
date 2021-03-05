package com.github.aetherwisp.volvox.infrastructure.user;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import com.github.aetherwisp.volvox.domain.user.Permission;
import com.github.aetherwisp.volvox.domain.user.PermissionRepository;
import com.github.aetherwisp.volvox.infrastructure.AbstractJdbcFinder;
import com.github.aetherwisp.volvox.infrastructure.Queries;

@Component
public class JdbcPermissionRepository extends NamedParameterJdbcDaoSupport implements PermissionRepository {

    //======================================================================
    // Fields
    private final ConversionService conversionService;

    //======================================================================
    // Constructors
    @Autowired
    public JdbcPermissionRepository(final DataSource _dataSource, final ConversionService _conversionService) {
        this.setDataSource(Objects.requireNonNull(_dataSource));
        this.conversionService = Objects.requireNonNull(_conversionService);
    }

    //======================================================================
    // Methods
    @Override
    public PermissionFinder finder() {
        return new JdbcPermissionFinder(this.getNamedParameterJdbcTemplate(), this.conversionService);
    }

    //======================================================================
    // Classes
    private static class JdbcPermissionFinder extends AbstractJdbcFinder<Permission, Integer> implements PermissionFinder {
        //======================================================================
        // Fields
        private Integer userId;
        private Integer roleId;

        //======================================================================
        // Constructors
        protected JdbcPermissionFinder(NamedParameterJdbcTemplate _jdbcTemplate, ConversionService _conversionService) {
            super(_jdbcTemplate, _conversionService);
        }

        //======================================================================
        // Methods
        @Override
        public Permission get(Integer _firstId, Integer... _remainingIds) {
            return this.getJdbcTemplate()
                    .query(Queries.query()
                            .append("SELECT id,")
                            .append("       name")
                            .append("  FROM permission")
                            .append(" WHERE id = :id")
                            .toString(),
                            Queries.parameters()
                                    .addValue("id", _firstId, Types.INTEGER),
                            this.getRowMapper(Permission.PermissionBuilder.class))
                    .stream()
                    .map(builder -> builder.build())
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public List<Permission> find() {
            final boolean byUserId = (null != this.userId);
            final boolean byRoleId = (null != this.roleId);

            final List<Permission> permissions = new ArrayList<>();
            if (byUserId || byRoleId) {
                if (byUserId) {
                    permissions.addAll(this.getJdbcTemplate()
                            .query(Queries.query()
                                    .append("SELECT permission.id,")
                                    .append("       permission.name")
                                    .append("  FROM user_permissions")
                                    .append(" INNER JOIN permission")
                                    .append("    ON user_permissions.permission_id = permission.id")
                                    .append(" WHERE user_permissions.user_id = :userId")
                                    .toString(),
                                    Queries.parameters()
                                            .addValue("userId", this.userId, Types.INTEGER),
                                    this.getRowMapper(Permission.PermissionBuilder.class))
                            .stream()
                            .map(builder -> builder.build())
                            .collect(Collectors.toList()));
                }

                if (byUserId || byRoleId) {
                    permissions.addAll(this.getJdbcTemplate()
                            .query(Queries.query()
                                    .append("SELECT permission.id,")
                                    .append("       permission.name")
                                    .append("  FROM user_roles")
                                    .append(" INNER JOIN role")
                                    .append("    ON user_roles.role_id = role.id")
                                    .append(" INNER JOIN role_permissions")
                                    .append("    ON role.id = role_permissions.role_id")
                                    .append(" INNER JOIN permission")
                                    .append("    ON role_permissions.permission_id = permission.id")
                                    .append(" WHERE TRUE")
                                    .append("   AND user_roles.user_id = :userId", byUserId)
                                    .append("   AND user_roles.role_id = :roleId", byRoleId)
                                    .append("   AND role_permissions.role_id = :roleId", byRoleId)
                                    .toString(),
                                    Queries.parameters()
                                            .addValue("userId", this.userId, Types.INTEGER, byUserId)
                                            .addValue("roleId", this.roleId, Types.INTEGER, byRoleId),
                                    this.getRowMapper(Permission.PermissionBuilder.class))
                            .stream()
                            .map(builder -> builder.build())
                            .collect(Collectors.toList()));
                }

                return permissions.stream()
                        .distinct()
                        .sorted(Comparator.comparing(Permission::getId, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
            } else {
                return this.getJdbcTemplate()
                        .query(Queries.query()
                                .append("SELECT permission.id,")
                                .append("       permission.name")
                                .append("  FROM permissions")
                                .append(" ORDER BY permission.id DESC")
                                .toString(), this.getRowMapper(Permission.PermissionBuilder.class))
                        .stream()
                        .map(builder -> builder.build())
                        .collect(Collectors.toList());
            }
        }

        @Override
        public PermissionFinder filterByUserId(@NonNull Integer _userId) {
            this.userId = _userId;
            return this;
        }

        @Override
        public PermissionFinder filterByRoleId(Integer _roleId) {
            this.roleId = _roleId;
            return this;
        }
    }
}
