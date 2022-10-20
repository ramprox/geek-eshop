package ru.ramprox.service.auth.database.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.ramprox.common.entity.AbstractEntity;
import ru.ramprox.common.entity.NamedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "roles",
        indexes = @Index(columnList = "name", name = "rolename_uq", unique = true))
@SequenceGenerator(name = AbstractEntity.SEQUENCE_GEN_NAME,
        sequenceName = "roles_seq_gen", allocationSize = 1)
public class Role extends NamedEntity {

    @ManyToMany(mappedBy = "roles")
    private Set<@NotNull User> users = new HashSet<>();

    public Role(String name) {
        super(name);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        Long id = getId();
        return id != null && id.equals(role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
