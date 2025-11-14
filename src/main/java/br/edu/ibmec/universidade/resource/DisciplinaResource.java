package br.edu.ibmec.universidade.resource;

import java.net.URI;
import java.util.List;

import br.edu.ibmec.universidade.dto.DisciplinaDTO;
import br.edu.ibmec.universidade.entity.Disciplina;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.service.DisciplinaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplinas")
@RequiredArgsConstructor
@Tag(name = "Disciplinas")
public class DisciplinaResource {

    private final DisciplinaService service;

    @GetMapping
    public List<Disciplina> listarTodos() throws ServiceException {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Disciplina buscarPorId(@PathVariable Long id) throws ServiceException {
        return service.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Criar disciplina e gerar turma automaticamente")
    public ResponseEntity<Disciplina> criarDisciplina(@RequestBody DisciplinaDTO dto) {
        try {
            Disciplina d = service.criarDisciplina(dto);
            return ResponseEntity.created(URI.create("/disciplinas/" + d.getId())).body(d);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public Disciplina atualizar(@PathVariable Long id, @RequestBody Disciplina disciplina) throws DaoException {
        disciplina.setId(id);
        return service.salvar(disciplina);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) throws ServiceException {
        service.deletar(id);
    }
}
