package br.edu.ibmec.universidade.service.strategy;

import br.edu.ibmec.universidade.entity.Inscricao;

public interface ApprovalStrategy {
    /**
     * Recebe uma inscrição e devolve a situação ("Aprovado" / "Reprovado" / outro)
     */
    String calcularSituacao(Inscricao inscricao);
}
