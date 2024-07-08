<script setup lang="ts">
    import MyTable from '@/components/MyTable.vue';
</script>

<template>
    <MyTable
        title="Empresas"
        entity="empresa"
        :colAttrs="['nomeFantasia', 'cnpj', 'cep']"
        :colNames="['Nome fantasia', 'CNPJ', 'CEP']"
    />
</template>