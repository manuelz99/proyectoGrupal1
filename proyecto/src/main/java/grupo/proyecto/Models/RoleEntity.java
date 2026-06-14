package grupo.proyecto.Models;

import grupo.proyecto.Enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Roles role;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permits",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permit_id")
    )
    private final Set<PermitEntity> permits = new HashSet<>();

    public RoleEntity() {}

    public RoleEntity(Roles role) {
        this.role = role;
    }

    public void addPermit(PermitEntity permit) {
        this.permits.add(permit);
    }

    public Long getId() { return id; }
    public Roles getRole() { return role; }
    public Set<PermitEntity> getPermits() { return permits; }
}
