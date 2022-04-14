package com.senla.hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="lodgers")
@SequenceGenerator(
        name = "lodger-gen",
        sequenceName = "lodgers_id_seq",
        initialValue = 1, allocationSize = 1)
public class Lodger {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lodger-gen")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="phone_number")
    private String phoneNumber;

    public Lodger(String firstName, String secondName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = secondName;
        this.phoneNumber = phoneNumber;
    }
}
