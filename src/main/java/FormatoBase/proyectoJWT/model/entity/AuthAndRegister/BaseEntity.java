package FormatoBase.proyectoJWT.model.entity.AuthAndRegister;

import FormatoBase.proyectoJWT.config.SecurityUtil;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Column(name = "fecha_creacion", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaModificacion;

    @Column(name = "usuario_creacion", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'Sistema'")
    private String creadoPor;

    @Column(name = "usuario_modifica", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'Sistema'")
    private String modificadoPor;

    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
        if (this.creadoPor == null || this.creadoPor.isEmpty()) {
            this.creadoPor = SecurityUtil.getCurrentUser();
            if (this.creadoPor.equals("Sistema")) {
                this.creadoPor = "Auto-Registro";
            }
        }
        if (this.modificadoPor == null || this.modificadoPor.isEmpty()) {
            this.modificadoPor = this.creadoPor; // Configurar modificadoPor igual a creadoPor en el momento de la creación
        }
    }


    @PreUpdate
    public void preUpdate() {
        fechaModificacion = LocalDateTime.now();
        this.modificadoPor = SecurityUtil.getCurrentUser();
    }
}