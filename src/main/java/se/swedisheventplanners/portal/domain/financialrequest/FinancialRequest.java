package se.swedisheventplanners.portal.domain.financialrequest;

import jakarta.persistence.*;
import lombok.Data;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RequestingDepartment;
import se.swedisheventplanners.portal.domain.user.Role;

import java.io.Serializable;

@Data
@Entity
@Table(name = "financial_request")
public class FinancialRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "requesting_department")
    private RequestingDepartment requestingDepartment;

    @Column(name = "project_reference")
    private String projectReference;

    @Column(name = "required_amount")
    private String requiredAmount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_by")
    private String createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FinancialRequestStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "assigned_to_role")
    private Role assignedToRole;
}
