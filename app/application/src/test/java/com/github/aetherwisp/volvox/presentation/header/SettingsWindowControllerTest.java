package com.github.aetherwisp.volvox.presentation.header;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SettingsWindowControllerTest {
    //======================================================================
    // Fields
    final MockMvc mockMvc;

    //======================================================================
    // Constructors
    @Autowired
    public SettingsWindowControllerTest(final MockMvc _mockMvc) {
        this.mockMvc = Objects.requireNonNull(_mockMvc);
    }


    //======================================================================
    // Tests
    @Test
    @DisplayName("初期表示")
    public void showIndexPage() throws Exception {
        mockMvc.perform(get("/app/header/SettingsWindow.js").contentType("application/javascript;charset=UTF-8")
            .characterEncoding(StandardCharsets.UTF_8.name())
            .with(csrf())
            .with(user("user")))
            .andDo(print())
            .andExpect(content().contentType("application/javascript;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().encoding(StandardCharsets.UTF_8.name()))
            .andExpect(view().name("app/header/SettingsWindow"));

    }
}
