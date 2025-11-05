<template>
  <div v-if="mostrar" class="modal-overlay">
    <div class="modal-container">
      <div class="modal-box p-4">
        <h5 class="text-danger mb-3 border-bottom pb-2">
          ⚠️ Confirmar Exclusão
        </h5>

        <p class="mb-4">
          Você tem certeza que deseja **excluir permanentemente** o cadastro de:
          <br>
          <strong class="text-primary">{{ pessoa?.nome }}</strong>?
        </p>

        <div class="d-flex justify-content-end">
          <button
            class="btn btn-secondary me-2"
            @click="$emit('cancelar')"
          >
            Cancelar
          </button>
          <button
            class="btn btn-danger"
            @click="$emit('confirmar')"
          >
            Sim, Excluir
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    mostrar: Boolean,
    pessoa: Object
  }
}
</script>

<style scoped>
/* Reutilizamos o mesmo estilo de overlay do PessoaModal para consistência */
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
  border-radius: 8px;
  width: 90%;
  max-width: 450px; /* Modal de exclusão pode ser menor */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}
</style>