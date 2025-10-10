package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Curso {
	private int codigo;
	private String nome;

	private List<Aluno> alunos = new ArrayList<>();
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	public Curso() {
		
	}
	
	public Curso(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public void addAluno(Aluno aluno) {
		alunos.add(aluno);
	}

	public void removeAluno(Aluno aluno) {
		alunos.remove(aluno);
	}

	public void addDisciplina(Disciplina disciplina) {
		disciplinas.add(disciplina);
	}

	public void removeDisciplina(Disciplina disciplina) {
		disciplinas.remove(disciplina);
	}

}
