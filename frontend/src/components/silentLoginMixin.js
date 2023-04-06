import axios from 'axios';

export default {
    methods: {
        async silentLogin() {
            const response = await axios.put("http://localhost:8085/exchange", {}, {
                headers: {
                    'Authorization': localStorage.getItem('token')
                }
            });
            localStorage.setItem('token', response.data.token);
            return true;
        },
    },
    created() {
        axios.interceptors.response.use(
            (response) => response,
            async (error) => {
                if (error.response && error.response.status === 418) {
                    const success = await this.silentLogin();
                    if (success) {
                        const config = error.config;
                        config.headers.Authorization = localStorage.getItem('token');
                        return axios.request(config);
                    }
                }
                return Promise.reject(error);
            }
        );
    },
};