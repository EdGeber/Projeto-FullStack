<script setup lang="ts">
    import MyTable from '@/components/MyTable.vue';
</script>

<template>
    <MyTable
        title="Fornecedores"
        entity="fornecedor"
        :colAttrs="['nome', 'email', 'cadastro', 'rg', 'dataNascimento', 'cep']"
        :colNames="['Nome', 'email', 'CNPJ ou CPF', 'RG', 'Data de nascimento', 'CEP']"
    />
</template>
