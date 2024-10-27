package se.swedisheventplanners.portal.domain.recruitmentrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecruitmentRequestStatus  {

    DRAFT("Draft"),
    IN_PROGRESS("In Progress"),
    FULFILLED("Fulfilled");

    private final String label;

}
