package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;

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
	private List<String> telefones;

	@ManyToOne
	private Curso curso;
	
//	private List<Inscricao> inscricoes = new ArrayList<>();

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

//	public void addInscricao(Inscricao inscricao) {
//		inscricoes.add(inscricao);
//	}

//	public void removeInscricao(Inscricao inscricao) {
//		inscricoes.remove(inscricao);
//	}
//
}
