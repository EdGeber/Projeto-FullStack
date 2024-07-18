import { createRouter, createWebHistory } from "vue-router";

import HomeView from '@/views/HomeView.vue';
import EmpresaForm from "@/views/Empresa/EmpresaForm.vue";
import EmpresaTable from "@/views/Empresa/EmpresaTable.vue";
import FornecedorForm from "@/views/Fornecedor/FornecedorForm.vue";
import FornecedorTable from "@/views/Fornecedor/FornecedorTable.vue";
import PageNotFound from "@/views/PageNotFound.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/empresa',
            name: 'empresas',
            component: EmpresaTable
        },
        {
            path: '/empresa/novo',
            name: 'novaEmpresa',
            component: EmpresaForm,
            props: true
        },
        {
            path: '/empresa/:id',
            name: 'empresaForm',
            component: EmpresaForm,
            props: true
        },
        {
            path: '/fornecedor',
            name: 'fornecedores',
            component: FornecedorTable
        },
        {
            path: '/fornecedor/novo',
            name: 'novoFornecedor',
            component: FornecedorForm,
            props: true
        },
        {
            path: '/fornecedor/:id',
            name: 'fornecedorForm',
            component: FornecedorForm,
            props: true
        },
        {
            path: "/:pathMatch(.*)*",
            component: PageNotFound
        }
    ]
});

export default router;
