package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import lombok.extern.slf4j.Slf4j;
import java.time.Duration;
import java.util.List;

@Slf4j
@Service
public class ConciertoEstadioService {

    @Autowired
    private ConciertoRepository conciertoRepository;
    
    @Autowired
    private EstadioRepository estadioRepository;

    public ConciertoEntity asociarConciertoAEstadio(Long conciertoId, Long estadioId) {
        ConciertoEntity concierto = conciertoRepository.findById(conciertoId)
                .orElseThrow(() -> new IllegalArgumentException("El concierto no existe"));
        
        EstadioEntity estadio = estadioRepository.findById(estadioId)
                .orElseThrow(() -> new IllegalArgumentException("El estadio no existe"));
        
        if (concierto.getCapacidadAforo() > estadio.getCapacidadMaxima()) {
            throw new IllegalArgumentException("La capacidad del concierto no debe superar la capacidad del estadio");
        }
        
        if (estadio.getPrecioAlquiler() > concierto.getPresupuesto()) {
            throw new IllegalArgumentException("El precio de alquiler del estadio no debe superar el presupuesto del concierto");
        }
        
        List<ConciertoEntity> conciertosPrevios = estadio.getConciertos();
        for (ConciertoEntity conciertoPrevio : conciertosPrevios) {
            long diasDiferencia = Duration.between(conciertoPrevio.getFechaConcierto(), concierto.getFechaConcierto()).toDays();
            if (diasDiferencia > 0 && diasDiferencia < 2) {
                throw new IllegalArgumentException("Debe existir un tiempo mínimo de 2 días entre conciertos en el mismo estadio");
            }
        }
        
        concierto.setEstadio(estadio);
        return conciertoRepository.save(concierto);
    }
}

