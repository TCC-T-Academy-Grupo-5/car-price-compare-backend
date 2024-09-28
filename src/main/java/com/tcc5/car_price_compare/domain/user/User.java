package com.tcc5.car_price_compare.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc5.car_price_compare.domain.shared.TimestampedEntity;
import com.tcc5.car_price_compare.domain.user.dto.RegisterDTO;
import com.tcc5.car_price_compare.domain.user.features.Favorites;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.domain.user.features.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Entity(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User extends TimestampedEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    private String cellphone;
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Rating> ratings;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Favorites> favorites;

    public User (String firstName, String lastName, String email, String password, String cellphone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cellphone = cellphone;
        this.role = UserRole.USER;
        this.status = UserStatusEnum.ACTIVE;
        this.notifications = new ArrayList<>();
        this.ratings = new ArrayList<>();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return getFirstName() + " " + getLastName();
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
        return this.status == UserStatusEnum.ACTIVE;
    }

    public static User fromDto(RegisterDTO registerDTO, String encryptedPass){
        User user = new User();
        user.firstName = registerDTO.firstName();
        user.lastName = registerDTO.lastName();
        user.email = registerDTO.email();
        user.password = encryptedPass;
        user.cellphone = registerDTO.cellphone();
        user.status = UserStatusEnum.ACTIVE;
        user.role = UserRole.USER;
        return user;
    }
}
