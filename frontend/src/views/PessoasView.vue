<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h2>Cadastro de Pessoas</h2>
      <div class="d-flex">
        <input
          v-model="q"
          @keyup.enter="buscar"
          placeholder="Buscar por nome, cidade ou CEP"
          class="form-control me-2"
          style="width: 300px"
        />
        <button class="btn btn-primary me-2" @click="buscar">🔍</button>
        <button class="btn btn-success" @click="abrirModal()">+ Adicionar</button>
      </div>
    </div>

    <div class="table-responsive">
      <table class="table table-hover align-middle">
        <thead class="table-light">
          <tr>
            <th>Nome</th>
            <th>CPF</th>
            <th class="d-none d-md-table-cell">E-mail</th>
            <th class="d-none d-lg-table-cell">Telefone</th>
            <th>Cidade/UF</th>
            <th style="width: 150px;">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="p in pessoas" :key="p.id">
            <td>{{ p.nome }}</td>
            <td>{{ p.cpf }}</td>
            <td class="d-none d-md-table-cell">{{ p.email }}</td>
            <td class="d-none d-lg-table-cell">{{ p.telefone }}</td>
            <td>{{ p.endereco?.cidade }} - {{ p.endereco?.estado }}</td>
            <td>
              <button class="btn btn-sm btn-outline-primary me-1" @click="abrirModal(p)">
                <i class="bi bi-pencil-fill"></i> Editar
              </button>
              <button class="btn btn-sm btn-outline-danger" @click="abrirExcluir(p)">
                <i class="bi bi-trash-fill"></i> Excluir
              </button>
            </td>
          </tr>
          <tr v-if="!pessoas.length">
            <td colspan="6" class="text-center text-muted">Nenhuma pessoa encontrada.</td>
          </tr>
        </tbody>
      </table>
    </div>
    <PessoaModal
      :mostrar="mostrarModal"
      :pessoa-selecionada="pessoaSelecionada"
      @fechar="fecharModal"
      @atualizar="carregarPessoas"
    />

    <ConfirmDeleteModal
      :mostrar="mostrarExcluir"
      :pessoa="pessoaSelecionada"
      @confirmar="confirmarExcluir"
      @cancelar="fecharExcluir"
    />
  </div>
</template>

<script>
import pessoaService from "@/services/pessoaService";
import PessoaModal from "@/components/PessoaModal.vue";
import ConfirmDeleteModal from "@/components/ConfirmDeleteModal.vue";

export default {
  components: { PessoaModal, ConfirmDeleteModal },
  data() {
    return {
      pessoas: [],
      q: "",
      mostrarModal: false,
      mostrarExcluir: false,
      pessoaSelecionada: null,
    };
  },
  methods: {
    async carregarPessoas() {
      try {
        const res = await pessoaService.listar(this.q);
        this.pessoas = res.data;
      } catch (e) {
        console.error("Erro ao carregar pessoas:", e);
      }
    },
    buscar() {
      this.carregarPessoas();
    },
    abrirModal(p = null) {
      this.pessoaSelecionada = p;
      this.mostrarModal = true;
    },
    fecharModal() {
      this.mostrarModal = false;
      this.pessoaSelecionada = null;
    },
    abrirExcluir(p) {
      this.pessoaSelecionada = p;
      this.mostrarExcluir = true;
    },
    fecharExcluir() {
      this.mostrarExcluir = false;
      this.pessoaSelecionada = null;
    },
    async confirmarExcluir() {
      if (!this.pessoaSelecionada) return;
      await pessoaService.excluir(this.pessoaSelecionada.id);
      this.fecharExcluir();
      this.carregarPessoas();
    },
  },
  mounted() {
    this.carregarPessoas();
  },
};
</script>

<style scoped>
.container {
  max-width: 1100px;
}
</style>
