package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "alunos")
@Entity

public class Curso {
    @Id
	private int codigo;
	private String nome;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Aluno> alunos = new ArrayList<>();
//	private List<Disciplina> disciplinas = new ArrayList<>();
	
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

//	public void addDisciplina(Disciplina disciplina) {
//		disciplinas.add(disciplina);
//	}
//
//	public void removeDisciplina(Disciplina disciplina) {
//		disciplinas.remove(disciplina);
//	}

}
