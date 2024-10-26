package se.swedisheventplanners.portal.domain.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sep_user")
public class SepUser {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "hash")
    private String hash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;



}
