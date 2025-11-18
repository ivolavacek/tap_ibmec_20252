package br.edu.ibmec.universidade;

import br.edu.ibmec.universidade.entity.*;
import br.edu.ibmec.universidade.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class UniversidadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversidadeApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(
			CursoRepository cursoRepository,
			AlunoRepository alunoRepository,
			ProfessorRepository professorRepository,
			DisciplinaRepository disciplinaRepository,
			TurmaRepository turmaRepository,
			InscricaoRepository inscricaoRepository
	) {
		return args -> {

			// -----------------------------
			// CURSOS
			// -----------------------------
			Curso c1 = new Curso(1, "Ciência da Computação", 550.0);
			Curso c2 = new Curso(2, "Engenharia de Software", 480.0);
			Curso c3 = new Curso(3, "Sistemas de Informação", 515.0);
			Curso c4 = new Curso(4, "Engenharia Elétrica", 475.0);
			Curso c5 = new Curso(5, "Matemática Aplicada", 650.0);

			cursoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));

			// -----------------------------
			// PROFESSORES
			// -----------------------------
			Professor p1 = new Professor(null, "Carlos Silva", new ArrayList<>());
			Professor p2 = new Professor(null, "Ana Souza", new ArrayList<>());
			Professor p3 = new Professor(null, "Ricardo Almeida", new ArrayList<>());
			Professor p4 = new Professor(null, "Marina Castro", new ArrayList<>());
			Professor p5 = new Professor(null, "Fernanda Rocha", new ArrayList<>());
			Professor p6 = new Professor(null, "Eduardo Rezende", new ArrayList<>());

			professorRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));


			// --------------------------------
			// DISCIPLINAS (SEM turmas)
			// --------------------------------
			Disciplina d1 = new Disciplina(null, "Algoritmos", 101, c1, new ArrayList<>());
			Disciplina d2 = new Disciplina(null, "Banco de Dados", 102, c2, new ArrayList<>());
			Disciplina d3 = new Disciplina(null, "Estruturas de Dados", 103, c1, new ArrayList<>());
			Disciplina d4 = new Disciplina(null, "Redes de Computadores", 104, c3, new ArrayList<>());
			Disciplina d5 = new Disciplina(null, "Probabilidade e Estatística", 105, c5, new ArrayList<>());
			Disciplina d6 = new Disciplina(null, "Cálculo 1", 106, c5, new ArrayList<>());
			Disciplina d7 = new Disciplina(null, "Engenharia de Requisitos", 107, c2, new ArrayList<>());
			Disciplina d8 = new Disciplina(null, "Circuitos Elétricos", 108, c4, new ArrayList<>());

			disciplinaRepository.saveAll(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8));

			// -----------------------------
			// TURMAS
			// -----------------------------
			Turma t1 = new Turma(null, 2024, 1, 1001, d1, p1, new ArrayList<>());
			Turma t2 = new Turma(null, 2024, 2, 1002, d2, p2, new ArrayList<>());
			Turma t3 = new Turma(null, 2024, 1, 1003, d3, p3, new ArrayList<>());
			Turma t4 = new Turma(null, 2024, 2, 1004, d4, p4, new ArrayList<>());
			Turma t5 = new Turma(null, 2024, 1, 1005, d5, p5, new ArrayList<>());
			Turma t6 = new Turma(null, 2024, 2, 1006, d6, p6, new ArrayList<>());
			Turma t7 = new Turma(null, 2024, 1, 1007, d7, p2, new ArrayList<>());
			Turma t8 = new Turma(null, 2024, 1, 1008, d8, p4, new ArrayList<>());

			turmaRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8));

			// adiciona as turmas aos professores (lado bidirecional)
			p1.getTurmas().add(t1);
			p2.getTurmas().addAll(Arrays.asList(t2, t7));
			p3.getTurmas().add(t3);
			p4.getTurmas().addAll(Arrays.asList(t4, t8));
			p5.getTurmas().add(t5);
			p6.getTurmas().add(t6);

			professorRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));

			// -----------------------------
			// ALUNOS
			// -----------------------------
			DataNascimento dn = new DataNascimento(10, 10, 2000);

			Aluno a1 = new Aluno(1, "João", dn, true, EstadoCivil.SOLTEIRO, c1, Arrays.asList("99999-1111"));
			Aluno a2 = new Aluno(2, "Maria", dn, true, EstadoCivil.CASADO, c2, Arrays.asList("99999-2222"));
			Aluno a3 = new Aluno(3, "Pedro", dn, true, EstadoCivil.SOLTEIRO, c1, Arrays.asList("99999-3333"));
			Aluno a4 = new Aluno(4, "Julia", dn, true, EstadoCivil.SOLTEIRO, c3, Arrays.asList("99999-4444"));
			Aluno a5 = new Aluno(5, "Miguel", dn, true, EstadoCivil.SOLTEIRO, c4, Arrays.asList("99999-5555"));
			Aluno a6 = new Aluno(6, "Sofia", dn, true, EstadoCivil.CASADO, c5, Arrays.asList("99999-6666"));
			Aluno a7 = new Aluno(7, "Paulo", dn, true, EstadoCivil.SOLTEIRO, c2, Arrays.asList("99999-7777"));
			Aluno a8 = new Aluno(8, "Larissa", dn, true, EstadoCivil.SOLTEIRO, c3, Arrays.asList("99999-8888"));

			alunoRepository.saveAll(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8));

			// -----------------------------
			// INSCRIÇÕES
			// -----------------------------
			Inscricao i1 = new Inscricao(a1, t1);
			Inscricao i2 = new Inscricao(a2, t2);
			Inscricao i3 = new Inscricao(a3, t3);
			Inscricao i4 = new Inscricao(a4, t4);
			Inscricao i5 = new Inscricao(a1, t2); // João em outra turma
			Inscricao i6 = new Inscricao(a5, t7);

			inscricaoRepository.saveAll(Arrays.asList(i1, i2, i3, i4, i5, i6));

			// adiciona nas turmas (LADO DONO não é aqui, mas mantém a consistência)
			t1.getInscricoes().add(i1);
			t2.getInscricoes().add(i2);
			t3.getInscricoes().add(i3);
			t4.getInscricoes().add(i4);
			t2.getInscricoes().add(i5);
			t7.getInscricoes().add(i6);

			turmaRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t7));

			System.out.println("✅ Banco inicial populado com sucesso!");
		};
	}
}
