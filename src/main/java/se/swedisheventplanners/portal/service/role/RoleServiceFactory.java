package se.swedisheventplanners.portal.service.role;

import se.swedisheventplanners.portal.domain.user.Role;

public interface RoleServiceFactory {

    RoleService getRoleService(Role role);

}
