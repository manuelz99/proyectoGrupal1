package grupo.proyecto.Models;

import grupo.proyecto.Enums.Permits;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "permits")
public class PermitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Permits permit;

    public PermitEntity() {}

    public PermitEntity(Permits permit) {
        this.permit = permit;
    }

    public Long getId() { return id; }
    public Permits getPermit() { return permit; }
    public void setPermit(Permits permit) { this.permit = permit; }
}
