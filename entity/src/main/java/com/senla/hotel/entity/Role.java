package com.senla.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
@Table(name = "roles")
@SequenceGenerator(
        name = "roles-gen",
        sequenceName = "roles_id_seq",
        initialValue = 1, allocationSize = 1)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles-gen")
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
