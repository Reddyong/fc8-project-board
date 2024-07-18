package com.fc8.projectboard.dto.security;

import com.fc8.projectboard.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal(
        Long id,
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String memo
) implements UserDetails {

    public static BoardPrincipal of(Long id, String username, String password, String email, String nickname, String memo) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(
                id,
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::name)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                email,
                nickname,
                memo
        );
    }

    public static BoardPrincipal from(UserDto userDto) {
        return BoardPrincipal.of(
                userDto.id(),
                userDto.userId(),
                userDto.password(),
                userDto.email(),
                userDto.nickname(),
                userDto.memo()
        );
    }

    public UserDto toDto() {
        return UserDto.of(
                id,
                username,
                password,
                email,
                nickname,
                memo
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum RoleType {
        USER("ROLE_USER");

        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
