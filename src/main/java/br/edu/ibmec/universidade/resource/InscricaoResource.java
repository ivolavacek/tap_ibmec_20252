package br.edu.ibmec.universidade.resource;

import java.net.URI;

import br.edu.ibmec.universidade.dto.CriarInscricaoDTO;
import br.edu.ibmec.universidade.exception.DaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.universidade.entity.Inscricao;
import br.edu.ibmec.universidade.service.InscricaoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inscricoes")
public class InscricaoResource {

    private final InscricaoService inscricaoService;

    @GetMapping("/{id}")
    public ResponseEntity<Inscricao> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(inscricaoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody CriarInscricaoDTO dto) {
        try {
            Inscricao inscricao = inscricaoService.criarInscricao(dto.getAlunoId(), dto.getTurmaId());
            return ResponseEntity.created(URI.create("/inscricoes/" + inscricao.getId())).body(inscricao);
        } catch (DaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/avaliacao1")
    public ResponseEntity<?> patchAvaliacao1(@PathVariable Long id, @RequestBody Float avaliacao1) {
        try {
            Inscricao i = inscricaoService.atualizarAvaliacao1(id, avaliacao1);
            return ResponseEntity.ok(i);
        } catch (DaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/avaliacao2")
    public ResponseEntity<?> patchAvaliacao2(@PathVariable Long id, @RequestBody Float avaliacao2) {
        try {
            Inscricao i = inscricaoService.atualizarAvaliacao2(id, avaliacao2);
            return ResponseEntity.ok(i);
        } catch (DaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/media")
    public ResponseEntity<?> patchMedia(@PathVariable Long id, @RequestBody Float media) {
        try {
            Inscricao i = inscricaoService.atualizarMedia(id, media);
            return ResponseEntity.ok(i);
        } catch (DaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/faltas")
    public ResponseEntity<?> patchFaltas(@PathVariable Long id, @RequestBody Integer faltas) {
        try {
            Inscricao i = inscricaoService.atualizarFaltas(id, faltas);
            return ResponseEntity.ok(i);
        } catch (DaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/situacao")
    public ResponseEntity<?> patchSituacao(@PathVariable Long id, @RequestBody String situacao) {
        try {
            Inscricao i = inscricaoService.atualizarSituacao(id, situacao);
            return ResponseEntity.ok(i);
        } catch (DaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
