<script setup lang="ts">
    import { ref, onMounted } from 'vue';
    import axios from 'axios';
    import { useRouter } from 'vue-router';

    const router = useRouter();
    axios.defaults.headers.common['Content-Type'] = 'application/json';
    
    const props = defineProps<{
        title: string,
        entity: string,
        colNames: string[],
        colAttrs: string[]
    }>();

    const apiUrl = import.meta.env.VITE_API_URL;
    const initialLoading = ref(true);
    const hasError = ref(false);
    const objs = ref([]);

    onMounted(async () => {
        try {
            const response = await axios.get(`${apiUrl}/${props.entity}/page`);
            objs.value = response.data;
            hasError.value = false;
        } catch (error) {
            hasError.value = true;
            alert("Ocorreu um erro inesperado.");
            console.log(error);
        } finally {
            initialLoading.value = false;
        }
    });

    function handleEditClick(id: number) {
        router.push(`${props.entity}/${id}`);
    }

    function handleAddClick(event: Event) {
        router.push(`${props.entity}/novo`);
    }
</script>

<template>
    <div class="flex flex-1 flex-row justify-center h-full">
        <div class="flex flex-col flex-nowrap justify-start items-start gap-10 w-fit my-20">
            <div class="flex w-full flex-row justify-between items-baseline">
                <h1 class="text-3xl">{{ title }}</h1>
                <button
                    class="bg-blue-600 hover:bg-blue-400 p-2 content-center items-center rounded"
                    @click="handleAddClick"
                >
                    <i class="pi pi-plus" style="font-size: 1.5rem"></i>
                </button>
            </div>
            <i v-if="initialLoading" class="pi pi-spin pi-spinner-dotted self-center text-emerald-600" style="font-size: 2rem"></i>
            <i v-if="hasError" class="pi pi-times self-center text-red-600" style="font-size: 2rem"></i>

            <table>
                <thead>
                    <tr>
                        <th v-for="(colName, index) in props.colNames" :key="index">
                            {{ colName }}
                        </th>
                    </tr>
                </thead>

                <tbody>
                    <tr class="data-row" v-for="obj in objs" :key="obj['id']" @click="handleEditClick(obj['id'])">
                        <td v-for="attr in props.colAttrs" :key="attr">
                            {{ obj[attr] }}
                        </td>
                    </tr>
                </tbody>
            </table>

        </div>
    </div>
</template>

<style>
/* TODO: replace with tailwind */
table {
  border-collapse: collapse;
  width: 100%;
  table-layout: auto !important;
  word-wrap: break-word;
  border:1px solid rgba(224, 242, 237, 0.5);
}

.data-row {
  cursor: pointer;
}

.data-row:hover {
  background-color: #494949;
}

th {
  padding: 15px;
  text-align: center;
}

td {
  padding: 15px;
  text-align: center;
  border:1px solid rgb(224, 242, 237, 0.5);
}
</style> 