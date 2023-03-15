import axios from "axios";

export const logout = function() {
    axios.put('http://localhost:8085/logout', {}, {
        headers: {
            'Authorization': localStorage.getItem('token')
        }
    })
    localStorage.clear()
}