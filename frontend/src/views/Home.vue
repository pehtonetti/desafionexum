<template>
    <div class="text-center mt-5">
        <div class="d-flex justify-content-center align-items-center mb-4">
            <img :src="logoUrl" alt="Logo Nexum Tecnologia" class="main-logo me-4" />
            <h1 class="display-4">Bem-vindo</h1>
        </div>

        <p class="lead mb-4">Sistema de Cadastro de Pessoas</p>

        <router-link class="btn btn-primary btn-lg" to="/pessoas">
            Ir para Cadastro de Pessoas
        </router-link>
    </div>
</template>

<script>
// 🌟 IMPORTAÇÃO DA IMAGEM DO CAMINHO ESPECIFICADO
import logoNexum from '@/appends/logo-nexum.jpg';

export default {
    name: 'Home',
    data() {
        return {
            logoUrl: logoNexum, // Atribui a imagem importada a uma variável reativa
        };
    },
};
</script>

<style scoped>
/* Estilos para o logo na tela inicial */
.main-logo {
    max-height: 100px; /* Tamanho maior para destaque na tela inicial */
    width: auto;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
}
.display-4 {
    font-weight: 300;
}
</style>