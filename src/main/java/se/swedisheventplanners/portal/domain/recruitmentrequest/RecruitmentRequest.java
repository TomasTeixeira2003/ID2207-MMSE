package se.swedisheventplanners.portal.domain.recruitmentrequest;

import jakarta.persistence.*;
import lombok.Data;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.user.Role;

import java.io.Serializable;

@Data
@Entity
@Table(name = "recruitment_request")
public class RecruitmentRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type")
    private ContractType contractType;

    @Enumerated(EnumType.STRING)
    @Column(name = "requesting_department")
    private RequestingDepartment requestingDepartment;

    @Column(name = "experience")
    private String experience;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "created_by")
    private String createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RecruitmentRequestStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "assigned_to_role")
    private Role assignedToRole;

}
