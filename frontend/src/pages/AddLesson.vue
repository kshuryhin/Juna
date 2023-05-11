<template>
    <div>
        <h1>Add lesson to course {{ this.id }}</h1>
    </div>
</template>

<script>
export default {

    data() {
        return {
            id: 0,
        }
    },

    mounted() {
        this.id = this.$route.params.id
    }
}
</script>

<style lang="scss" scoped>

</style>