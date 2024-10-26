package se.swedisheventplanners.portal.service.role.impl;

import org.springframework.stereotype.Component;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.service.role.RoleService;
import se.swedisheventplanners.portal.service.role.RoleServiceFactory;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class RoleServiceFactoryImpl implements RoleServiceFactory {

    private final Map<Role, RoleService> roleServicesMap;

    private RoleServiceFactoryImpl(List<RoleService> roleServices) {
        roleServicesMap = new EnumMap<>(Role.class);
        roleServices.forEach(roleService -> roleServicesMap.put(roleService.getRole(), roleService));
    }

    @Override
    public RoleService getRoleService(Role role) {
        RoleService roleService = roleServicesMap.get(role);
        if (roleService == null)
            throw new IllegalArgumentException("No RoleService found for role: " + role);

        return roleService;
    }
}
