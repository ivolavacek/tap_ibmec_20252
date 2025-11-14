package br.edu.ibmec.universidade.resource;

import java.util.List;

import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.universidade.entity.Inscricao;
import br.edu.ibmec.universidade.service.InscricaoService;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoResource {

    @Autowired
    private InscricaoService service;

    @GetMapping
    public List<Inscricao> listarTodos() throws ServiceException {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Inscricao buscarPorId(@PathVariable Long id) throws ServiceException {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Inscricao salvar(@RequestBody Inscricao inscricao) throws DaoException {
        return service.salvar(inscricao);
    }

    @PutMapping("/{id}")
    public Inscricao atualizar(@PathVariable Long id, @RequestBody Inscricao inscricao) throws DaoException {
        inscricao.setId(id);
        return service.salvar(inscricao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) throws ServiceException {
        service.deletar(id);
    }
}
