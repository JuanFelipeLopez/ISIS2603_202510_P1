package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ConciertoService {
    
    @Autowired
    private ConciertoRepository conciertoRepository;
    
    public ConciertoEntity crearConcierto(ConciertoEntity concierto) {
        if (concierto.getFechaConcierto().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha del concierto no puede estar en el pasado");
        }
        if (concierto.getCapacidadAforo() <= 10) {
            throw new IllegalArgumentException("La capacidad del concierto debe ser superior a 10 personas");
        }
        if (concierto.getPresupuesto() <= 1000) {
            throw new IllegalArgumentException("El presupuesto del concierto debe ser superior a 1000 dólares");
        }
        return conciertoRepository.save(concierto);
    }
}
