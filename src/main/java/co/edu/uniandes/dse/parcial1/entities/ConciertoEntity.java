package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private Long presupuesto;
    private String nombreArtista;
    private LocalDateTime fechaConcierto;
    private int capacidadAforo;

    @ManyToOne
    private EstadioEntity estadio;

}
