<script setup lang="ts">
    import { ref, onMounted, computed } from 'vue';
    import axios from 'axios';
    import DataTable from 'primevue/datatable';
    import Column from 'primevue/column';

    axios.defaults.headers.common['Content-Type'] = 'application/json';
    
    const props = defineProps({
        title: {
            type: String,
            default: 'Tabela'
        },
        entity: {
            type: String,
            default: ''
        },
        colNames: {
            type: Array,
            default: []
        },
        colAttrs: {
            type: Array,
            default: []
        }
    });

    const apiUrl = import.meta.env.VITE_API_URL;
    let initialLoading = ref(true);
    let hasError = ref(false);
    let objs = ref([]);

    onMounted(async () => {
        try {
            const response = await axios.get(`${apiUrl}/${props.entity}/page`);
            objs.value = response.data;
            hasError.value = false;
            console.log(objs);
            console.log(props.colAttrs);
            console.log(props.colNames);
        } catch (error) {
            hasError.value = true;
            alert("Ocorreu um erro inesperado.");
            console.log(error);
        } finally {
            initialLoading.value = false;
        }
    });
</script>

<template>
    <div class="flex flex-1 flex-row justify-center h-full">
        <div class="flex flex-col flex-nowrap justify-start items-start gap-10 w-fit my-20">
            <h1 class="text-3xl">{{ title }}</h1>
            <i v-if="initialLoading" class="pi pi-spin pi-spinner-dotted self-center text-emerald-600" style="font-size: 2rem"></i>
            <i v-if="hasError" class="pi pi-times self-center text-red-600" style="font-size: 2rem"></i>
            <DataTable
                :value="objs"
                class="bg-color-white"
                size="large"
                show-gridlines="true"
                striped-rows="true"
            >
                <Column
                    v-for="(colName, index) in props.colNames"
                    :key="index"
                    :field="props.colAttrs[index]"
                    :header="colName"
                ></Column>
            </DataTable>
        </div>
    </div>
</template>