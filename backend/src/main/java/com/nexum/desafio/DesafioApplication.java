package com.nexum.desafio;

import com.nexum.desafio.model.Endereco;
import com.nexum.desafio.model.Pessoa;
import com.nexum.desafio.repository.PessoaRepository;
import com.nexum.desafio.service.PessoaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DesafioApplication {

    /**
     * Ponto de entrada da aplicação Spring Boot.
     */
    public static void main(String[] args) {
        SpringApplication.run(DesafioApplication.class, args);
    }

    /**
     * Define um Bean do tipo CommandLineRunner para executar o Data Seeding.
     *
     * @param repo Repositório de Pessoas.
     * @param service Serviço de Pessoas (responsável por salvar e validar).
     * @return Uma instância de CommandLineRunner.
     */
    @Bean
    public CommandLineRunner initDatabase(PessoaRepository repo, PessoaService service) {
        return args -> {
            // Verifica se a tabela está vazia.
            if (repo.count() == 0) {
                System.out.println("--- 💾 POPULANDO BANCO DE DADOS COM DADOS FALSOS (10 REGISTROS VÁLIDOS) ---");

                List<Pessoa> pessoasDeTeste = criarPessoasDeTeste();

                for (Pessoa pessoa : pessoasDeTeste) {
                    try {
                        // Chama o serviço, que executa a validação de CPF e a busca de CEP (que agora não é crítica).
                        service.salvar(pessoa);
                        System.out.println("   ✅ Pessoa salva: " + pessoa.getNome());
                    } catch (Exception e) {
                        // Captura e exibe o erro em caso de falha (ajuda a diagnosticar se o problema for outro).
                        System.err.println("   ❌ ERRO ao salvar pessoa de teste: " + pessoa.getNome() + " - " + e.getMessage());
                    }
                }
                System.out.println("--- ✅ POPULADO. " + repo.count() + " registros no total. ---");
            } else {
                System.out.println("--- ℹ️ Banco de dados já possui " + repo.count() + " registros. Seeding ignorado. ---");
            }
        };
    }

    /**
     * Constrói e retorna uma lista de 10 objetos Pessoa com todos os campos de Endereço preenchidos localmente.
     * Isto evita falhas de validação durante o Data Seeding causadas por timeouts ou erros do ViaCEP.
     *
     * @return Lista de objetos Pessoa.
     */
    private List<Pessoa> criarPessoasDeTeste() {

        // 1. Ana Silva - São Paulo
        Pessoa p1 = criarPessoaCompleto("Ana Silva (TESTE)", "58332170046", "ana.silva@teste.com", "11987654321",
                "01001000", "Praça da Sé", "Sé", "São Paulo", "SP", "50");

        // 2. Bruno Costa - Rio de Janeiro
        Pessoa p2 = criarPessoaCompleto("Bruno Costa (TESTE)", "76077748083", "bruno.costa@teste.com", "21998765432",
                "20040001", "Av. Rio Branco", "Centro", "Rio de Janeiro", "RJ", "100");

        // 3. Carla Rocha - Belo Horizonte
        Pessoa p3 = criarPessoaCompleto("Carla Rocha (TESTE)", "87062254054", "carla.rocha@teste.com", "31987651234",
                "30130000", "Av. Afonso Pena", "Centro", "Belo Horizonte", "MG", "500");

        // 4. Davi Santos - Curitiba
        Pessoa p4 = criarPessoaCompleto("Davi Santos (TESTE)", "46470472028", "davi.santos@teste.com", "41987659876",
                "80020320", "Rua XV de Novembro", "Centro", "Curitiba", "PR", "123");

        // 5. Eliana Melo - Salvador
        Pessoa p5 = criarPessoaCompleto("Eliana Melo (TESTE)", "99645229046", "eliana.melo@teste.com", "71987650000",
                "40010000", "Rua da Bahia", "Comércio", "Salvador", "BA", "10");

        // 6. Fábio Gomes - Porto Alegre
        Pessoa p6 = criarPessoaCompleto("Fábio Gomes (TESTE)", "52857440058", "fabio.gomes@teste.com", "51987651111",
                "90010000", "Av. Mauá", "Centro Histórico", "Porto Alegre", "RS", "20");

        // 7. Gabriela Neta - Fortaleza
        Pessoa p7 = criarPessoaCompleto("Gabriela Neta (TESTE)", "21040523091", "gabriela.neta@teste.com", "85987652222",
                "60010000", "Av. Beira Mar", "Meireles", "Fortaleza", "CE", "30");

        // 8. Henrique Lima - São Paulo
        Pessoa p8 = criarPessoaCompleto("Henrique Lima (TESTE)", "44304899047", "henrique.lima@teste.com", "11987653333",
                "01001000", "Praça da Sé", "Sé", "São Paulo", "SP", "40");

        // 9. Íris Ferreira - Rio de Janeiro
        Pessoa p9 = criarPessoaCompleto("Íris Ferreira (TESTE)", "25046277028", "iris.ferreira@teste.com", "21998764444",
                "20040001", "Av. Rio Branco", "Centro", "Rio de Janeiro", "RJ", "55");

        // 10. João Paulo - Belo Horizonte
        Pessoa p10 = criarPessoaCompleto("João Paulo (TESTE)", "99516641049", "joao.paulo@teste.com", "31987655555",
                "30130000", "Av. Afonso Pena", "Centro", "Belo Horizonte", "MG", "999");


        return List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
    }

    /**
     * Método auxiliar que cria e preenche todos os campos de Pessoa e Endereco.
     */
    private Pessoa criarPessoaCompleto(String nome, String cpf, String email, String telefone,
                                       String cep, String logradouro, String bairro, String cidade, String estado, String numero) {
        Pessoa p = new Pessoa();
        // Atribuição das propriedades de Pessoa.
        p.setNome(nome);
        p.setCpf(cpf);
        p.setTelefone(telefone);
        p.setEmail(email);

        // Atribuição das propriedades do Endereco, garantindo que todos os campos @NotBlank estejam preenchidos.
        Endereco e = new Endereco();
        e.setCep(cep);
        e.setLogradouro(logradouro);
        e.setBairro(bairro);
        e.setCidade(cidade);
        e.setEstado(estado);
        e.setNumero(numero);
        p.setEndereco(e);

        return p;
    }
}