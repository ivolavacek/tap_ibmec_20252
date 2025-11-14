package br.edu.ibmec.universidade.resource;

import java.util.List;

import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.universidade.entity.Professor;
import br.edu.ibmec.universidade.service.ProfessorService;

@RestController
@RequestMapping("/professores")
public class ProfessorResource {

    @Autowired
    private ProfessorService service;

    @GetMapping
    public List<Professor> listarTodos() throws ServiceException {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Professor buscarPorId(@PathVariable Long id) throws ServiceException {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Professor salvar(@RequestBody Professor professor) throws DaoException {
        return service.salvar(professor);
    }

    @PutMapping("/{id}")
    public Professor atualizar(@PathVariable Long id, @RequestBody Professor professor) throws DaoException {
        professor.setId(id);
        return service.salvar(professor);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) throws ServiceException {
        service.deletar(id);
    }
}
