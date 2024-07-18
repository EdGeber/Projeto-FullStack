<script setup lang="ts">
    import { ref } from 'vue';
    import Menubar from 'primevue/menubar';
    import { useRouter } from 'vue-router';
    
    const router = useRouter();

    const items = [
        {
            label: "Empresas",
            url: "/empresa"
        },
        {
            label: "Fornecedores",
            url: "/fornecedor"
        }
    ];
</script>

<template>
    <Menubar :model="items" class="!bg-emerald-900 min-w-40 flex-row justify-center"/>
</template>