package com.pfc;

import com.pfc.thindesk.entity.Chamado;
import com.pfc.thindesk.repository.ChamadoRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChamadoInicialConfig implements CommandLineRunner {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (chamadoRepository.count() > 0) {
            return;
        }
        LocalDate hoje = LocalDate.now();
        salvar("Chamado de Teste", "Aberto", "Manutenção", "João", "Alberto", null, hoje.minusDays(1), null);
        salvar("Impressora não imprime", "Concluído", "Manutenção", "Maria", "Lucas", "Cabo USB substituído", hoje.minusDays(10), hoje.minusDays(9));
        salvar("Sem acesso à internet", "Concluído", "Rede", "Pedro", "Fernanda", "Roteador reiniciado", hoje.minusDays(9), hoje.minusDays(9));
        salvar("Instalação do pacote Office", "Concluído", "Instalação", "João", "Ricardo", "Licença ativada", hoje.minusDays(8), hoje.minusDays(7));
        salvar("Troca de senha do e-mail", "Concluído", "Acesso", "Maria", "Beatriz", "Senha redefinida", hoje.minusDays(7), hoje.minusDays(7));
        salvar("Computador lento", "Concluído", "Manutenção", "João", "Marcos", "Limpeza e troca de memória", hoje.minusDays(6), hoje.minusDays(3));
        salvar("Projetor sem sinal", "Cancelado", "Manutenção", "Pedro", "Renato", "Equipamento enviado para garantia", hoje.minusDays(5), null);
        salvar("Wi-Fi caindo na sala de reuniões", "Em andamento", "Rede", "Pedro", "Juliana", null, hoje.minusDays(3), null);
        salvar("Instalação de novo monitor", "Concluído", "Instalação", "Maria", "Camila", "Monitor configurado", hoje.minusDays(2), hoje.minusDays(1));
        salvar("Teclado com teclas falhando", "Aberto", "Manutenção", "João", "Diego", null, hoje.minusDays(1), null);
        salvar("Liberação de acesso ao sistema", "Em andamento", "Acesso", "Maria", "Patrícia", null, hoje, null);
    }

    private void salvar(String descricao, String status, String tipo, String tecnico, String usuario,
            String observacaoTecnica, LocalDate dataAbertura, LocalDate dataConclusao) {
        Chamado chamado = new Chamado();
        chamado.setDescricao(descricao);
        chamado.setStatus(status);
        chamado.setTipo(tipo);
        chamado.setTecnico(tecnico);
        chamado.setUsuario(usuario);
        chamado.setObservacaoTecnica(observacaoTecnica);
        chamado.setDataAbertura(dataAbertura);
        chamado.setDataConclusao(dataConclusao);
        chamadoRepository.save(chamado);
    }
}
