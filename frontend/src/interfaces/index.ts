export interface IFormField {
  name: string;
  value: string;
  error: string;
  type: string;
}

export interface IRegisterForm {
  firstName: IFormField;
  lastName: IFormField;
  email: IFormField;
  password: IFormField;
  confirmPassword: IFormField;
}

export interface IUser {
  abbreviation: string;
  email: string;
  firstName: string;
  id: number;
  lastName: string;
  loggedIn: boolean;
  role: string;
  profileId: number;
  avatarUrl: string;
}

export interface ITokens {
  refreshToken: string;
  token: string;
}

export interface IUserContext {
  tokens: ITokens;
  user: IUser;
  stowTokens: (tokens: ITokens) => void;
  updateUser: (user: IUser) => void;
  logout: () => void;
}