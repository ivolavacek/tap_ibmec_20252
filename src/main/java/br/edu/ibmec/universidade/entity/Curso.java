package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = {"alunos", "disciplinas"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int codigo;
	private String nome;
	private double valorPorDisciplina;

    // ---- Relacionamento com DISCIPLINA (1:N) ----
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value = "curso-disciplina")
	private List<Disciplina> disciplinas = new ArrayList<>();

	// ---- Relacionamento com ALUNO (1:N) ----
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value = "curso-aluno")
	private List<Aluno> alunos = new ArrayList<>();

    public Curso(double valorPorDisciplina) {
        this.valorPorDisciplina = valorPorDisciplina;
    }

    public Curso(int codigo, String nome, double valorPorDisciplina) {
        this.codigo =  codigo;
        this.nome = nome;
        this.valorPorDisciplina = valorPorDisciplina;
    }

	// ----- Métodos auxiliares -----

	public void addAluno(Aluno aluno) {
		alunos.add(aluno);
		aluno.setCurso(this);
	}

	public void removeAluno(Aluno aluno) {
		alunos.remove(aluno);
		aluno.setCurso(null);
	}
}
