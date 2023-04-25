<template>
    <div>
      <h1>Course {{ this.course.id }}</h1>
    </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            course: {},
        }
    },

    methods: {
        async fetchCourseInfo() {
            const id = this.$route.params.id
            const response = await axios.get(`http://localhost:8082/api/v1/courses/${id}`)
            this.course = response.data
        }
    },

    mounted() {
        this.fetchCourseInfo()
    }
}
</script>

<style lang="scss" scoped>

</style>