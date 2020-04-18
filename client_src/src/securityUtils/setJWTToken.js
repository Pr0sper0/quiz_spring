import axios from "axios" 

const setJWTToken = token => {
    if(token) {
        axios.defaults.headers.common["Authorizaion"] = token;
    } else {
        delete axios.defaults.headers.common["Authorizaion"];
    }
}

export default setJWTToken;