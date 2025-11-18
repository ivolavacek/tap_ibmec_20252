package br.edu.ibmec.universidade.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import br.edu.ibmec.universidade.entity.Aluno;
import br.edu.ibmec.universidade.entity.Curso;
import br.edu.ibmec.universidade.entity.DataNascimento;
import br.edu.ibmec.universidade.entity.EstadoCivil;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlunoDTO {

    private int matricula;
    private String nome;
    private String dtNascimento; // dd/MM/yyyy
    private int idade;
    private boolean matriculaAtiva;

    private EstadoCivilDTO estadoCivil;

    private List<String> telefones;

    private Integer curso; // código do curso

    // Construtor que converte automaticamente a entidade Aluno para DTO
    public AlunoDTO(br.edu.ibmec.universidade.entity.Aluno aluno) {
        this.matricula = aluno.getMatricula();
        this.nome = aluno.getNome();

        if (aluno.getDataNascimento() != null) {
            // usa toLocalDate() / toString() já implementado no DataNascimento
            this.dtNascimento = aluno.getDataNascimento().toString();
            // idade pode ser calculada a partir da data se quiser (omitido aqui)
            this.idade = aluno.getIdade();
        }

        this.matriculaAtiva = aluno.isMatriculaAtiva();

        if (aluno.getEstadoCivil() != null) {
            // assumes enums have same names (case): EstadoCivilDTO.valueOf(...)
            this.estadoCivil = EstadoCivilDTO.valueOf(aluno.getEstadoCivil().name());
        }

        if (aluno.getTelefones() != null) {
            this.telefones = aluno.getTelefones().stream().collect(Collectors.toList());
        }

        if (aluno.getCurso() != null) {
            this.curso = aluno.getCurso().getCodigo();
        }
    }

    /**
     * Converte este DTO para entidade Aluno.
     *
     * Observação importante:
     * - Este método **não** consulta repositórios. Ele apenas monta uma entidade
     *   que pode ser salva. Se você precisa que o Curso seja uma entidade gerenciada
     *   pelo JPA (com todos os seus campos), faça a busca do Curso no Service:
     *     Curso curso = cursoRepository.findById(this.curso).orElse(...);
     *
     * - Data esperada em dtNascimento: "dd/MM/yyyy". Se for outro formato, ajuste.
     */
    public Aluno toEntity() {
        Aluno aluno = new Aluno();

        aluno.setMatricula(this.matricula);
        aluno.setNome(this.nome);
        aluno.setMatriculaAtiva(this.matriculaAtiva);

        // DataNascimento
        if (this.dtNascimento != null && !this.dtNascimento.isBlank()) {
            try {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate ld = LocalDate.parse(this.dtNascimento, fmt);
                DataNascimento dn = new DataNascimento(ld.getDayOfMonth(), ld.getMonthValue(), ld.getYear());
                aluno.setDataNascimento(dn);
            } catch (Exception e) {
                // se parsing falhar, deixar null — o service pode validar e lançar ServiceException
                aluno.setDataNascimento(null);
            }
        }

        // Estado civil
        if (this.estadoCivil != null) {
            try {
                EstadoCivil ec = EstadoCivil.valueOf(this.estadoCivil.name());
                aluno.setEstadoCivil(ec);
            } catch (Exception e) {
                aluno.setEstadoCivil(null);
            }
        }

        // Telefones
        if (this.telefones != null) {
            aluno.setTelefones(this.telefones.stream().collect(Collectors.toList()));
        }

        // Curso: aqui apenas criamos uma instância com o código.
        // Preferível: no service, buscar o Curso real via CursoRepository e setar no Aluno.
        if (this.curso != null) {
            Curso c = new Curso();
            c.setCodigo(this.curso);
            aluno.setCurso(c);
        } else {
            aluno.setCurso(null);
        }

        // bolsista padrão false — o DTO poderia ter um campo boolean bolsista se quiser
        // aluno.setBolsista(false);

        return aluno;
    }
}
