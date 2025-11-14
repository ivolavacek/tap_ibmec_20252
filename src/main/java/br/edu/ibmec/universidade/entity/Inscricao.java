package br.edu.ibmec.universidade.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float avaliacao1;
    private float avaliacao2;
    private float media;
    private int numFaltas;
    private String situacao;

    @ManyToOne
    @JsonBackReference(value = "aluno-inscricoes")
    private Aluno aluno;

    @ManyToOne
    @JsonBackReference(value = "turma-inscricoes")
    private Turma turma;

}
