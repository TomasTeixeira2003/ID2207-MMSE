package se.swedisheventplanners.portal.domain.recruitmentrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestingDepartment  {

    SERVICES("Services"),
    ADMINISTRATION("Administration"),
    PRODUCTION("Production"),
    FINANCIAL("Financial");

    private final String label;

}
