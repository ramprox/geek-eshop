package ru.ramprox.service.auth.database.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ramprox.common.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users", indexes = @Index(columnList = "username", name = "username_uq", unique = true))
@SequenceGenerator(name = AbstractEntity.SEQUENCE_GEN_NAME,
        sequenceName = "users_seq_gen", allocationSize = 1)
public class User extends AbstractEntity {

    @NotBlank
    @Column(nullable = false)
    private String username;

    @NotNull
    @Min(18)
    @Max(100)
    @Column(nullable = false)
    private Integer age;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Size(min = 1)
    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_usersroles_user_id")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_usersroles_role_id")))
    private Set<@NotNull Role> roles = new HashSet<>();

    public User(String username, String password, Integer age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.addUser(this);
    }

    public boolean removeRole(Role role) {
        boolean removed = roles.remove(role);
        if(removed) {
            role.removeUser(this);
        }
        return removed;
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof User)) return false;
        User other = (User) o;
        Long id = getId();
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
