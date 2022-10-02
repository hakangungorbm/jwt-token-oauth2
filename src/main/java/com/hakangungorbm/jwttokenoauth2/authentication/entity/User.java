package com.hakangungorbm.jwttokenoauth2.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * @author HakanGungorBm
 * @date 31.07.2022
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "oauth_user")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "username", unique=true)
    private String username;

    @Column(name = "enabled")
    private boolean enabled;

    @NotEmpty
    @Size(min=4)
    @Column(name = "password", nullable=false)
    private String password;


    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private Set<Role> roles = new HashSet<>();



}
