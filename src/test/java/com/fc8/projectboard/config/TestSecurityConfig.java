package com.fc8.projectboard.config;

import com.fc8.projectboard.domain.User;
import com.fc8.projectboard.repository.UserRepository;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean
    private UserRepository userRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        given(userRepository.findByUserId(anyString())).willReturn(Optional.of(User.of(
                "reddyongTest",
                "passwordTest",
                "test@email.com",
                "nicknameTest",
                "test memo"
        )));
    }
}
