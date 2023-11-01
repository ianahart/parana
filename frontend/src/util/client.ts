import axios from 'axios';
import { IRegisterForm, ILoginForm } from '../interfaces';

export const http = axios.create({
  baseURL: 'http://localhost:5173/api/v1',
});

export const Client = {
  createComment(userId: number, postId: number, text: string, ownerId: number) {
    return http.post('/comments', { userId, postId, text, ownerId });
  },

  removeLikePost(userId: number, postId: number) {
    return http.delete(`/posts/${postId}/likes/${userId}`);
  },

  likePost(userId: number, postId: number) {
    return http.post(`/posts/${postId}/likes`, { userId, postId });
  },

  updatePost(postId: number, text: string, file: File | null, gif: string) {
    const formData = new FormData();
    formData.append('text', text);
    formData.append('gif', gif ?? '');
    if (file !== undefined && file !== null) {
      formData.append('file', file);
    }

    return http.patch(`/posts/${postId}`, formData);
  },

  getPost(postId: number) {
    return http.get(`/posts/${postId}`);
  },

  removePost(postId: number) {
    return http.delete(`/posts/${postId}`);
  },

  getPosts(ownerId: number, page: number, pageSize: number, direction: string) {
    return http.get(
      `/posts?ownerId=${ownerId}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  createPost(
    ownerId: number,
    authorId: number,
    postText: string,
    file?: File | null,
    gif?: string
  ) {
    const formData = new FormData();
    formData.append('ownerId', ownerId.toString());
    formData.append('authorId', authorId.toString());
    formData.append('text', postText);
    formData.append('gif', gif ?? '');
    if (file !== undefined && file !== null) {
      formData.append('file', file);
    }
    return http.post('/posts', formData);
  },

  getSuggestions() {
    return http.get('/users/suggestions');
  },

  getFavorites(userId: number, page: number, pageSize: number, direction: string) {
    return http.get(
      `/favorites?userId=${userId}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  removeTeacherFavorite(favoriteId: number) {
    return http.delete(`/favorites/${favoriteId}`);
  },

  getFavorite(userId: number, teacherId: number) {
    return http.get(`/favorites/favorite?userId=${userId}&teacherId=${teacherId}`);
  },

  addTeacherFavorite(userId: number, teacherId: number) {
    return http.post('/favorites', { userId, teacherId });
  },

  deleteRecentSearch(recentSearchId: number) {
    return http.delete(`/recent-searches/${recentSearchId}`);
  },

  getRecentSearches(limit: number) {
    return http.get(`/recent-searches?limit=${limit}`);
  },

  searchTeachers(searchTerm: string, page: number, pageSize: number, direction: string) {
    return http.get(
      `/users/search?searchTerm=${searchTerm}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  deleteRecommendation(recommendationId: number) {
    return http.delete(`/recommendations/${recommendationId}`);
  },

  getRecommendations(
    teacherId: number,
    page: number,
    pageSize: number,
    direction: string
  ) {
    return http.get(
      `/recommendations?teacherId=${teacherId}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  createRecommendation(
    authorId: number,
    teacherId: number,
    recommendation: string,
    words: string
  ) {
    return http.post('/recommendations', { authorId, teacherId, recommendation, words });
  },

  updateReview(
    rating: number,
    review: string,
    userId: number,
    teacherId: number,
    reviewId: number
  ) {
    return http.put(`/reviews/${reviewId}`, { rating, review, userId, teacherId });
  },

  getReview(reviewId: number) {
    return http.get(`reviews/${reviewId}`);
  },

  deleteReview(reviewId: number) {
    return http.delete(`/reviews/${reviewId}`);
  },

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
