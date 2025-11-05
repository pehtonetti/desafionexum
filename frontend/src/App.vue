<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <router-link to="/" class="navbar-brand">
          <img src="/src/appends/logo-nexum.jpg" alt="Logo Nexum" height="30" class="me-2" />
          Desafio Nexum
        </router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            </ul>
        </div>
      </div>
    </nav>

    <div class="container mt-4">
      <div class="d-flex align-items-center mb-4">
        <img src="/src/appends/logo-nexum.jpg" alt="Logo Nexum" class="main-logo me-3" />
        <h1>Bem-vindo ao Sistema de Cadastro de Pessoas</h1>
      </div>

      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
/* Estilos globais ou para o App.vue */
.main-logo {
  max-height: 80px; /* Ajuste o tamanho conforme necessário */
  width: auto;
}
/* Adicione outros estilos se precisar */
</style>