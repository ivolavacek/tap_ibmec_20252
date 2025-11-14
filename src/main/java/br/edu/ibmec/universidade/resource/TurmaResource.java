package br.edu.ibmec.universidade.resource;

import java.util.List;

import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.universidade.entity.Turma;
import br.edu.ibmec.universidade.service.TurmaService;

@RestController
@RequestMapping("/turmas")
public class TurmaResource {

    @Autowired
    private TurmaService service;

    @GetMapping
    public List<Turma> listarTodos() throws ServiceException {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Turma buscarPorId(@PathVariable Long id) throws ServiceException {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Turma salvar(@RequestBody Turma turma) throws DaoException {
        return service.salvar(turma);
    }

    @PutMapping("/{id}")
    public Turma atualizar(@PathVariable Long id, @RequestBody Turma turma) throws DaoException {
        turma.setId(id);
        return service.salvar(turma);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) throws ServiceException {
        service.deletar(id);
    }
}
