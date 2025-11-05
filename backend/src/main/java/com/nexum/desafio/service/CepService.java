package com.nexum.desafio.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
public class CepService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    private final RestTemplate restTemplate = new RestTemplate();

    // Classe interna que mapeia o JSON retornado pelo ViaCEP
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EnderecoResponse {
        private String cep;
        private String logradouro;
        private String bairro;
        private String localidade;
        private String uf;
        private Boolean erro;

        // Getters e Setters
        public String getCep() { return cep; }
        public void setCep(String cep) { this.cep = cep; }
        public String getLogradouro() { return logradouro; }
        public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
        public String getBairro() { return bairro; }
        public void setBairro(String bairro) { this.bairro = bairro; }
        public String getLocalidade() { return localidade; }
        public void setLocalidade(String localidade) { this.localidade = localidade; }
        public String getUf() { return uf; }
        public void setUf(String uf) { this.uf = uf; }
        public Boolean getErro() { return erro; }
        public void setErro(Boolean erro) { this.erro = erro; }
    }

    // Método público para consultar o CEP
    public EnderecoResponse consultarCep(String cep) {
        try {
            if (cep == null || cep.isBlank()) {
                return null;
            }

            // Remove caracteres não numéricos
            String cepLimpo = cep.replaceAll("\\D", "");
            if (cepLimpo.length() != 8) {
                return null;
            }

            String url = UriComponentsBuilder.fromUriString(VIA_CEP_URL)
                    .buildAndExpand(cepLimpo)
                    .toUriString();

            return restTemplate.getForObject(url, EnderecoResponse.class);
        } catch (Exception e) {
            System.err.println("Erro ao consultar o CEP: " + e.getMessage());
            return null;
        }
    }
}
