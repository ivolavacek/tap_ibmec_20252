package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int codigo;
    private int ano;
    private int semestre;

    @ManyToOne
    @JsonBackReference(value = "disciplina-turma")
    private Disciplina disciplina;

    @ManyToOne
    @JsonManagedReference(value = "professor-turma")
    private Professor professor;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "turma-inscricoes")
    private List<Inscricao> inscricoes = new ArrayList<>();

    public void addInscricao(Inscricao inscricao) {
        inscricoes.add(inscricao);
        inscricao.setTurma(this);
    }

    public void removeInscricao(Inscricao inscricao) {
        inscricoes.remove(inscricao);
        inscricao.setTurma(null);
    }
}
