package se.swedisheventplanners.portal.domain.event;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "test_table")
public class TestTable {

    @Id
    @SequenceGenerator(name = "test_table_seq", sequenceName = "test_table_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_table_seq")
    private Long id;

    @Column(name = "name")
    private String name;
}
