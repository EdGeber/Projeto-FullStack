import { createRouter, createWebHistory } from "vue-router";

import HomeView from '@/views/HomeView.vue';
import EmpresaForm from "@/views/Empresa/EmpresaForm.vue";
import EmpresaTable from "@/views/Empresa/EmpresaTable.vue";
import FornecedorForm from "@/views/Fornecedor/FornecedorForm.vue";
import FornecedorTable from "@/views/Fornecedor/FornecedorTable.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/empresas',
            name: 'empresas',
            component: EmpresaTable
        },
        {
            path: '/empresas/novo',
            name: 'novaEmpresa',
            component: EmpresaForm,
            props: true
        },
        {
            path: '/empresas/:id',
            name: 'empresaForm',
            component: EmpresaForm,
            props: true
        },
        {
            path: '/fornecedores',
            name: 'fornecedores',
            component: FornecedorTable
        },
        {
            path: '/fornecedores/novo',
            name: 'novoFornecedor',
            component: FornecedorForm,
            props: true
        },
        {
            path: '/fornecedores/:id',
            name: 'fornecedorForm',
            component: FornecedorForm,
            props: true
        },
    ]
});

export default router;
