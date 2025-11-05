package com.nexum.desafio;

import com.nexum.desafio.model.Pessoa;
import com.nexum.desafio.model.Endereco;
import com.nexum.desafio.repository.PessoaRepository;
import com.nexum.desafio.service.CepService;
import com.nexum.desafio.service.PessoaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class PessoaServiceTest {

    @Test
    void cpfValido() {
        Pessoa p = new Pessoa();
        p.setCpf("52998224725");
        assertTrue(p.isCpfValido(), "O CPF informado deveria ser válido");
    }

    @Test
    void salvarPreencheEndereco() {
        // Mock dos repositórios e serviços
        PessoaRepository repo = Mockito.mock(PessoaRepository.class);
        CepService cep = Mockito.mock(CepService.class);

        // Mock da resposta do serviço de CEP (usando setters públicos)
        CepService.EnderecoResponse resp = new CepService.EnderecoResponse();
        resp.setCep("17055270");
        resp.setLogradouro("Rua Teste");
        resp.setLocalidade("Bauru");
        resp.setUf("SP");

        // Quando consultar o CEP, retorna o mock acima
        Mockito.when(cep.consultarCep("17000000")).thenReturn(resp);
        Mockito.when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // Cria o serviço e executa o método de salvar
        PessoaService service = new PessoaService(repo, cep);
        Pessoa p = new Pessoa();
        p.setCpf("52998224725");
        p.setNome("Pedro");

        Endereco e = new Endereco();
        e.setCep("17000000");
        e.setNumero("100");
        p.setEndereco(e);

        Pessoa salvo = service.salvar(p);

        // Validações
        assertNotNull(salvo.getEndereco(), "O endereço não deve ser nulo após salvar");
        assertEquals("Rua Teste", salvo.getEndereco().getLogradouro(), "O logradouro não foi preenchido corretamente");
        assertEquals("Bauru", salvo.getEndereco().getCidade(), "A cidade não foi preenchida corretamente");
        assertEquals("SP", salvo.getEndereco().getEstado(), "O estado não foi preenchido corretamente");
    }
}
