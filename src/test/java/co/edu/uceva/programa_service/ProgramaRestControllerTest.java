package co.edu.uceva.programa_service;

import co.edu.uceva.programa_service.model.entities.Programa;
import co.edu.uceva.programa_service.model.service.IProgramaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Pruebas unitarias (unit tests) para la  API RESTful que se encarga de realizar operaciones CRUD sobre una entidad
 * llamada "Pais".
 * Se importan las clases necesarias para realizar las pruebas (MockMvc, ObjectMapper, etc.), se inyecta el servicio
 * que se encarga de realizar las operaciones sobre la entidad "Pais" (IPaisService), y se definen varios métodos de
 * prueba para la API RESTful, que comprueban el correcto funcionamiento de los métodos:
 * GET, POST, PUT y DELETE de la API.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramaRestControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private IProgramaService paisService;

    /**
     * Inicializa los objetos necesarios para la prueba. En el ejemplo de código dado, este método se utiliza para \
     * inicializar el objeto MockMvc, que se utiliza para simular el envío de solicitudes HTTP en la prueba de la  \
     * clase PaisRestController.
     */
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Prueba del método GET "/pais-service/hola/{nombre}", que comprueba que se recibe el nombre correcto
     * en la respuesta.
     * @throws Exception
     */
    @Test
    public void testHolaMundo() throws Exception {
        String nombre = "Juan";
        this.mockMvc.perform(get("/pais-service/hola/{nombre}", nombre))
                .andExpect(status().isOk())
                .andExpect(content().string("Hola " + nombre));
    }

    /**
     * Prueba del método GET "/pais-service/paises", que comprueba que se recibe una lista de países en la respuesta.
     * @throws Exception
     */
    @Test
    public void testListar() throws Exception {
        Programa pais1 = new Programa();
        pais1.setNombre("croacia");
        Programa pais2 = new Programa();
        pais2.setNombre("España");
        paisService.save(pais1);
        paisService.save(pais2);
        List<Programa> listaPaises = new ArrayList<>();
        listaPaises.add(pais1);
        listaPaises.add(pais2);

        this.mockMvc.perform(get("/pais-service/paises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is(pais1.getNombre())))
                .andExpect(jsonPath("$[1].nombre", is(pais2.getNombre())));

        paisService.delete(pais1);
        paisService.delete(pais2);
    }

    /**
     * Prueba del método GET "/pais-service/paises/{id}", que comprueba que se recibe el país correcto en la respuesta.
     * @throws Exception
     */
    @Test
    public void testBuscarPais() throws Exception {
        Programa pais = new Programa();
        pais.setNombre("España");
        paisService.save(pais);

        this.mockMvc.perform(get("/pais-service/paises/{id}", pais.getId_programa()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(pais.getNombre())));

        paisService.delete(pais);
    }

    /**
     * Prueba del método POST "/pais-service/paises", que comprueba que se crea un nuevo país correctamente.
     * @throws Exception
     */
    @Test
    public void testCrearPais() throws Exception {
        Programa pais = new Programa();
        pais.setNombre("España");

        this.mockMvc.perform(post("/pais-service/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pais)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(pais.getNombre())));

        paisService.delete(pais);
    }

    /**
     * Prueba del método PUT "/pais-service/paises", que comprueba que se actualiza un país correctamente.
     * @throws Exception
     */
    @Test
    public void testActualizarPais() throws Exception {
        Programa pais = new Programa();
        pais.setNombre("España");
        paisService.save(pais);
        pais.setNombre("Portugal");

        this.mockMvc.perform(put("/pais-service/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pais)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(pais.getNombre())));
//                .andExpect(jsonPath("$.capital", is(pais.getNombre())));

        paisService.delete(pais);
    }

    /**
     * Prueba del método DELETE "/pais-service/paises/{id}", que comprueba que se elimina un país correctamente.
     * @throws Exception
     */
    @Test
    public void testBorrarPais() throws Exception {
        Programa pais = new Programa();
        pais.setNombre("Canada");
        paisService.save(pais);

        this.mockMvc.perform(delete("/pais-service/paises/{id}", pais.getId_programa()))
                .andExpect(status().isOk());

        assertNull(paisService.findById(pais.getId_programa()));
    }

    /**
     * Método para convertir un objeto a una cadena JSON
     *
     * @param obj Objeto a convertir
     * @return Cadena JSON
     */
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
