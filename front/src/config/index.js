import axios from "axios";

const clienteAxios = axios.create({
  baseURL: "https://us-central1-maiti-d295b.cloudfunctions.net/app",
});

export default clienteAxios;
