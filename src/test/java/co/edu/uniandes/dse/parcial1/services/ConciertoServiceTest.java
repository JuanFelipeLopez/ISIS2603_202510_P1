package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ConciertoServiceTest {

    private ConciertoService conciertoService;
    private ConciertoRepository conciertoRepository;
    private ConciertoEntity concierto;

    @BeforeEach
    void setUp() {
        conciertoService = new ConciertoService();
        
        concierto = new ConciertoEntity();
        concierto.setFechaConcierto(LocalDateTime.now().plusDays(5));
        concierto.setCapacidadAforo(50); 
        concierto.setPresupuesto(5000L); 
    }

    @Test
    void testCrearConciertoExitoso() {
        ConciertoEntity resultado = conciertoService.crearConcierto(concierto);
        assertNotNull(resultado);
        assertEquals(concierto.getFechaConcierto(), resultado.getFechaConcierto());
        assertEquals(concierto.getCapacidadAforo(), resultado.getCapacidadAforo());
        assertEquals(concierto.getPresupuesto(), resultado.getPresupuesto());
        assertTrue(conciertoRepository.existsById(resultado.getId()));
    }
    
    @Test
    void testCrearConciertoFechaPasada() {
        concierto.setFechaConcierto(LocalDateTime.now().minusDays(1));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            conciertoService.crearConcierto(concierto);
        });
        assertEquals("La fecha del concierto no puede estar en el pasado", exception.getMessage());
    }
}
