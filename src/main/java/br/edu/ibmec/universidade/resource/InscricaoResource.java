package br.edu.ibmec.universidade.resource;

import java.util.List;

import br.edu.ibmec.universidade.dto.CriarInscricaoDTO;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.universidade.entity.Inscricao;
import br.edu.ibmec.universidade.service.InscricaoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inscricoes")
public class InscricaoResource {

    @Autowired
    private InscricaoService inscricaoService;

    @GetMapping
    public List<Inscricao> listarTodos() throws ServiceException {
        return inscricaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Inscricao buscarPorId(@PathVariable Long id) throws ServiceException {
        return inscricaoService.buscarPorId(id);
    }

    @PostMapping
    public Inscricao salvar(@RequestBody Inscricao inscricao) throws DaoException {
        return inscricaoService.salvar(inscricao);
    }

    @PostMapping
    public ResponseEntity<Inscricao> criar(@RequestBody CriarInscricaoDTO dto) {
        Inscricao inscricao = inscricaoService.criarInscricao(dto.getAlunoId(), dto.getTurmaId());
        return ResponseEntity.ok(inscricao);
    }

    @PutMapping("/{id}")
    public Inscricao atualizar(@PathVariable Long id, @RequestBody Inscricao inscricao) throws DaoException {
        inscricao.setId(id);
        return inscricaoService.salvar(inscricao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) throws ServiceException {
        inscricaoService.deletar(id);
    }
}
