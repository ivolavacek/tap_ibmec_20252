package br.edu.ibmec.universidade.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Aluno aluno;

    @ManyToOne(optional = false)
    private Turma turma;

    private LocalDate dataInscricao = LocalDate.now();

    // Construtor obrigatório
    public Inscricao(Aluno aluno, Turma turma) {
        this.aluno = aluno;
        this.turma = turma;
    }

    public Inscricao() {} // JPA

    // getters
}

