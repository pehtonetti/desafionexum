import axios from 'axios';

const API_URL = 'http://localhost:8080/api/pessoas'; // ajuste se backend for outra porta

export default {
  listar(q) {
    return axios.get(API_URL, { params: { q } });
  },
  criar(pessoa) {
    return axios.post(API_URL, pessoa);
  },
  atualizar(id, pessoa) {
    return axios.put(`${API_URL}/${id}`, pessoa);
  },
  excluir(id) {
    return axios.delete(`${API_URL}/${id}`);
  },
};
