package co.edu.uceva.programa_service.controller;

import co.edu.uceva.programa_service.model.entities.Programa;
import co.edu.uceva.programa_service.model.service.IProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*") //Se permite el Cross Origin a http://localhost:4200
@RequestMapping("/programa-service")
public class ProgramaRestController {

    @Autowired
    IProgramaService programaService;

    /**
     * Endpoint para recibir un saludo
     * @param nombre Es el nombre que envian desde la url
     * @return Un saludo
     */
    @GetMapping("/hola/{nombre}")
    public String holaMundo(@PathVariable("nombre") String nombre){
        return "Hola "+ nombre;
    }

    /**
     * Metodo que lista los programas
     * @return Lista de programas
     */
    @GetMapping("/programas")
    public List<Programa> listar(){
        return programaService.findAll();
    }

    @GetMapping("/programas/{id_programa}")
    public Programa buscarPrograma(@PathVariable("id_programa") Long id){
        return programaService.findById(id);
    }

    @PostMapping("/programas")
    public Programa crearPrograma(@RequestBody Programa programa){
        return programaService.save(programa);
    }

    @DeleteMapping("/programas/{id_programa}")
    public void borrarPrograma(@PathVariable("id_programa") Long id){
        Programa programa;
        programa = programaService.findById(id);
        programaService.delete(programa);
    }

    @PutMapping("/programas")
    public Programa actualizarPrograma(@RequestBody Programa programa){
        return programaService.update(programa);
    }

}
