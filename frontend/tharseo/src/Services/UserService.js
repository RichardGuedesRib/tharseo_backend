import serverConfig from "./ServerConfig";
import axios from 'axios'

export async function getUserData(userID) {
    const response = await axios.get(serverConfig.addressServerTharseo + "/users/" + userID);
    // console.log("RETORNO DO GETUSER", response.data);
    return response.data;
}



