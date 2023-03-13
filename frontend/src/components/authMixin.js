export default {
    created() {
        const token = localStorage.getItem('token')
        if (!token) {
            this.$router.push('/')
        }
    }
}
