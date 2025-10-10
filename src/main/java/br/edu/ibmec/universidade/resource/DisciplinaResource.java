package br.edu.ibmec.universidade.resource;

import br.edu.ibmec.universidade.dto.DisciplinaDTO;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.exception.ServiceException.ServiceExceptionEnum;
import br.edu.ibmec.universidade.service.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/disciplina", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Disciplinas", description = "Operações de gestão de disciplinas")
public class DisciplinaResource {

    private final DisciplinaService disciplinaService;

    @GetMapping("/{codigo}")
    @Operation(summary = "Buscar disciplina por código")
    public ResponseEntity<DisciplinaDTO> buscarDisciplina(@PathVariable int codigo) {
        try {
            DisciplinaDTO dto = disciplinaService.buscarDisciplina(codigo);
            return ResponseEntity.ok(dto);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Cadastrar disciplina")
    public ResponseEntity<Void> cadastrarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            disciplinaService.cadastrarDisciplina(disciplinaDTO);
            return ResponseEntity.created(URI.create("/disciplina/" + disciplinaDTO.getCodigo())).build();
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

    @PutMapping(consumes = "application/json")
    @Operation(summary = "Alterar disciplina")
    public ResponseEntity<Void> alterarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            disciplinaService.alterarDisciplina(disciplinaDTO);
            return ResponseEntity.created(URI.create("/disciplina/" + disciplinaDTO.getCodigo())).build();
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
    @Operation(summary = "Remover disciplina")
    public ResponseEntity<Void> removerDisciplina(@PathVariable int codigo) {
        try {
            disciplinaService.removerDisciplina(codigo);
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
