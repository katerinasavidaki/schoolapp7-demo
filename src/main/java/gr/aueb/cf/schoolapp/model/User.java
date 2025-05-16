package gr.aueb.cf.schoolapp.model;

import gr.aueb.cf.schoolapp.core.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.security.Principal;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Override
    public String getName() {
        return username;
    }
}
