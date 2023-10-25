import axios from 'axios';
import { IRegisterForm, ILoginForm } from '../interfaces';

export const http = axios.create({
  baseURL: 'http://localhost:5173/api/v1',
});

export const Client = {
  getReviews(teacherId: number, page: number, pageSize: number, direction: string) {
    return http.get(
      `/reviews?teacherId=${teacherId}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  createReview(rating: number, review: string, userId: number, teacherId: number) {
    return http.post('/reviews', { rating, review, userId, teacherId });
  },

  getConnections(
    userId: number,
    page: number,
    pageSize: number,
    direction: string,
    searchTerm: string
  ) {
    return http.get(
      `/connections?userId=${userId}&page=${page}&pageSize=${pageSize}&direction=${direction}&searchTerm=${searchTerm}`
    );
  },

  confirmConnectionRequest(connectionId: number, currentUserId: number) {
    return http.patch(`/connections/${connectionId}`, { currentUserId });
  },

  removeConnection(connectionId: number) {
    return http.delete(`/connections/${connectionId}`);
  },

  getConnectionRequests(
    userId: number,
    role: string,
    page: number,
    pageSize: number,
    direction: string
  ) {
    return http.get(
      `/connections/requests?userId=${userId}&role=${role}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  getConnectionStatus(senderId: number, receiverId: number) {
    return http.get(`/connections/status?senderId=${senderId}&receiverId=${receiverId}`);
  },

  createConnectionRequest(senderId: number, receiverId: number) {
    return http.post(`/connections`, { senderId, receiverId });
  },

  getTeachers(
    role = '',
    page: number,
    direction: string,
    pageSize: number,
    rate: number,
    distance: number
  ) {
    return http.get(
      `/users?role=${role}&page=${page}&direction=${direction}&pageSize=${pageSize}&rate=${rate}&distance=${distance}`
    );
  },

  getProfile(profileId: number) {
    return http.get(`/profiles/${profileId}`);
  },

  updateProfile<T>(form: T, profileId: number) {
    return http.patch(`/profiles/${profileId}`, form);
  },

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
