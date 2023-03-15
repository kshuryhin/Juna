import axios from "axios";
import statuses from "@/statuses";

export default {
    methods: {
        async silentLogin(error) {
            if (error.response.status === statuses.TOKEN_EXPIRED) {

                    const response = await axios.put("http://localhost:8085/exchange", {
                        email: localStorage.getItem('email')
                    });
                    localStorage.setItem('token', response.data.token);
            }
        }
    }
}