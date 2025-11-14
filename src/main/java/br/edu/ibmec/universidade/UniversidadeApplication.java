package br.edu.ibmec.universidade;

import br.edu.ibmec.universidade.entity.*;
import br.edu.ibmec.universidade.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
			Curso c1 = new Curso(1, "Ciência da Computação",550.0);
			Curso c2 = new Curso(2, "Engenharia de Software",480.0);
			Curso c3 = new Curso(3, "Sistemas de Informação",515.0);
			Curso c4 = new Curso(4, "Engenharia Elétrica",475.0);
			Curso c5 = new Curso(5, "Matemática Aplicada",650.0);

			cursoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));

			// -----------------------------
			// PROFESSORES
			// -----------------------------
			Professor p1 = new Professor(null, "Carlos Silva", null);
			Professor p2 = new Professor(null, "Ana Souza", null);
			Professor p3 = new Professor(null, "Ricardo Almeida", null);
			Professor p4 = new Professor(null, "Marina Castro", null);
			Professor p5 = new Professor(null, "Fernanda Rocha", null);
			Professor p6 = new Professor(null, "Eduardo Rezende", null);

			professorRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));

			// -----------------------------
			// DISCIPLINAS
			// -----------------------------
			Disciplina d1 = new Disciplina(null, 101, "Algoritmos", c1, null);
			Disciplina d2 = new Disciplina(null, 102, "Banco de Dados", c2, null);
			Disciplina d3 = new Disciplina(null, 103, "Estruturas de Dados", c1, null);
			Disciplina d4 = new Disciplina(null, 104, "Redes de Computadores", c3, null);
			Disciplina d5 = new Disciplina(null, 105, "Probabilidade e Estatística", c5, null);
			Disciplina d6 = new Disciplina(null, 106, "Cálculo 1", c5, null);
			Disciplina d7 = new Disciplina(null, 107, "Engenharia de Requisitos", c2, null);
			Disciplina d8 = new Disciplina(null, 108, "Circuitos Elétricos", c4, null);

			disciplinaRepository.saveAll(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8));

			// -----------------------------
			// TURMAS
			// -----------------------------
			Turma t1 = new Turma(null, 1001, 2024, 1, d1, p1, null);
			Turma t2 = new Turma(null, 1002, 2024, 2, d2, p2, null);
			Turma t3 = new Turma(null, 1003, 2024, 1, d3, p3, null);
			Turma t4 = new Turma(null, 1004, 2024, 2, d4, p4, null);
			Turma t5 = new Turma(null, 1005, 2024, 1, d5, p5, null);
			Turma t6 = new Turma(null, 1006, 2024, 2, d6, p6, null);
			Turma t7 = new Turma(null, 1007, 2024, 1, d7, p2, null);
			Turma t8 = new Turma(null, 1008, 2024, 1, d8, p4, null);

			turmaRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8));

			// -----------------------------
			// ALUNOS
			// -----------------------------
			DataNascimento dn = new DataNascimento(10, 10, 2000);

			Aluno a1 = new Aluno(1, "João", dn, true, EstadoCivil.solteiro, c1, Arrays.asList("99999-1111"));
			Aluno a2 = new Aluno(2, "Maria", dn, true, EstadoCivil.casado, c2, Arrays.asList("99999-2222"));
			Aluno a3 = new Aluno(3, "Pedro", dn, true, EstadoCivil.solteiro, c1, Arrays.asList("99999-3333"));
			Aluno a4 = new Aluno(4, "Julia", dn, true, EstadoCivil.solteiro, c3, Arrays.asList("99999-4444"));
			Aluno a5 = new Aluno(5, "Miguel", dn, true, EstadoCivil.solteiro, c4, Arrays.asList("99999-5555"));
			Aluno a6 = new Aluno(6, "Sofia", dn, true, EstadoCivil.casado, c5, Arrays.asList("99999-6666"));
			Aluno a7 = new Aluno(7, "Paulo", dn, true, EstadoCivil.solteiro, c2, Arrays.asList("99999-7777"));
			Aluno a8 = new Aluno(8, "Larissa", dn, true, EstadoCivil.solteiro, c3, Arrays.asList("99999-8888"));

			alunoRepository.saveAll(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8));

            // -----------------------------
            // INSCRIÇÕES
            // -----------------------------
            Inscricao i1 = new Inscricao(null, 8.5f, 7.5f, 8.0f, 2, "Aprovado", a1, t1);
            Inscricao i2 = new Inscricao(null, 6.0f, 5.5f, 5.75f, 5, "Reprovado", a2, t2);
            Inscricao i3 = new Inscricao(null, 9.0f, 8.0f, 8.5f, 1, "Aprovado", a3, t3);
            Inscricao i4 = new Inscricao(null, 4.0f, 6.0f, 5.0f, 6, "Reprovado", a4, t4);
            Inscricao i5 = new Inscricao(null, 7.0f, 7.5f, 7.25f, 3, "Aprovado", a5, t5);
            Inscricao i6 = new Inscricao(null, 5.5f, 4.5f, 5.0f, 4, "Reprovado", a6, t6);
            Inscricao i7 = new Inscricao(null, 8.0f, 9.0f, 8.5f, 2, "Aprovado", a7, t7);
            Inscricao i8 = new Inscricao(null, 9.5f, 9.0f, 9.25f, 0, "Aprovado", a8, t8);

            // Adicionando as inscrições aos alunos
            a1.addInscricao(i1); // Associar inscrição com aluno a1
            a2.addInscricao(i2); // Associar inscrição com aluno a2
            a3.addInscricao(i3); // Associar inscrição com aluno a3
            a4.addInscricao(i4); // Associar inscrição com aluno a4
            a5.addInscricao(i5); // Associar inscrição com aluno a5
            a6.addInscricao(i6); // Associar inscrição com aluno a6
            a7.addInscricao(i7); // Associar inscrição com aluno a7
            a8.addInscricao(i8); // Associar inscrição com aluno a8

            // Salvar inscrições
            inscricaoRepository.saveAll(Arrays.asList(i1, i2, i3, i4, i5, i6, i7, i8));

			System.out.println("✅ Banco H2 populado com dados iniciais");
            System.out.println("Mensalidade João: " + a1.calcularMensalidade()); // 500 * 2 = 1000
            System.out.println("Mensalidade Maria: " + a2.calcularMensalidade()); // 450 * 1 = 450

        };
	}
}
