package se.swedisheventplanners.portal.domain.recruitmentrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContractType {

    FULL_TIME("Full Time"),
    PART_TIME("Part Time");

    private final String label;

}
