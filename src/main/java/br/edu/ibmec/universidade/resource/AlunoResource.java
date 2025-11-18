package br.edu.ibmec.universidade.resource;

import br.edu.ibmec.universidade.dto.AlunoDTO;
import br.edu.ibmec.universidade.entity.Aluno;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.exception.ServiceException.ServiceExceptionEnum;
import br.edu.ibmec.universidade.service.AlunoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/alunos", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "Operações de gestão de alunos")
public class AlunoResource {

    private final AlunoService alunoService;

    @GetMapping("/{matricula}")
    @Operation(summary = "Buscar aluno por matrícula")
    public ResponseEntity<AlunoDTO> buscarAluno(@PathVariable int matricula) {
        try {
            return ResponseEntity.ok(alunoService.buscarAluno(matricula));
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{matricula}/mensalidade")
    @Operation(summary = "Calcula a mensalidade do aluno")
    public ResponseEntity<Double> calcularMensalidade(@PathVariable int matricula) {
        try {
            double valor = alunoService.calcularMensalidade(matricula);
            return ResponseEntity.ok(valor);
        } catch (DaoException e) {
            return ResponseEntity.badRequest().header("Motivo", e.getMessage()).body(null);
        }
    }

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Cadastrar aluno")
    public ResponseEntity<Void> cadastrarAluno(@RequestBody AlunoDTO alunoDTO) {
        try {
            alunoService.cadastrarAluno(alunoDTO);
            return ResponseEntity.created(URI.create("/alunos/" + alunoDTO.getMatricula())).build();
        } catch (ServiceException | DaoException e) {
            return ResponseEntity.badRequest().header("Motivo", e.getMessage()).build();
        }
    }

    @PutMapping(consumes = "application/json")
    @Operation(summary = "Alterar aluno")
    public ResponseEntity<Void> alterarAluno(@RequestBody AlunoDTO alunoDTO) {
        try {
            alunoService.alterarAluno(alunoDTO);
            return ResponseEntity.ok().build();
        } catch (ServiceException | DaoException e) {
            return ResponseEntity.badRequest().header("Motivo", e.getMessage()).build();
        }
    }

    @DeleteMapping("/{matricula}")
    @Operation(summary = "Remover aluno")
    public ResponseEntity<Void> removerAluno(@PathVariable int matricula) {
        try {
            alunoService.removerAluno(matricula);
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar nomes")
    public ResponseEntity<List<String>> listarAlunos() {
        List<String> nomes = alunoService.listarAlunos()
                .stream()
                .map(Aluno::getNome)
                .collect(Collectors.toList());

        return ResponseEntity.ok(nomes);
    }
}
