package co.edu.uceva.programa_service.model.service;

import co.edu.uceva.programa_service.model.entities.Programa;
import co.edu.uceva.programa_service.model.dao.IProgramaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Clase implementa el servicio de programa
 * Un servicio es un objeto controlado por el contenedor de Spring
 */
@Service
public class ProgramaServiceImpl implements IProgramaService {

    @Autowired
    IProgramaDao programaDao;

    @Override
    public Programa save(Programa programa) {
        return programaDao.save(programa);
    }

    @Override
    public void delete(Programa programa) {
        programaDao.delete(programa);
    }

    @Override
    public Programa update(Programa programa) {
        return programaDao.save(programa);
    }

    /**
     * Este metodo lista los programas guardados y sus provincias
     * @return una lista de programas
     */
    @Override
    @Transactional(readOnly = true)  //Para ejecutar en modo de solo lectura
    public List<Programa> findAll() {
        List<Programa> programas = (List<Programa>) programaDao.findAll(); //Traemos la lista de programas

        return programas;
    }

    /**
     * Este metodo busca un programa
     * @param id Llave primaria del programa que se quiere buscar
     * @return el programa identificado por el campo id
     */
    @Override
    public Programa findById(Long id) {
        return programaDao.findById(id).get();
    }
}
