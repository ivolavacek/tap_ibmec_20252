package br.edu.ibmec.universidade.resource;

import br.edu.ibmec.universidade.dto.InscricaoDTO;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.exception.ServiceException.ServiceExceptionEnum;
import br.edu.ibmec.universidade.service.InscricaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/inscricao", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Inscrições", description = "Operações de gestão de inscrições")
public class InscricaoResource {

    private final InscricaoService inscricaoService;

    @GetMapping("/{matricula}/{codigo}/{ano}/{semestre}")
    @Operation(summary = "Buscar inscrição por chave composta (matrícula, código, ano, semestre)")
    public ResponseEntity<InscricaoDTO> buscarInscricao(@PathVariable int matricula,
                                                        @PathVariable int codigo,
                                                        @PathVariable int ano,
                                                        @PathVariable int semestre) {
        try {
            InscricaoDTO dto = inscricaoService.buscarInscricao(matricula, codigo, ano, semestre);
            return ResponseEntity.ok(dto);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Cadastrar inscrição")
    public ResponseEntity<Void> cadastrarInscricao(@RequestBody InscricaoDTO inscricaoDTO) {
        try {
            inscricaoService.cadastrarInscricao(inscricaoDTO);
            URI location = URI.create("/inscricao/" +
                    inscricaoDTO.getAluno() + "/" +
                    inscricaoDTO.getCodigo() + "/" +
                    inscricaoDTO.getAno() + "/" +
                    inscricaoDTO.getSemestre());
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
    @Operation(summary = "Alterar inscrição")
    public ResponseEntity<Void> alterarTurma(@RequestBody InscricaoDTO inscricaoDTO) {
        try {
            inscricaoService.alterarInscricao(inscricaoDTO);
            URI location = URI.create("/inscricao/" +
                    inscricaoDTO.getAluno() + "/" +
                    inscricaoDTO.getCodigo() + "/" +
                    inscricaoDTO.getAno() + "/" +
                    inscricaoDTO.getSemestre());
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

    @DeleteMapping("/{matricula}/{codigo}/{ano}/{semestre}")
    @Operation(summary = "Remover inscrição por chave composta")
    public ResponseEntity<Void> removerInscricao(@PathVariable int matricula,
                                                 @PathVariable int codigo,
                                                 @PathVariable int ano,
                                                 @PathVariable int semestre) {
        try {
            inscricaoService.removerInscricao(matricula, codigo, ano, semestre);
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
