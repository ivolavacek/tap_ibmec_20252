package br.edu.ibmec.universidade.service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import br.edu.ibmec.universidade.dao.EscolaDAO;
import br.edu.ibmec.universidade.dto.AlunoDTO;
import br.edu.ibmec.universidade.entity.Aluno;
import br.edu.ibmec.universidade.entity.Curso;
import br.edu.ibmec.universidade.entity.DataNascimento;
import br.edu.ibmec.universidade.entity.EstadoCivil;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.exception.ServiceException.ServiceExceptionEnum;

public class AlunoService {

    private final EscolaDAO dao;

    public AlunoService() {
        this.dao = EscolaDAO.getInstance();
    }

    // Evite java.util.Date.getYear()/getMonth()/getDay (deprecated). Ideal seria java.time.
    public static DataNascimento getData(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = sdf.parse(data);
            DataNascimento dn = new DataNascimento();
            // Date.getYear retorna ano-1900; getMonth 0-11; getDay é dia da semana (!)
            // Se DataNascimento espera (ano, mes, diaDoMes), calcule corretamente:
            // Use Calendar para evitar erros com getDay (dia da semana).
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(d);
            dn.setAno(cal.get(java.util.Calendar.YEAR));
            dn.setMes(cal.get(java.util.Calendar.MONTH) + 1); // 1-12
            dn.setDia(cal.get(java.util.Calendar.DAY_OF_MONTH)); // 1-31
            return dn;
        } catch (Exception e) {
            System.out.println("Erro conversão da data: " + e.getMessage());
            return null;
        }
    }

    public AlunoDTO buscarAluno(int matricula) throws DaoException {
        Aluno aluno = dao.getAluno(matricula); // deixa DaoException propagar
        return toDTO(aluno);
    }

    public Collection<Aluno> listarAlunos() {
        return dao.getAlunos();
    }

    public void cadastrarAluno(AlunoDTO alunoDTO) throws ServiceException, DaoException {
        validarAlunoDTO(alunoDTO);

        Aluno aluno = new Aluno(
                alunoDTO.getMatricula(),
                alunoDTO.getNome(),
                getData(alunoDTO.getDtNascimento()),
                alunoDTO.isMatriculaAtiva(),
                EstadoCivil.solteiro,
                dao.getCurso(alunoDTO.getCurso()),
                alunoDTO.getTelefones()
        );

        dao.addAluno(aluno);
        Curso curso = dao.getCurso(alunoDTO.getCurso());
        curso.getAlunos().add(aluno);
    }

    public void alterarAluno(AlunoDTO alunoDTO) throws ServiceException, DaoException {
        validarAlunoDTO(alunoDTO);

        Aluno aluno = new Aluno(
                alunoDTO.getMatricula(),
                alunoDTO.getNome(),
                getData(alunoDTO.getDtNascimento()),
                alunoDTO.isMatriculaAtiva(),
                EstadoCivil.solteiro,
                dao.getCurso(alunoDTO.getCurso()),
                alunoDTO.getTelefones()
        );

        dao.updateAluno(aluno);
    }

    public void removerAluno(int matricula) throws DaoException {
        dao.removeAluno(matricula);
    }

    // --------- Helpers (Clean Code) ---------

    private void validarAlunoDTO(AlunoDTO dto) throws ServiceException {
        if (dto.getMatricula() < 1 || dto.getMatricula() > 99) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if (dto.getNome() == null || dto.getNome().isEmpty() || dto.getNome().length() > 20) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }
        // Você pode adicionar validações de data, curso, etc., aqui.
    }

    private AlunoDTO toDTO(Aluno a) {
        AlunoDTO dto = new AlunoDTO();
        dto.setMatricula(a.getMatricula());
        dto.setNome(a.getNome());

        String dataFormatada = formatarData(a.getDataNascimento());
        dto.setDtNascimento(dataFormatada);

        // Usa o helper já existente no DTO para calcular idade (mantendo a sua lógica atual)
        dto.setIdade(AlunoDTO.getIdadeConvertida(dataFormatada));

        dto.setMatriculaAtiva(a.isMatriculaAtiva());

        // Mapear EstadoCivil ≥ EstadoCivilDTO se/quando você tiver esse conversor
        dto.setEstadoCivilDTO(null);

        dto.setTelefones(a.getTelefones());
        dto.setCurso(a.getCurso() != null ? a.getCurso().getCodigo() : 0);

        return dto;
    }

    private String formatarData(DataNascimento dn) {
        if (dn == null) return null;
        // Ajuste se DataNascimento já tiver toString no formato certo;
        // aqui garantimos dd/MM/yyyy:
        int dia = dn.getDia();
        int mes = dn.getMes();
        int ano = dn.getAno();
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}
