<script setup lang="ts">
    import { ref, onMounted } from 'vue';
    import axios from 'axios';
    import InputText from 'primevue/inputtext';
    import FloatLabel from 'primevue/floatlabel';
    import DatePicker from 'primevue/datepicker';
    import * as Types from '@/types/Fornecedor/types';
    import { useRouter } from 'vue-router';

    axios.defaults.headers.common['Content-Type'] = 'application/json';
    const router = useRouter();

    const props = defineProps<{
        id?: string,
    }>();

    const apiUrl = import.meta.env.VITE_API_URL;
    const loading = ref(true);
    const hasError = ref(false);

    const nome = ref('');
    const email = ref('');
    const cep = ref('');
    const cadastro = ref('');
    const rg = ref<string | null | undefined>('');
    const dataNascimento = ref<string | null | undefined>('');
    const fornecimentos = ref<Types.FornecimentoFornecedor[]>([]);

    onMounted(async () => {
        if(props.id === undefined) {
            loading.value = false;
        } else {
            try {
                const response = await axios.get<Types.GetFornecedorResponse>(`${apiUrl}/fornecedor/${props.id}`);
                console.log(response);
                nome.value = response.data.fornecedor.nome;
                email.value = response.data.fornecedor.email;
                cep.value = response.data.fornecedor.cep;
                cadastro.value = response.data.fornecedor.cadastro;
                rg.value = response.data.fornecedor.rg;
                dataNascimento.value = response.data.fornecedor.dataNascimento;
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
            const fornecedorData: Types.FornecedorDataFields = {
                nome: nome.value,
                email: email.value,
                cep: cep.value,
                cadastro: cadastro.value,
                rg: rg.value,
                dataNascimento: dataNascimento.value
            };
            var response;
            if(props.id === undefined) {
                const body: Types.PostFornecedorRequest = {
                    fornecedor: fornecedorData,
                    empresaIds: fornecimentos.value.map(emp => emp.id)
                };
                response = await axios.post(`${apiUrl}/fornecedor`, body);
            } else {
                const body: Types.PutFornecedorRequest = {
                    id: Number(props.id),
                    ...fornecedorData
                };
                response = await axios.put(`${apiUrl}/fornecedor/${props.id}`, body);
            }
            console.log(response);
            alert("Forecedor salvo com sucesso.");
        } catch (error) {
            alert("Ocorreu um erro. Tente novamente.");
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
            const response = await axios.delete(`${apiUrl}/fornecedor/${props.id}`);
            console.log(response);
            alert("Fornecedor deletado com sucesso.");
            router.replace("/fornecedor");
        } catch (error) {
            alert("Ocorreu um erro inesperado. Tente novamente.");
            console.log(error);
        } finally {
            loading.value = false;
        }
    }
</script>

<template>
    <div id="container" class="flex flex-1 flex-col justify-evenly items-center flex-nowrap gap-14 my-10">
        <h1 class="text-4xl pb-10">Fornecedor</h1>

        <i v-if="loading" class="pi pi-spin pi-spinner-dotted self-center text-emerald-600" style="font-size: 2rem"></i>
        <i v-if="hasError" class="pi pi-times self-center text-red-600" style="font-size: 2rem"></i>

        <div>
            <FloatLabel>
                <InputText id="nome" v-model="nome" size="large" variant="filled"/>
                <label for="nome">Nome Fantasia</label>
            </FloatLabel>
        </div>

        <div>
            <FloatLabel>
                <InputText id="email" v-model="email" size="large" variant="filled"/>
                <label for="email">Email</label>
            </FloatLabel>
        </div>

        <div>
            <FloatLabel>
                <InputText id="cep" v-model="cep" size="large" variant="filled"/>
                <label for="cep">CEP</label>
            </FloatLabel>
        </div>

        <div>
            <FloatLabel>
                <InputText id="cadastro" v-model="cadastro" size="large" variant="filled"/>
                <label for="cadastro">CPF ou CNPJ</label>
            </FloatLabel>
        </div>

        <div class="flex flex-col gap-2 items-center">
            <FloatLabel>
                <InputText id="rg" v-model="rg" size="large" variant="filled"/>
                <label for="rg">RG</label>
            </FloatLabel>
            <small>Apenas necessário se o fornecedor for pessoa física.</small>
        </div>

        <div class="flex flex-col gap-2 items-center">
            <FloatLabel>
                <DatePicker
                    id="dataNascimento"
                    v-model="dataNascimento"
                    size="large"
                    variant="filled"
                    dateFormat="mm/dd/yy"
                    showIcon
                    fluid
                    iconDisplay="input"
                />
                <label for="dataNascimento">Data de nascimento</label>
            </FloatLabel>
            <small>Apenas necessário se o fornecedor for pessoa física.</small>
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