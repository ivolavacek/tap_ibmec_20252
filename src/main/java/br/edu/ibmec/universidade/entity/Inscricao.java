package br.edu.ibmec.universidade.entity;
import lombok.Data;

@Data
public class Inscricao {
	private float avaliacao1;
	private float avaliacao2;
	private float media;
	private int numFaltas;
	private String situacao;

	private Aluno aluno;
	private Turma turma;

	// private Avaliacao avaliacao;

	public Inscricao() {

	}

	public Inscricao(float avaliacao1, float avaliacao2, int numFaltas,
			String situacao, Aluno aluno, Turma turma) {
		this.avaliacao1 = avaliacao1;
		this.avaliacao2 = avaliacao2;
		this.media = (avaliacao1 + avaliacao2) / 2;
		this.numFaltas = numFaltas;
		this.situacao = situacao;
		this.aluno = aluno;
		this.turma = turma;
	}

}
