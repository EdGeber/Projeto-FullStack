<script setup lang="ts">
    import Navbar from '@/components/Navbar.vue';
    import { RouterView } from 'vue-router';
</script>

<template>
    <div class="h-screen flex flex-col flex-nowrap justify-between items-stretch">
        <div class="flex flex-row justify-center m-5">
            <Navbar/>
        </div>
        <div class="flex flex-1 justify-center items-center">
            <RouterView/>
        </div>
    </div>
</template>

<style>
</style>