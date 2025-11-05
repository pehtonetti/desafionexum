<template>
  <div v-if="mostrar" class="modal-overlay">
    <div class="modal-container">
      <div class="modal-box">
        <h4 class="mb-4 text-primary border-bottom pb-2">
          {{ editando ? 'Editar Cadastro' : 'Novo Cadastro de Pessoa' }}
        </h4>

        <form @submit.prevent="salvar">
          <div class="row">
            <div class="col-md-6">
              <h5 class="mb-3 text-secondary">Dados Pessoais</h5>

              <div class="mb-3">
                <label class="form-label">Nome Completo</label>
                <input class="form-control" v-model="pessoa.nome" required />
              </div>

              <div class="mb-3">
                <label class="form-label">CPF</label>
                <input
                  class="form-control"
                  v-model="pessoa.cpf"
                  required
                  maxlength="14"
                  placeholder="000.000.000-00"
                />
              </div>

              <div class="mb-3">
                <label class="form-label">Telefone</label>
                <input
                  class="form-control"
                  v-model="pessoa.telefone"
                  placeholder="(00) 00000-0000"
                />
              </div>

              <div class="mb-4">
                <label class="form-label">E-mail</label>
                <input
                  class="form-control"
                  v-model="pessoa.email"
                  type="email"
                  required
                  placeholder="exemplo@dominio.com"
                />
              </div>
            </div>

            <div class="col-md-6 border-start ps-md-4">
              <h5 class="mb-3 text-secondary">Endereço</h5>

              <div class="mb-3">
                <label class="form-label">CEP</label>
                <input
                  class="form-control"
                  v-model="pessoa.endereco.cep"
                  @blur="buscarCep"
                  required
                  maxlength="9"
                  placeholder="00000-000"
                />
              </div>

              <div class="mb-3">
                <label class="form-label">Logradouro (Rua/Av)</label>
                <input class="form-control" v-model="pessoa.endereco.logradouro" />
              </div>

              <div class="row mb-3">
                <div class="col-5">
                  <label class="form-label">Número</label>
                  <input class="form-control" v-model="pessoa.endereco.numero" required/>
                </div>
                <div class="col-7">
                  <label class="form-label">Complemento</label>
                  <input class="form-control" v-model="pessoa.endereco.complemento" />
                </div>
              </div>

              <div class="mb-3">
                <label class="form-label">Bairro</label>
                <input class="form-control" v-model="pessoa.endereco.bairro" />
              </div>

              <div class="row">
                <div class="col-8 mb-3">
                    <label class="form-label">Cidade</label>
                    <input class="form-control" v-model="pessoa.endereco.cidade" />
                </div>
                <div class="col-4 mb-3">
                    <label class="form-label">Estado (UF)</label>
                    <input class="form-control" v-model="pessoa.endereco.estado" maxlength="2" />
                </div>
              </div>
            </div>
          </div>

          <div class="d-flex justify-content-end pt-4 border-top mt-3">
            <button
              type="button"
              class="btn btn-secondary me-2"
              @click="$emit('fechar')"
            >
              Cancelar
            </button>
            <button type="submit" class="btn btn-primary">
              {{ editando ? 'Salvar Alterações' : 'Cadastrar' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
// A seção <script> permanece a mesma do passo anterior (com a lógica de inicialização e salvamento corrigida)
import axios from 'axios';

export default {
  props: {
    mostrar: Boolean,
    pessoaSelecionada: Object,
  },
  data() {
    return {
      // ⚠️ pessoa deve ser inicializada ou observada corretamente
      pessoa: this.inicializarPessoa(this.pessoaSelecionada),
    };
  },
  computed: {
    editando() {
      return this.pessoa && this.pessoa.id;
    },
  },
  watch: {
    // 💡 IMPORTANTE: Observa a prop para carregar dados ao editar
    pessoaSelecionada(novoPessoa) {
      this.pessoa = this.inicializarPessoa(novoPessoa);
    },
  },
  methods: {
    /**
     * Auxiliares de Estrutura
     */
    getEstruturaEnderecoVazio() {
        return {
            cep: '',
            logradouro: '',
            numero: '',
            complemento: '',
            bairro: '',
            cidade: '',
            estado: '',
        };
    },
    inicializarPessoa(p) {
        // Se p existir, significa que estamos editando.
        if (p) {
            // Clona o objeto recebido para evitar mutação da prop (Boa Prática Vue)
            const clone = JSON.parse(JSON.stringify(p));
            // Garante que 'endereco' e outros campos existam no clone
            if (!clone.endereco) {
                clone.endereco = this.getEstruturaEnderecoVazio();
            }
            // Garante que Telefone e Email existam (se não estiverem no objeto vindo do DB)
            if (!clone.telefone) clone.telefone = '';
            if (!clone.email) clone.email = '';

            return clone;
        }
        // Ao criar: retorna um objeto base com a estrutura completa
        return {
            nome: '',
            cpf: '',
            telefone: '', // Campo novo
            email: '',    // Campo novo
            endereco: this.getEstruturaEnderecoVazio()
        };
    },

    /**
     * Lógica de Negócio: CEP
     */
    async buscarCep() {
        // Limpa o CEP para consulta
        const cepLimpo = this.pessoa.endereco.cep.replace(/[^0-9]/g, "");

        if (cepLimpo.length === 8) {
            try {
                const res = await axios.get(`https://viacep.com.br/ws/${cepLimpo}/json/`);
                const data = res.data;

                if (!data.erro) {
                    this.pessoa.endereco.logradouro = data.logradouro;
                    this.pessoa.endereco.bairro = data.bairro;
                    this.pessoa.endereco.cidade = data.localidade;
                    this.pessoa.endereco.estado = data.uf;
                    this.pessoa.endereco.numero = '';
                    this.pessoa.endereco.complemento = '';

                    this.$nextTick(() => {
                        const numeroInput = document.querySelector('input[v-model="pessoa.endereco.numero"]');
                        if (numeroInput) numeroInput.focus();
                    });

                } else {
                    alert("CEP não encontrado.");
                }
            } catch (err) {
                console.error("Erro ao buscar CEP:", err);
                alert("Erro ao consultar ViaCEP. Tente novamente.");
            }
        }
    },

    /**
     * Lógica de Salvamento (Criar/Editar) - CRÍTICO!
     */
    async salvar() {
      const service = this.$pessoaService;

      // 1. Clona e Limpa os dados para envio (Prevenindo erro de validação do Backend)
      const dadosParaSalvar = JSON.parse(JSON.stringify(this.pessoa));

      // Remove caracteres não numéricos de CPF (o Backend espera só 11 dígitos)
      if (dadosParaSalvar.cpf) {
          dadosParaSalvar.cpf = dadosParaSalvar.cpf.replace(/[^0-9]/g, "");
      }

      // Remove caracteres não numéricos do CEP
      if (dadosParaSalvar.endereco && dadosParaSalvar.endereco.cep) {
          dadosParaSalvar.endereco.cep = dadosParaSalvar.endereco.cep.replace(/[^0-9]/g, "");
      }

      try {
        let res;
        if (this.editando) {
            res = await service.atualizar(dadosParaSalvar.id, dadosParaSalvar);
        } else {
            res = await service.criar(dadosParaSalvar);
        }

        console.log("Resposta do servidor:", res && res.data ? res.data : res);
        this.$emit("atualizar");
        this.$emit("fechar");
        alert("✅ Salvo com sucesso");

      } catch (err) {
        // Lógica de tratamento de erro
        console.error("Erro completo ao salvar:", err);
        const status = err.response?.status;
        let mensagemErro = err.response?.data?.message || err.message;

        if (status === 400 && err.response.data.errors) {
            const validationErrors = err.response.data.errors.map(e => {
                let campo = e.field || 'Campo';
                return `${campo}: ${e.defaultMessage}`;
            }).join('\n');
            mensagemErro = `Erros de Validação (400):\n${validationErrors}`;
        }

        if (mensagemErro.includes("CPF inválido")) {
             mensagemErro = `❌ Erro de Negócio: ${mensagemErro}`;
        }

        alert(`⚠️ Erro ao salvar. Status: ${status || 'N/A'}\n\n${mensagemErro}`);
      }
    },
  },
};
</script>

<style scoped>
/* CSS CORRIGIDO E RESPONSIVO */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.55);
  backdrop-filter: blur(3px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.modal-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
}

.modal-box {
  background: #fff;
  padding: 30px;
  border-radius: 8px;
  width: 90%; /* Ocupa 90% da largura */
  max-width: 900px; /* Limita a largura máxima para não esticar demais em telas grandes */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  max-height: 90vh; /* Limita a altura do modal */
  overflow-y: auto; /* Adiciona scroll se o conteúdo for maior que a tela */
}

/* Garante que em telas pequenas (mobile), o layout de 2 colunas se torna 1 coluna (feito pelo Bootstrap) */

.border-start {
    border-color: #dee2e6 !important; /* Cor mais neutra para a linha divisória */
}

/* Remove a linha divisória em telas pequenas para melhor responsividade */
@media (max-width: 767.98px) {
    .border-start {
        border-left: none !important;
        padding-left: 0 !important;
        margin-top: 20px;
        border-top: 1px solid #dee2e6;
        padding-top: 20px;
    }
}
</style>