package se.swedisheventplanners.portal.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    CUSTOMER_SUPPORT_OFFICER("Customer Support Officer"),
    SENIOR_CUSTOMER_SUPPORT_OFFICER("Senior Customer Support Officer"),
    HR_MANAGER("HR Manager"),
    FINANCIAL_MANAGER("Financial Manager"),
    SERVICES_MANAGER("Services Manager"),
    PRODUCTION_MANAGER("Production Manager"),
    SERVICES_SUB_TEAM("Services Sub Team"),
    PRODUCTION_SUB_TEAM("Production Sub Team"),
    ADMINISTRATION_MANAGER("Administration Manager");

    private final String label;

}
