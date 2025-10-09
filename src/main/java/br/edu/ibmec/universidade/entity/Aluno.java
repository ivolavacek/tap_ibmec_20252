package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import lombok.Data;

@Data
public class Aluno {
	private int matricula;
	private String nome;
	private Date dataNascimento;
	private int idade;
	private boolean matriculaAtiva;
	private EstadoCivil estadoCivil;
	private Vector<String> telefones;

	
	private Curso curso;
	
	private List<Inscricao> inscricoes = new ArrayList<Inscricao>();

	public Aluno() {

	}

	public Aluno(int matricula, String nome, Date dataNascimento,
			boolean matriculaAtiva, EstadoCivil estadoCivil, Curso curso, 
			Vector<String> telefones) {
		this.matricula = matricula;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.matriculaAtiva = matriculaAtiva;
		this.estadoCivil = estadoCivil;
		this.curso = curso;
		
		this.idade = 0;
		this.telefones = telefones;
	}

	public void addInscricao(Inscricao inscricao) {
		inscricoes.add(inscricao);
	}

	public void removeInscricao(Inscricao inscricao) {
		inscricoes.remove(inscricao);
	}

}
