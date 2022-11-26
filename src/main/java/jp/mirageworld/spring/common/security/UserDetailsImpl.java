package jp.mirageworld.spring.common.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.mirageworld.spring.common.model.Users;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class UserDetailsImpl 
    implements UserDetails {

        final Users users ;

        public Users getUsers() {
            return this.users;
        }
        
        @Override
        public String getUsername() {
        return users.getUsername();
        }

        @Override
        public String getPassword() {
            return users.getPassword();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return !users.isLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return users.isEnabled()
                && Objects.isNull(users.getDeleted());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                // 初期設定では USER 限定
                Set<GrantedAuthority> authorities = new HashSet<>();
                authorities.add(new SimpleGrantedAuthority("USER"));
                return authorities;
        }
}
