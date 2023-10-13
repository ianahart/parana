export const userState = {
  abbreviation: '',
  email: '',
  id: 0,
  lastName: '',
  firstName: '',
  loggedIn: false,
  role: '',
  profileId: 0,
  avatarUrl: '',
};

export const tokenState = {
  refreshToken: '',
  token: '',
};

export const registerFormState = {
  firstName: { name: 'firstName', error: '', value: '', type: 'text' },
  lastName: { name: 'lastName', error: '', value: '', type: 'text' },
  email: { name: 'email', error: '', value: '', type: 'email' },
  password: { name: 'password', error: '', value: '', type: 'password' },
  confirmPassword: { name: 'confirmPassword', error: '', value: '', type: 'password' },
};

export const loginFormState = {
  email: { name: 'email', error: '', value: '', type: 'email' },
  password: { name: 'password', error: '', value: '', type: 'password' },
};
