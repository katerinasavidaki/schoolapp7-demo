package gr.aueb.cf.schoolapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "teachers")
public class Teacher extends AbstractEntity implements IdentifiableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String vat;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;
}
