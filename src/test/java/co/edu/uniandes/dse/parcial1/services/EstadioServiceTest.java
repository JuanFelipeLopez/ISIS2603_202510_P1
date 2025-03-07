package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstadioServiceTest {

    private EstadioService estadioService;
    private EstadioRepository estadioRepository;
    private EstadioEntity estadio;

    @BeforeEach
    void setUp() {
        estadioService = new EstadioService();
        
        estadio = new EstadioEntity();
        estadio.setCiudad("Bogotá");
        estadio.setCapacidadMaxima(50000);
        estadio.setPrecioAlquiler(150000L);
    }

    @Test
    void testCrearEstadioExitoso() {
        EstadioEntity resultado = estadioService.crearEstadio(estadio);
        assertNotNull(resultado);
        assertEquals(estadio, resultado);
    }

    @Test
    void testCrearEstadioCiudadInvalida() {
        estadio.setCiudad("Bo"); 
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            estadioService.crearEstadio(estadio);
        });
        assertEquals("El nombre de la ciudad debe tener al menos 3 letras", exception.getMessage());
    }
}
