package com.github.aetherwisp.volvox.infrastructure.user;

import java.sql.Types;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.github.aetherwisp.volvox.domain.user.GeneralUser;
import com.github.aetherwisp.volvox.domain.user.Password;
import com.github.aetherwisp.volvox.domain.user.PasswordRepository;
import com.github.aetherwisp.volvox.domain.user.PasswordRepository.PasswordFinder;
import com.github.aetherwisp.volvox.domain.user.Permission;
import com.github.aetherwisp.volvox.domain.user.PermissionRepository;
import com.github.aetherwisp.volvox.domain.user.PermissionRepository.PermissionFinder;
import com.github.aetherwisp.volvox.domain.user.RoleRepository;
import com.github.aetherwisp.volvox.domain.user.RoleRepository.RoleFinder;
import com.github.aetherwisp.volvox.domain.user.User;
import com.github.aetherwisp.volvox.domain.user.UserRepository;
import com.github.aetherwisp.volvox.infrastructure.AbstractJdbcFinder;
import com.github.aetherwisp.volvox.infrastructure.Queries;

@Component
public class JdbcUserRepository extends NamedParameterJdbcDaoSupport implements UserRepository {

    //======================================================================
    // Fields
    private final ConversionService conversionService;

    private final PasswordRepository passwordRepository;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    //======================================================================
    // Constructors
    @Autowired
    public JdbcUserRepository(final DataSource _dataSource, final ConversionService _conversionService,
            final PasswordRepository _passwordRepository, final RoleRepository _roleRepository,
            final PermissionRepository _permissionRepository) {
        this.setDataSource(Objects.requireNonNull(_dataSource));
        this.conversionService = Objects.requireNonNull(_conversionService);
        this.passwordRepository = Objects.requireNonNull(_passwordRepository);
        this.roleRepository = Objects.requireNonNull(_roleRepository);
        this.permissionRepository = Objects.requireNonNull(_permissionRepository);
    }

    //======================================================================
    // Methods
    @Override
    public UserFinder finder() {
        return new JdbcUserFinder(this.getNamedParameterJdbcTemplate(), this.conversionService, this.passwordRepository.finder(),
                this.roleRepository.finder(), this.permissionRepository.finder());
    }

    //======================================================================
    // Classes
    private static class JdbcUserFinder extends AbstractJdbcFinder<User, Integer> implements UserFinder {
        //======================================================================
        // Fields
        private final PasswordFinder passwordFinder;
        private final RoleFinder roleFinder;
        private final PermissionFinder permissionFinder;
        private Integer id;
        private String name;

        //======================================================================
        // Constructors
        private JdbcUserFinder(NamedParameterJdbcTemplate _jdbcTemplate, ConversionService _conversionService,
                PasswordFinder _passwordFinder, RoleFinder _roleFinder, PermissionFinder _permissionFinder) {
            super(_jdbcTemplate, _conversionService);
            this.passwordFinder = Objects.requireNonNull(_passwordFinder);
            this.roleFinder = Objects.requireNonNull(_roleFinder);
            this.permissionFinder = Objects.requireNonNull(_permissionFinder);
        }

        //======================================================================
        // Methods
        @Override
        public UserFinder filterById(Integer _id) {
            this.id = Objects.requireNonNull(_id);
            return this;
        }

        @Override
        public UserFinder filterByName(String _name) {
            this.name = Objects.requireNonNull(_name);
            return this;
        }

        @Override
        public User get(Integer _firstId, Integer... _remainingIds) {
            final Integer userId = Objects.requireNonNull(_firstId);
            final Password password = this.passwordFinder.filterByUserId(userId)
                .find()
                .stream()
                .findFirst()
                .get();

            if (null == password) {
                return null;
            } else {
                return this.getJdbcTemplate()
                    .query(Queries.query()
                        .append("SELECT id,")
                        .append("       name,")
                        .append("       locked,")
                        .append("       expired_at")
                        .append("  FROM \"user\"")
                        .append(" WHERE id = :id")
                        .toString()
                        .replaceAll(" +", " "),
                            Queries.parameters()
                                .addValue("id", userId, Types.INTEGER),
                            this.getRowMapper(GeneralUser.Builder.class))
                    .stream()
                    .peek(builder -> builder.setPassword(password))
                    .peek(builder -> builder.setPermissions(this.findPermissions(userId)))
                    .map(builder -> builder.build())
                    .findFirst()
                    .orElse(null);
            }
        }

        @Override
        public List<User> find() {
            final boolean byId = (null != this.id);
            final boolean byName = (null != this.name);

            return this.getJdbcTemplate()
                .query(Queries.query()
                    .append("SELECT id,")
                    .append("       name,")
                    .append("       locked,")
                    .append("       expired_at")
                    .append("  FROM \"user\"")
                    .append(" WHERE TRUE")
                    .append("   AND id = :id", byId)
                    .append("   AND name = :name", byName)
                    .toString()
                    .replaceAll(" +", " "),
                        Queries.parameters()
                            .addValue("id", this.id, Types.INTEGER, byId)
                            .addValue("name", this.name, Types.VARCHAR, byName),
                        this.getRowMapper(GeneralUser.Builder.class))
                .stream()
                .peek(builder -> {
                    builder.setPassword(this.passwordFinder.filterByUserId(builder.getId())
                        .find()
                        .stream()
                        .findFirst()
                        .get());
                    builder.setPermissions(this.findPermissions(builder.getId()));
                })
                .map(builder -> builder.build())
                .collect(Collectors.toList());

        }

        private List<Permission> findPermissions(Integer _userId) {
            final List<Permission> permissions = this.permissionFinder.filterByUserId(_userId)
                .find();
            this.roleFinder.filterByUserId(_userId)
                .find()
                .stream()
                .peek(role -> permissions.addAll(role.getPermissions()));

            return permissions.stream()
                .distinct()
                .sorted(Comparator.comparing(Permission::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        }
    }
}
