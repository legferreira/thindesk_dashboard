package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.Chamado;
import com.pfc.thindesk.repository.ChamadoRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChamadoService {

    public static final String CONCLUIDO = "Concluído";

    @Autowired
    private ChamadoRepository chamadoRepository;

    public Chamado criarChamado(Chamado chamado) {
        chamado.setId(null);
        return chamadoRepository.save(tratarDados(chamado));
    }

    public List<Chamado> listarChamados() {
        return chamadoRepository.findAll();
    }

    public List<Chamado> listarChamados(LocalDate inicio, LocalDate fim, String status, String tipo, String tecnico) {
        return chamadoRepository.findAll().stream()
                .filter(c -> inicio == null || (c.getDataAbertura() != null && !c.getDataAbertura().isBefore(inicio)))
                .filter(c -> fim == null || (c.getDataAbertura() != null && !c.getDataAbertura().isAfter(fim)))
                .filter(c -> vazio(status) || status.equalsIgnoreCase(c.getStatus()))
                .filter(c -> vazio(tipo) || tipo.equalsIgnoreCase(c.getTipo()))
                .filter(c -> vazio(tecnico) || tecnico.equalsIgnoreCase(c.getTecnico()))
                .toList();
    }

    public Chamado buscarPorId(String id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new ChamadoNaoEncontradoException(id));
    }

    public Chamado atualizarChamado(String id, Chamado chamadoAtualizado) {
        Chamado chamado = buscarPorId(id);
        chamado.setDescricao(chamadoAtualizado.getDescricao());
        chamado.setStatus(chamadoAtualizado.getStatus());
        chamado.setTipo(chamadoAtualizado.getTipo());
        chamado.setTecnico(chamadoAtualizado.getTecnico());
        chamado.setUsuario(chamadoAtualizado.getUsuario());
        chamado.setObservacaoTecnica(chamadoAtualizado.getObservacaoTecnica());
        chamado.setDataConclusao(chamadoAtualizado.getDataConclusao());
        return chamadoRepository.save(tratarDados(chamado));
    }

    private Chamado tratarDados(Chamado chamado) {
        chamado.setDescricao(limpar(chamado.getDescricao()));
        chamado.setStatus(limpar(chamado.getStatus()));
        chamado.setTipo(limpar(chamado.getTipo()));
        if (chamado.getDataAbertura() == null) {
            chamado.setDataAbertura(LocalDate.now());
        }
        if (CONCLUIDO.equalsIgnoreCase(chamado.getStatus()) && chamado.getDataConclusao() == null) {
            chamado.setDataConclusao(LocalDate.now());
        }
        if (!CONCLUIDO.equalsIgnoreCase(chamado.getStatus())) {
            chamado.setDataConclusao(null);
        }
        return chamado;
    }

    private String limpar(String valor) {
        return valor == null ? null : valor.trim();
    }

    private boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
