package com.vart.springbootsecurityjwtdemo.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserDetails{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(name = "username", unique = true)
    @NotNull
    @Length(max = 45)
    public String username;

    @Column(name = "password")
    @NotNull
    @Length(max = 255, min = 6)
    public String password;

    @ManyToMany (cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)//级联更新策略,这2个不设置会有异常
    @JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    public List<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.rolename));
        }
        return list;
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

    /*
    这个方法如果返回false，登入时会有
    org.springframework.security.authentication.LockedException: User account is locked
    的异常，且没有任何提示，困扰了我一天。。
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
    账户是否不存在过期问题，如果返回false，登入的时候会有
    CredentialsExpiredException: User credentials have expired
    的异常
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
