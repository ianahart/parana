import axios from 'axios';
import { IRegisterForm, ILoginForm } from '../interfaces';

export const http = axios.create({
  baseURL: 'http://localhost:5173/api/v1',
});

export const Client = {
  uploadProfilePhoto(file: File | null, endpoint: string, action?: string) {
    const formData = new FormData();
    if (file !== null) {
      formData.append('file', file);
    }
    formData.append('action', action ?? '');

    return http.patch(endpoint, formData);
  },

  resetPassword(
    password: string,
    confirmPassword: string,
    passCode: string,
    token: string
  ) {
    return http.post('/auth/reset-password', {
      password,
      confirmPassword,
      passCode,
      token,
    });
  },

  sendForgotPasswordEmail(email: string) {
    return http.post('/auth/forgot-password', { email });
  },

  logout(refreshToken: string) {
    return http.post('/auth/logout', { refreshToken });
  },

  login(form: ILoginForm) {
    return http.post('/auth/login', {
      email: form.email.value,
      password: form.password.value,
    });
  },

  syncUser: (token: string) => {
    return http.get('/users/sync', {
      headers: { Authorization: 'Bearer ' + token },
    });
  },
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
  heartbeat() {
    return http.get('/heartbeat');
  },
};
