const roleMixin = {
    created() {
        const { requiredRole } = this.$options;
        const role = localStorage.getItem('role');

        if (role !== requiredRole) {
            this.$router.push('/');
        }
    }
}

export default roleMixin;

