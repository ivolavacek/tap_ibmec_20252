package br.edu.ibmec.universidade.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
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

    // --- CAMPOS QUE SÓ SERÃO PREENCHIDOS DEPOIS ---
    private Float avaliacao1;
    private Float avaliacao2;
    private Float media;
    private Integer numFaltas;
    private String situacao; // Aprovado, Reprovado etc.

    // Construtor obrigatório
    public Inscricao(Aluno aluno, Turma turma) {
        this.aluno = aluno;
        this.turma = turma;
    }
}
