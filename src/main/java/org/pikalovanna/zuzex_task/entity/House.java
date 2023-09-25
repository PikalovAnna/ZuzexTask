package org.pikalovanna.zuzex_task.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "houses")
public class House {
    @Access(AccessType.PROPERTY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column
    String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    User owner;

    @OneToMany
    @JoinTable(name = "house_roomers",
            joinColumns = @JoinColumn(name = "id_house"),
            inverseJoinColumns = @JoinColumn(name = "id_user"))
    Set<User> roomers = new HashSet<>();
}
