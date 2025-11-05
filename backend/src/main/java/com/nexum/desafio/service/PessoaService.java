package com.nexum.desafio.service;

import com.nexum.desafio.model.Pessoa;
import com.nexum.desafio.model.Endereco;
import com.nexum.desafio.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository repo;
    private final CepService cepService;

    public PessoaService(PessoaRepository repo, CepService cepService) {
        this.repo = repo;
        this.cepService = cepService;
    }

    public Pessoa salvar(Pessoa p) {
        // ✅ Validação de CPF
        if (!p.isCpfValido()) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        // ✅ Verifica e busca o CEP
        if (p.getEndereco() != null && p.getEndereco().getCep() != null) {
            String cep = p.getEndereco().getCep().replaceAll("\\D", "");
            if (cep.length() == 8) {
                var resp = cepService.consultarCep(cep);

                Endereco e = p.getEndereco();
                if (resp != null && (resp.getErro() == null || !resp.getErro())) {
                    e.setLogradouro(resp.getLogradouro());
                    e.setBairro(resp.getBairro());
                    e.setCidade(resp.getLocalidade());
                    e.setEstado(resp.getUf());
                    e.setCep(cep);
                } else {
                    // ⚠️ Se o CEP for inválido, apenas limpa os campos
                    e.setLogradouro("");
                    e.setBairro("");
                    e.setCidade("");
                    e.setEstado("");
                    e.setCep("");
                }
                p.setEndereco(e);
            }
        }

        return repo.save(p);
    }

    public Pessoa atualizar(Long id, Pessoa p) {
        Optional<Pessoa> opt = repo.findById(id);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Pessoa não encontrada para atualização.");
        }

        Pessoa existente = opt.get();
        existente.setNome(p.getNome());
        existente.setCpf(p.getCpf());
        existente.setEndereco(p.getEndereco());

        // ✅ Revalida CPF e CEP ao atualizar
        return salvar(existente);
    }

    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Pessoa não encontrada para exclusão.");
        }
        repo.deleteById(id);
    }

    public List<Pessoa> listar(String q) {
        if (q != null && !q.isBlank()) {
            String like = q.trim();
            // Novo: O termo de busca é checado contra nome, cidade, CEP e ESTADO.
            return repo.findByNomeContainingIgnoreCaseOrEndereco_CidadeContainingIgnoreCaseOrEndereco_CepContainingIgnoreCaseOrEndereco_EstadoContainingIgnoreCase(
                    like, like, like, like);
        }
        return repo.findAll();
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return repo.findById(id);
    }
}
