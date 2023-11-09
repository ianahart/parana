import axios from 'axios';
import { IRegisterForm, ILoginForm, IBlock } from '../interfaces';

export const http = axios.create({
  baseURL: 'http://localhost:5173/api/v1',
});

export const Client = {
  removeNotification(notificationId: number) {
    return http.delete(`/notifications/${notificationId}`);
  },

  getNotifications(userId: number, page: number, pageSize: number, direction: string) {
    return http.get(
      `/notifications?userId=${userId}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  updateBlockedUser(block: string, isChecked: boolean, privacyId: number) {
    return http.patch(`/privacies/${privacyId}`, { block, isChecked });
  },

  getBlockedUsers(userId: number, page: number, pageSize: number, direction: string) {
    return http.get(
      `/privacies?userId=${userId}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  blockUser(blockedByUserId: number, blockedUserId: number, block: IBlock) {
    return http.post('/privacies', { blockedByUserId, blockedUserId, block });
  },

  getNonBlockedUsers(
    userId: number,
    blockType: string,
    fullName: string,
    page: number,
    pageSize: number,
    direction: string
  ) {
    return http.get(
      `/users/non-blocked?userId=${userId}&blockType=${blockType}&page=${page}&pageSize=${pageSize}&direction=${direction}&fullName=${fullName}`
    );
  },

  deleteAccount(userId: number) {
    return http.delete(`/users/${userId}`);
  },

  updateRememberMe(settingId: number, rememberMe: boolean) {
    return http.patch(`/settings/${settingId}/remember-me`, { rememberMe });
  },

  getFeedPosts(userId: number, page: number, pageSize: number, direction: string) {
    return http.get(
      `/posts/feed?userId=${userId}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  changeEmail(email: string, password: string, userId: number) {
    return http.patch(`users/${userId}/change-email`, { email, password });
  },

  getSettings(settingId: number) {
    return http.get(`/settings/${settingId}`);
  },
  changePassword(
    userId: number,
    oldPassword: string,
    password: string,
    confirmPassword: string
  ) {
    return http.patch(`users/${userId}/change-password`, {
      oldPassword,
      password,
      confirmPassword,
    });
  },

  updateSettings(
    settingId: number,
    rememberMe: boolean,
    blockMessages: boolean,
    blockComments: boolean,
    blockPosts: boolean
  ) {
    return http.patch(`/settings/${settingId}`, {
      rememberMe,
      blockMessages,
      blockComments,
      blockPosts,
    });
  },

  getFilteredPosts(
    ownerId: number,
    year: number,
    month: number,
    page: number,
    pageSize: number,
    direction: string
  ) {
    return http.get(
      `/posts/filtered?ownerId=${ownerId}&year=${year}&month=${month}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  getMessages(userId: number, connectionUserId: number) {
    return http.get(`/messages?userId=${userId}&connectionUserId=${connectionUserId}`);
  },

  createMessage(senderId: number, receiverId: number, text: string) {
    return http.post('/messages', { senderId, receiverId, text });
  },

  removeReplyComment(replyCommentId: number, ownerId: number) {
    return http.delete(`/reply-comments/${replyCommentId}?ownerId=${ownerId}`);
  },

  getReplyComments(commentId: number, page: number, pageSize: number, direction: string) {
    return http.get(
      `/reply-comments?commentId=${commentId}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

  createReplyComment(userId: number, commentId: number, text: string, ownerId: number) {
    return http.post('/reply-comments', { userId, commentId, text, ownerId });
  },

  removeLikeComment(commentId: number, userId: number) {
    return http.delete(`/comments/${commentId}/likes/${userId}`);
  },

  likeComment(commentId: number, userId: number) {
    return http.post(`/comments/${commentId}/likes`, { userId });
  },

  removeComment(commentId: number, ownerId: number) {
    return http.delete(`/comments/${commentId}?ownerId=${ownerId}`);
  },

  getComments(postId: number, page: number, pageSize: number, direction: string) {
    return http.get(
      `/comments?postId=${postId}&page=${page}&pageSize=${pageSize}&direction=${direction}`
    );
  },

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

  login(form: ILoginForm, rememberMe: boolean) {
    return http.post('/auth/login', {
      email: form.email.value,
      password: form.password.value,
      rememberMe,
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
