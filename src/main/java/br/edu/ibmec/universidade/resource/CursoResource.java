package br.edu.ibmec.universidade.resource;

import br.edu.ibmec.universidade.dto.CursoDTO;
import br.edu.ibmec.universidade.entity.Curso;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.exception.ServiceException.ServiceExceptionEnum;
import br.edu.ibmec.universidade.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/curso", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Cursos", description = "Operações de gestão de cursos")
public class CursoResource {

    private final CursoService cursoService; // injetado via construtor (Lombok)

    @GetMapping("/{codigo}")
    @Operation(summary = "Buscar curso por código")
    public ResponseEntity<CursoDTO> buscarCurso(@PathVariable int codigo) {
        try {
            CursoDTO dto = cursoService.buscarCurso(codigo);
            return ResponseEntity.ok(dto);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Cadastrar curso")
    public ResponseEntity<Void> cadastrarCurso(@RequestBody CursoDTO cursoDTO) {
        try {
            cursoService.cadastrarCurso(cursoDTO);
            return ResponseEntity.created(URI.create("/curso/" + cursoDTO.getCodigo())).build();
        } catch (ServiceException e) {
            if (e.getTipo() == ServiceExceptionEnum.CURSO_CODIGO_INVALIDO) {
                return ResponseEntity.badRequest().header("Motivo", "Código inválido").build();
            } else if (e.getTipo() == ServiceExceptionEnum.CURSO_NOME_INVALIDO) {
                return ResponseEntity.badRequest().header("Motivo", "Nome inválido").build();
            } else {
                return ResponseEntity.badRequest().header("Motivo", e.getMessage()).build();
            }
        }
    }

    @PutMapping(consumes = "application/json")
    @Operation(summary = "Alterar curso")
    public ResponseEntity<Void> alterarCurso(@RequestBody CursoDTO cursoDTO) {
        try {
            cursoService.alterarCurso(cursoDTO);
            return ResponseEntity.created(URI.create("/curso/" + cursoDTO.getCodigo())).build();
        } catch (ServiceException e) {
            if (e.getTipo() == ServiceExceptionEnum.CURSO_CODIGO_INVALIDO) {
                return ResponseEntity.badRequest().header("Motivo", "Código inválido").build();
            } else if (e.getTipo() == ServiceExceptionEnum.CURSO_NOME_INVALIDO) {
                return ResponseEntity.badRequest().header("Motivo", "Nome inválido").build();
            } else {
                return ResponseEntity.badRequest().header("Motivo", e.getMessage()).build();
            }
        } catch (DaoException e) {
            return ResponseEntity.badRequest().header("Motivo", "Erro no banco de dados").build();
        }
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Remover curso")
    public ResponseEntity<Void> removerCurso(@PathVariable int codigo) {
        try {
            cursoService.removerCurso(codigo);
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Listar nomes dos cursos")
    public ResponseEntity<List<String>> listarCursos() {
        List<String> nomes = cursoService.listarCursos()
                .stream()
                .map(Curso::getNome)
                .toList();
        return ResponseEntity.ok(nomes);
    }
}
