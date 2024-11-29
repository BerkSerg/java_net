package org.example.demo.bookingservice.model;

import lombok.*;
import org.example.demo.bookingservice.model.enums.Role;
import org.example.demo.bookingservice.model.enums.UserStatus;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Role role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String county;
    private String city;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Min(value = 0)
    @Max(value = 5)
    private double rating;
    private int ratingCount;

    @OneToMany(mappedBy="owner")
    private List<Property> userProperties;

}
