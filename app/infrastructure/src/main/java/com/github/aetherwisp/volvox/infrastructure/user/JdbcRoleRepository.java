package com.github.aetherwisp.volvox.infrastructure.user;

import java.sql.Types;
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
import com.github.aetherwisp.volvox.domain.user.PermissionRepository.PermissionFinder;
import com.github.aetherwisp.volvox.domain.user.Role;
import com.github.aetherwisp.volvox.domain.user.RoleRepository;
import com.github.aetherwisp.volvox.infrastructure.AbstractJdbcFinder;
import com.github.aetherwisp.volvox.infrastructure.Queries;

@Component
public class JdbcRoleRepository extends NamedParameterJdbcDaoSupport implements RoleRepository {

    //======================================================================
    // Fields
    private final ConversionService conversionService;
    private final PermissionRepository permissionRepository;

    //======================================================================
    // Constructors
    @Autowired
    public JdbcRoleRepository(final DataSource _dataSource, final ConversionService _conversionService, final PermissionRepository _permissionRepository) {
        this.setDataSource(Objects.requireNonNull(_dataSource));
        this.conversionService = Objects.requireNonNull(_conversionService);
        this.permissionRepository = Objects.requireNonNull(_permissionRepository);
    }

    //======================================================================
    // Methods
    @Override
    public RoleFinder finder() {
        return new JdbcRoleFinder(this.getNamedParameterJdbcTemplate(), this.conversionService, this.permissionRepository.finder());
    }

    //======================================================================
    // Classes
    private static class JdbcRoleFinder extends AbstractJdbcFinder<Role, Integer> implements RoleFinder {
        //======================================================================
        // Fields
        private final PermissionFinder permissionFinder;
        private Integer userId;
        private Integer roleId;

        //======================================================================
        // Constructors
        protected JdbcRoleFinder(NamedParameterJdbcTemplate _jdbcTemplate, ConversionService _conversionService, PermissionFinder _permissionFinder) {
            super(_jdbcTemplate, _conversionService);
            this.permissionFinder = Objects.requireNonNull(_permissionFinder);
        }

        //======================================================================
        // Methods
        @Override
        public Role get(Integer _firstId, Integer... _remainingIds) {
            final List<Permission> permissions = this.permissionFinder.filterByRoleId(_firstId)
                    .find();

            return this.getJdbcTemplate()
                    .query(Queries.query()
                            .append("SELECT id,")
                            .append("       name")
                            .append("  FROM role")
                            .append(" WHERE id = :roleId")
                            .toString(),
                            Queries.parameters()
                                    .addValue("roleId", _firstId, Types.INTEGER),
                            this.getRowMapper(Role.RoleBuilder.class))
                    .stream()
                    .peek(builder -> builder.setPermissions(permissions))
                    .map(builder -> builder.build())
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public List<Role> find() {
            final boolean byUserId = (null != this.userId);
            final boolean byRoleId = (null != this.roleId);

            final List<Permission> permissions = this.permissionFinder.filterByUserId(this.userId)
                    .filterByRoleId(this.roleId)
                    .find();

            return this.getJdbcTemplate()
                    .query(Queries.query()
                            .append("SELECT role.id,")
                            .append("       role.name")
                            .append("  FROM role")
                            .append(" INNER JOIN user_roles")
                            .append("    ON role.id = user_roles.role_id")
                            .append(" WHERE TRUE")
                            .append("   AND user_roles.user_id = :userId", byUserId)
                            .append("   AND user_roles.role_id = :roleId", byRoleId)
                            .append(" ORDER BY role.id DESC")
                            .toString(),
                            Queries.parameters()
                                    .addValue("userId", this.userId, Types.INTEGER, byUserId)
                                    .addValue("roleId", this.roleId, Types.INTEGER, byRoleId),
                            this.getRowMapper(Role.RoleBuilder.class))
                    .stream()
                    .peek(builder -> builder.setPermissions(permissions))
                    .map(builder -> builder.build())
                    .collect(Collectors.toList());
        }

        @Override
        public RoleFinder filterByUserId(@NonNull Integer _userId) {
            this.userId = Objects.requireNonNull(_userId);
            return this;
        }

        @Override
        public RoleFinder filterByRoleId(Integer _roleId) {
            this.roleId = Objects.requireNonNull(_roleId);
            return this;
        }
    }
}
