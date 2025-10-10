package br.edu.ibmec.universidade.resource;

import br.edu.ibmec.universidade.dto.TurmaDTO;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.exception.ServiceException.ServiceExceptionEnum;
import br.edu.ibmec.universidade.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/turma", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Turmas", description = "Operações de gestão de turmas")
public class TurmaResource {

    private final TurmaService turmaService;

    @GetMapping("/{codigo}/{ano}/{semestre}")
    @Operation(summary = "Buscar turma por chave composta (código, ano, semestre)")
    public ResponseEntity<TurmaDTO> buscarTurma(@PathVariable int codigo,
                                                @PathVariable int ano,
                                                @PathVariable int semestre) {
        try {
            TurmaDTO dto = turmaService.buscarTurma(codigo, ano, semestre);
            return ResponseEntity.ok(dto);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Cadastrar turma")
    public ResponseEntity<Void> cadastrarTurma(@RequestBody TurmaDTO turmaDTO) {
        try {
            turmaService.cadastrarTurma(turmaDTO);
            URI location = URI.create("/turma/" + turmaDTO.getCodigo() + "/" + turmaDTO.getAno() + "/" + turmaDTO.getSemestre());
            return ResponseEntity.created(location).build();
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
    @Operation(summary = "Alterar turma")
    public ResponseEntity<Void> alterarTurma(@RequestBody TurmaDTO turmaDTO) {
        try {
            turmaService.alterarTurma(turmaDTO); // (corrigido: usar alterar em vez de cadastrar)
            URI location = URI.create("/turma/" + turmaDTO.getCodigo() + "/" + turmaDTO.getAno() + "/" + turmaDTO.getSemestre());
            return ResponseEntity.created(location).build();
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

    @DeleteMapping("/{codigo}/{ano}/{semestre}")
    @Operation(summary = "Remover turma por chave composta")
    public ResponseEntity<Void> removerTurma(@PathVariable int codigo,
                                             @PathVariable int ano,
                                             @PathVariable int semestre) {
        try {
            turmaService.removerTurma(codigo, ano, semestre);
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
