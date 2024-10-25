package se.swedisheventplanners.portal.domain.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sep_user")
public class SepUser {

    @Id
    @SequenceGenerator(name = "sep_user_seq", sequenceName = "sep_user_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sep_user_seq")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "hash")
    private String hash;

    @Column(name = "role")
    private Role role;



}
