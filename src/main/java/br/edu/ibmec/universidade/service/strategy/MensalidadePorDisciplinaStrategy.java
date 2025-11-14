package br.edu.ibmec.universidade.service.strategy;

import br.edu.ibmec.universidade.entity.Aluno;
import br.edu.ibmec.universidade.entity.Inscricao;

public class MensalidadePorDisciplinaStrategy implements MensalidadeStrategy {

    @Override
    public double calcularMensalidade(Aluno aluno) {
        // Calcular mensalidade com base no número de disciplinas que o aluno está inscrito
        double totalMensalidade = 0;
        for (Inscricao inscricao : aluno.getInscricoes()) {
            double valorPorDisciplina = inscricao.getTurma().getDisciplina().getCurso().getValorPorDisciplina();
            totalMensalidade += valorPorDisciplina;
        }
        return totalMensalidade;
    }
}
