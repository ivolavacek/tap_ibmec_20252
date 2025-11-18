package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;

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

	// novo: indica se o aluno é bolsista (recebe desconto)
	private boolean bolsista = false;

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
