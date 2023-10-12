import axios from 'axios';
import { IRegisterForm } from '../interfaces';

export const http = axios.create({
  baseURL: 'http://localhost:5173/api/v1',
});

export const Client = {
  register(form: IRegisterForm, role: string) {
    return http.post('/auth/register', {
      email: form.email.value,
      firstName: form.firstName.value,
      lastName: form.lastName.value,
      password: form.password.value,
      confirmPassword: form.confirmPassword.value,
      role: role.toUpperCase(),
    });
  },
};
