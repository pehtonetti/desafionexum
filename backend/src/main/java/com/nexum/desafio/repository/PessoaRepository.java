package com.nexum.desafio.repository;

import com.nexum.desafio.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    // Busca genérica: nome, cidade, CEP ou ESTADO (sigla)
    List<Pessoa> findByNomeContainingIgnoreCaseOrEndereco_CidadeContainingIgnoreCaseOrEndereco_CepContainingIgnoreCaseOrEndereco_EstadoContainingIgnoreCase(
            String nome, String cidade, String cep, String estado
    );
}