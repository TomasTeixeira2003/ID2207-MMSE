package se.swedisheventplanners.portal.domain.task;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "task")
@Data
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project")
    private String project;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @Column(name = "priority")
    @Enumerated(value = EnumType.STRING)
    private Priority priority;

}
