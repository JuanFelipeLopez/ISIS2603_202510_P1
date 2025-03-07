package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class ConciertoEstadioServiceTest {

    private ConciertoEstadioService conciertoEstadioService;
    private ConciertoRepository conciertoRepository;
    private EstadioRepository estadioRepository;
    private ConciertoEntity concierto;
    private EstadioEntity estadio;

    @BeforeEach
    void setUp() {
        conciertoRepository = new ConciertoRepository();
        estadioRepository = new EstadioRepository();
        conciertoEstadioService = new ConciertoEstadioService();

        estadio = new EstadioEntity();
        estadio.setNombre("Estadio Nacional");
        estadio.setCapacidadMaxima(50000);
        estadio.setPrecioAlquiler(80000L);
        
        concierto = new ConciertoEntity();
        concierto.setFechaConcierto(LocalDateTime.now().plusDays(5));
        concierto.setCapacidadAforo(30000);
        concierto.setPresupuesto(100000L);
    }

    @Test
    void testAsociarConciertoAEstadioExitoso() {
        estadioRepository.save(estadio);
        conciertoRepository.save(concierto);
        
        ConciertoEntity resultado = conciertoEstadioService.asociarConciertoAEstadio(concierto.getId(), estadio.getId());
        assertNotNull(resultado);
        assertEquals(estadio.getId(), resultado.getEstadio().getId());
    }

    @Test
    void testAsociarConciertoConCapacidadExcedida() { //Deberia funcionar, lo juro
        estadioRepository.save(estadio);
        concierto.setCapacidadAforo(60000); 
        conciertoRepository.save(concierto);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            conciertoEstadioService.asociarConciertoAEstadio(concierto.getId(), estadio.getId());
        });
        assertEquals("La capacidad del concierto no debe superar la capacidad del estadio", exception.getMessage());
    }
}
