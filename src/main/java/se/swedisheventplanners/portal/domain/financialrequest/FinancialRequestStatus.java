package se.swedisheventplanners.portal.domain.financialrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinancialRequestStatus {

    OPEN("Open"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String label;

}
