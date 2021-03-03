package com.github.aetherwisp.volvox.application.index;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aetherwisp.volvox.test.CommonOperations;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class IndexControllerTest {
    //======================================================================
    // Fields
    final MockMvc mockMvc;

    final DataSource dataSource;

    final PasswordEncoder passwordEncoder;

    final ObjectMapper mapper = new ObjectMapper();

    //======================================================================
    // Constructors
    @Autowired
    public IndexControllerTest(final MockMvc _mockMvc, final DataSource _dataSource, final PasswordEncoder _passwordEncoder) {
        this.mockMvc = Objects.requireNonNull(_mockMvc);
        this.dataSource = Objects.requireNonNull(_dataSource);
        this.passwordEncoder = Objects.requireNonNull(_passwordEncoder);
    }


    //======================================================================
    // Tests
    @Nested
    @DisplayName("ログイン画面表示")
    public class ShowTest {
        @BeforeEach
        public void setUp() {
            //======================================================================
            // テストデータ登録
            final LocalDateTime sysdate = LocalDateTime.now();
            final Operation operation = Operations.sequenceOf(CommonOperations.DELETE_ALL_TABLES, Operations.insertInto("user")
                .columns("name", "locked", "expired_at", "created_at", "created_by", "updated_at", "updated_by")
                .values("テストユーザ", Boolean.FALSE, sysdate.plusDays(1L), sysdate, Integer.valueOf(1), sysdate, Integer.valueOf(1))
                .build());
            final DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
            dbSetup.launch();
        }

        @Test
        @DisplayName("ログイン画面へリダイレクト")
        public void redirectIndexPage() throws Exception {
            mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isMovedPermanently())
                .andExpect(redirectedUrl("index"));

        }

        @Test
        @DisplayName("初期表示")
        public void showIndexPage() throws Exception {
            mockMvc.perform(get("/index").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().encoding(StandardCharsets.UTF_8.name()))
                .andExpect(view().name("index"));

        }
    }

    @Nested
    @DisplayName("ログイン処理")
    public class LoginTest {
        @BeforeEach
        public void setUp() {
            //======================================================================
            // テストデータ登録
            final LocalDateTime sysdate = LocalDateTime.now();
            final Operation operation = Operations.sequenceOf(Arrays.asList(CommonOperations.DELETE_ALL_TABLES,
                    Operations.insertInto("role")
                        .columns("id", "name", "created_at", "created_by", "updated_at", "updated_by")
                        .values(Integer.valueOf(1), "admin", sysdate, Integer.valueOf(1), sysdate, Integer.valueOf(1))
                        .build(),
                    Operations.insertInto("permission")
                        .columns("id", "name", "created_at", "created_by", "updated_at", "updated_by")
                        .values(Integer.valueOf(1), "all", sysdate, Integer.valueOf(1), sysdate, Integer.valueOf(1))
                        .values(Integer.valueOf(2), "test", sysdate, Integer.valueOf(1), sysdate, Integer.valueOf(1))
                        .build(),
                    Operations.insertInto("user")
                        .columns("id", "name", "locked", "expired_at", "created_at", "created_by", "updated_at", "updated_by")
                        .values(Integer.valueOf(1), "test-user", Boolean.FALSE, sysdate.plusDays(1L), sysdate, Integer.valueOf(1),
                                sysdate, Integer.valueOf(1))
                        .build(),
                    Operations.insertInto("user_password")
                        .columns("user_id", "password", "expired_at", "created_at", "created_by", "updated_at", "updated_by")
                        .values(Integer.valueOf(1), passwordEncoder.encode("password"), sysdate.plusDays(1L), sysdate,
                                Integer.valueOf(1), sysdate, Integer.valueOf(1))
                        .build(),
                    Operations.insertInto("user_roles")
                        .columns("user_id", "role_id", "created_at", "created_by", "updated_at", "updated_by")
                        .values(Integer.valueOf(1), Integer.valueOf(1), sysdate, Integer.valueOf(1), sysdate, Integer.valueOf(1))
                        .build(),
                    Operations.insertInto("role_permissions")
                        .columns("role_id", "permission_id", "created_at", "created_by", "updated_at", "updated_by")
                        .values(Integer.valueOf(1), Integer.valueOf(1), sysdate, Integer.valueOf(1), sysdate, Integer.valueOf(1))
                        .build(),
                    Operations.insertInto("user_permissions")
                        .columns("user_id", "permission_id", "created_at", "created_by", "updated_at", "updated_by")
                        .values(Integer.valueOf(1), Integer.valueOf(2), sysdate, Integer.valueOf(1), sysdate, Integer.valueOf(1))
                        .build()));
            final DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
            dbSetup.launch();
        }

        @Test
        @DisplayName("ログイン成功")
        public void showPage() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "test-user")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isFound())
                .andExpect(content().encoding(StandardCharsets.UTF_8.name()))
                .andExpect(redirectedUrl("/dashboard"));

            System.err.println("password: " + passwordEncoder.encode("password"));

        }
    }
}
