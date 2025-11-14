package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;

import br.edu.ibmec.universidade.service.strategy.MensalidadeStrategy;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "curso")
@Entity

public class Aluno {
    @Id
	private int matricula;

	private String nome;
    @Embedded
	private DataNascimento dataNascimento;
	private int idade;
	private boolean matriculaAtiva;
    @Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;

	@ElementCollection
	private List<String> telefones = new ArrayList<>();

	@ManyToOne
	private Curso curso;

	@OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value = "aluno-inscricoes")
	private List<Inscricao> inscricoes = new ArrayList<>();



    // Método para calcular a mensalidade
    public double calcularMensalidade() {
        if (curso == null) {
            throw new IllegalStateException("Curso não definido para o aluno");
        }

        // Valor da mensalidade por disciplina baseado no curso
        double valorPorDisciplina = curso.getValorPorDisciplina();
        double totalMensalidade = 0;

        // Soma o valor das mensalidades de todas as disciplinas nas quais o aluno está matriculado
        for (Inscricao inscricao : inscricoes) {
            totalMensalidade += valorPorDisciplina;
        }

        return totalMensalidade;  // Retorna o total
    }


	public Aluno() {
    }

	public Aluno(int matricula, String nome, DataNascimento dataNascimento,
			boolean matriculaAtiva, EstadoCivil estadoCivil, Curso curso, 
			List<String> telefones) {
		this.matricula = matricula;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.matriculaAtiva = matriculaAtiva;
		this.estadoCivil = estadoCivil;
		this.curso = curso;
		
		this.idade = 0;
		this.telefones = telefones;
	}

    public List<Disciplina> getDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        for (Inscricao inscricao : inscricoes) {
            disciplinas.add(inscricao.getTurma().getDisciplina());
        }
        return disciplinas;
    }
	public void addInscricao(Inscricao inscricao) {
		inscricoes.add(inscricao);
		inscricao.setAluno(this);
	}

	public void removeInscricao(Inscricao inscricao) {
		inscricoes.remove(inscricao);
		inscricao.setAluno(null);
	}
}
