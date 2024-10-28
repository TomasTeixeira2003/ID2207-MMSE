package se.swedisheventplanners.portal.service.role.impl;

import org.junit.jupiter.api.Test;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.service.role.RoleService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleServiceFactoryImplTest {

    private AdministrationManagerRoleService administrationManagerRoleService;
    private CustomerSupportOfficerRoleService customerSupportOfficerRoleService;
    private SeniorCustomerSupportOfficerRoleService seniorCustomerSupportOfficerRoleService;
    private FinancialManagerRoleService financialManagerRoleService;
    private HrManagerRoleService hrManagerRoleService;
    private ProductionManagerRoleService productionManagerRoleService;
    private ProductionSubTeamRoleService productionSubTeamRoleService;
    private ServicesManagerRoleService servicesManagerRoleService;
    private ServicesSubTeamRoleService servicesSubTeamRoleService;

    private RoleServiceFactoryImpl roleServiceFactory;

    public void setUp() {
        List<RoleService> roleServices = new ArrayList<>();
        administrationManagerRoleService = new AdministrationManagerRoleService();
        roleServices.add(administrationManagerRoleService);
        customerSupportOfficerRoleService = new CustomerSupportOfficerRoleService();
        roleServices.add(customerSupportOfficerRoleService);
        seniorCustomerSupportOfficerRoleService = new SeniorCustomerSupportOfficerRoleService();
        roleServices.add(seniorCustomerSupportOfficerRoleService);
        financialManagerRoleService = new FinancialManagerRoleService();
        roleServices.add(financialManagerRoleService);
        hrManagerRoleService = new HrManagerRoleService();
        roleServices.add(hrManagerRoleService);
        productionManagerRoleService = new ProductionManagerRoleService();
        roleServices.add(productionManagerRoleService);
        productionSubTeamRoleService = new ProductionSubTeamRoleService();
        roleServices.add(productionSubTeamRoleService);
        servicesManagerRoleService = new ServicesManagerRoleService();
        roleServices.add(servicesManagerRoleService);
        servicesSubTeamRoleService = new ServicesSubTeamRoleService();
        roleServices.add(servicesSubTeamRoleService);
        roleServiceFactory = new RoleServiceFactoryImpl(roleServices);
    }

    @Test
    void getRoleService() {
        setUp();
        assertEquals(administrationManagerRoleService, roleServiceFactory.getRoleService(Role.ADMINISTRATION_MANAGER));
        assertEquals(customerSupportOfficerRoleService, roleServiceFactory.getRoleService(Role.CUSTOMER_SUPPORT_OFFICER));
        assertEquals(seniorCustomerSupportOfficerRoleService, roleServiceFactory.getRoleService(Role.SENIOR_CUSTOMER_SUPPORT_OFFICER));
        assertEquals(financialManagerRoleService, roleServiceFactory.getRoleService(Role.FINANCIAL_MANAGER));
        assertEquals(hrManagerRoleService, roleServiceFactory.getRoleService(Role.HR_MANAGER));
        assertEquals(productionManagerRoleService, roleServiceFactory.getRoleService(Role.PRODUCTION_MANAGER));
        assertEquals(productionSubTeamRoleService, roleServiceFactory.getRoleService(Role.PRODUCTION_SUB_TEAM));
        assertEquals(servicesManagerRoleService, roleServiceFactory.getRoleService(Role.SERVICES_MANAGER));
        assertEquals(servicesSubTeamRoleService, roleServiceFactory.getRoleService(Role.SERVICES_SUB_TEAM));
    }
}