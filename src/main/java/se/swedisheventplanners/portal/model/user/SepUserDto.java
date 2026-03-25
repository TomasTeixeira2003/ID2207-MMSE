package se.swedisheventplanners.portal.model.user;

import lombok.Data;
import se.swedisheventplanners.portal.domain.user.Role;

import java.io.Serializable;

@Data
public class SepUserDto implements Serializable {

    private Long id;

    private String username;

    private String hash;

    private Role role;

    private boolean active;

}
