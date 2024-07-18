<script setup lang="ts">
    import { ref, onMounted } from 'vue';
    import axios from 'axios';
    import InputText from 'primevue/inputtext';
    import FloatLabel from 'primevue/floatlabel';
    import * as EmpresaTypes from '@/types/Empresa/types';
    import * as FornecedorTypes from '@/types/Fornecedor/types';
    import { useRouter } from 'vue-router';
    import MultiSelect from 'primevue/multiselect';

    interface FornecedorOption {
        fornecedorId: number, // id
        fornecedorNome: string  // nome
    }

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
    let currentFornecedores: EmpresaTypes.FornecimentoEmpresa[] = [];
    const selectedFornecedores = ref<number[]>([]);
    // deve ser ref porque pode ser alterado dinamicamente ao implementarmos paginacao
    const fornecedorOptions = ref<FornecedorOption[]>([]);

    onMounted(async () => {
        try {
            const fornecedoresResponse = await axios.get<FornecedorTypes.GetFornecedoresResponse>(`${apiUrl}/fornecedor/page`);
            console.log(fornecedoresResponse);
            fornecedorOptions.value = fornecedoresResponse.data.map(
                forn => { return {fornecedorId: forn.id, fornecedorNome: forn.nome} }
            );

            if(props.id !== undefined) {
                const empresaResponse = await axios.get<EmpresaTypes.GetEmpresaResponse>(`${apiUrl}/empresa/${props.id}?fornecedores=true`);
                console.log(empresaResponse);

                cnpj.value = empresaResponse.data.empresa.cnpj;
                nomeFantasia.value = empresaResponse.data.empresa.nomeFantasia;
                cep.value = empresaResponse.data.empresa.cep;

                currentFornecedores = empresaResponse.data.fornecimentos ?? [];
                selectedFornecedores.value = currentFornecedores.map(
                    forn => forn.fornecedorId
                );
            }

            hasError.value = false;
        } catch (error) {
            hasError.value = true;
            alert("Ocorreu um erro inesperado.");
            console.log(error);
        } finally {
            loading.value = false;
        }
    });

    async function save() {
        try {
            loading.value = true;
            const empresaData: EmpresaTypes.EmpresaDataFields = {
                cnpj: cnpj.value,
                nomeFantasia: nomeFantasia.value,
                cep: cep.value
            };
            var response;
            if(props.id === undefined) {
                // post
                const body: EmpresaTypes.PostEmpresaRequest = {
                    empresa: empresaData,
                    fornecedorIds: selectedFornecedores.value
                };
                response = await axios.post<EmpresaTypes.PostEmpresaRequest, {data: EmpresaTypes.PostEmpresaResponse}>(`${apiUrl}/empresa`, body);
            } else {
                // put
                const body: EmpresaTypes.PutEmpresaRequest = {
                    id: Number(props.id),
                    ...empresaData
                };
                // deletar os que estao em current mas nao em selected
                // TODO: melhorar a performance, porque isso tem complexidade de tempo quadratica
                const fornecedorIdsToDelete = currentFornecedores.filter(
                    currForn => !selectedFornecedores.value.some(selectedFornId => selectedFornId === currForn.fornecedorId)
                ).map(forn => forn.fornecedorId);
                const fornecimentoIdsToDelete = currentFornecedores.filter(
                    currForn => fornecedorIdsToDelete.some(fornId => fornId === currForn.fornecedorId)
                ).map(currForn => currForn.id);
                // adicionar os que estao em selected mas nao em current
                // TODO: melhorar a performance, porque isso tem complexidade de tempo quadratica
                const fornecedorIdsToAdd = selectedFornecedores.value.filter(
                    selectedFornId => !currentFornecedores.some(currForn => currForn.fornecedorId === selectedFornId)
                );
                console.log(fornecedorIdsToDelete);
                const [response, addResponse, deleteResponse] = await Promise.all([
                    await axios.put(`${apiUrl}/empresa/${props.id}`, body),
                    await axios.post(`${apiUrl}/empresa/${props.id}/fornecimentos`, fornecedorIdsToAdd),
                    await axios.put(`${apiUrl}/fornecimento/delete`, fornecimentoIdsToDelete),
                ]);
                console.log(addResponse);
                console.log(deleteResponse);
            }
            console.log(response);
            alert("Empresa salva com sucesso.");
            if(response?.data?.empresaId) {
                router.replace(`/empresa/${response.data.empresaId}`);
            }
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
            alert("Ocorreu um erro. Tente novamente.");
            console.log(error);
        } finally {
            loading.value = false;
        }
    }
</script>

<template>
    <div id="container" class="flex flex-1 flex-col justify-evenly items-center flex-nowrap gap-14 my-10">
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

        <div class="flex flex-col gap-2 text-gray-400">
            <FloatLabel>
                <MultiSelect
                    id="fornecedores"
                    v-model="selectedFornecedores"
                    :options="fornecedorOptions"
                    optionValue="fornecedorId"
                    optionLabel="fornecedorNome"
                    filter
                    :loading="loading"
                    placeholder="Selecione fornecedores"
                    :virtualScrollerOptions="{ itemSize: 44 }"
                    variant="filled"
                    :maxSelectedLabels="2"
                    class="w-full md:w-80"
                />
                <label for="fornecedores">Fornecedores</label>
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