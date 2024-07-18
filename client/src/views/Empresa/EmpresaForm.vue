<script setup lang="ts">
    import { ref, onMounted } from 'vue';
    import axios from 'axios';
    import InputText from 'primevue/inputtext';
    import FloatLabel from 'primevue/floatlabel';
    import * as Types from '@/types/Empresa/types';
    import { useRouter } from 'vue-router';

    axios.defaults.headers.common['Content-Type'] = 'application/json';
    const router = useRouter();

    const props = defineProps<{
        id?: string,
    }>();

    const apiUrl = import.meta.env.VITE_API_URL;
    const loading = ref(true);
    const hasError = ref(false);

    const nomeFantasia = ref('');
    const cnpj = ref('');
    const cep = ref('');
    const fornecimentos = ref<Types.FornecimentoEmpresa[]>([]);

    onMounted(async () => {
        if(props.id === undefined) {
            loading.value = false;
        } else {
            try {
                const response = await axios.get<Types.GetEmpresaResponse>(`${apiUrl}/empresa/${props.id}`);
                console.log(response);
                cnpj.value = response.data.empresa.cnpj;
                nomeFantasia.value = response.data.empresa.nomeFantasia;
                cep.value = response.data.empresa.cep;
                fornecimentos.value = response.data.fornecimentos ?? [];
                hasError.value = false;
            } catch (error) {
                hasError.value = true;
                alert("Ocorreu um erro inesperado.");
                console.log(error);
            } finally {
                loading.value = false;
            }
        }
    });

    async function save() {
        try {
            loading.value = true;
            const empresaData: Types.EmpresaDataFields = {
                cnpj: cnpj.value,
                nomeFantasia: nomeFantasia.value,
                cep: cep.value
            };
            var response;
            if(props.id === undefined) {
                const body: Types.PostEmpresaRequest = {
                    empresa: empresaData,
                    fornecedorIds: fornecimentos.value.map(forn => forn.id)
                };
                response = await axios.post(`${apiUrl}/empresa`, body);
            } else {
                const body: Types.PutEmpresaRequest = {
                    id: Number(props.id),
                    ...empresaData
                };
                response = await axios.put(`${apiUrl}/empresa/${props.id}`, body);
            }
            console.log(response);
            alert("Empresa salva com sucesso.");
        } catch (error) {
            alert("Ocorreu um erro inesperado. Tente novamente.");
            console.log(error);
        } finally {
            loading.value = false;
        }
    }

    async function del() {
        if(props.id === undefined) {
            return;
        }
        try {
            loading.value = true;
            const response = await axios.delete(`${apiUrl}/empresa/${props.id}`);
            console.log(response);
            alert("Empresa deletada com sucesso.");
            router.replace("/empresa");
        } catch (error) {
            alert("Ocorreu um erro inesperado. Tente novamente.");
            console.log(error);
        } finally {
            loading.value = false;
        }
    }
</script>

<template>
    <div id="container" class="flex flex-1 flex-col justify-evenly items-center flex-nowrap gap-14">
        <h1 class="text-4xl pb-10">Empresa</h1>

        <i v-if="loading" class="pi pi-spin pi-spinner-dotted self-center text-emerald-600" style="font-size: 2rem"></i>
        <i v-if="hasError" class="pi pi-times self-center text-red-600" style="font-size: 2rem"></i>

        <div>
            <FloatLabel>
                <InputText id="nomeFantasia" v-model="nomeFantasia" size="large" variant="filled"/>
                <label for="nomeFantasia">Nome Fantasia</label>
            </FloatLabel>
        </div>

        <div>
            <FloatLabel>
                <InputText id="cnpj" v-model="cnpj" size="large" variant="filled"/>
                <label for="cnpj">CNPJ</label>
            </FloatLabel>
        </div>

        <div>
            <FloatLabel>
                <InputText id="cep" v-model="cep" size="large" variant="filled"/>
                <label for="cep">CEP</label>
            </FloatLabel>
        </div>
        
        <div class="flex flex-row gap-10 justify-center">
            <button
                class="px-10 bg-blue-600 hover:bg-blue-400 p-2 content-center items-center rounded text-xl"
                @click="save"
            >
                Salvar
            </button>

            <button
                v-if="props.id !== undefined"
                class="px-10 bg-rose-600 hover:bg-rose-400 p-2 content-center items-center rounded text-xl"
                @click="del"
            >
                Deletar
            </button>
        </div>
    </div>
</template>

<style>
</style>