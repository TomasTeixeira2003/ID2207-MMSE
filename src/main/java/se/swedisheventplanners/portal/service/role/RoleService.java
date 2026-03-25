package se.swedisheventplanners.portal.service.role;

import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.routing.PageLink;

import java.util.List;

public interface RoleService {

    Role getRole();

    List<PageLink> getRolePageLinks(Long userId);

}
