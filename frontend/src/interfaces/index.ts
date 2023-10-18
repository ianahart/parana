export interface IFormField {
  name: string;
  value: string;
  error: string;
  type: string;
}

export interface IEditUserProfileForm {
  bio: IFormField;
  yearsSnowboarding: IFormField;
  city: IFormField;
  state: IFormField;
  stance: IFormField;
  homeMountain: IFormField;
  perHour: IFormField;
  travelUpTo: IFormField;
  terrain: { name: string; error: string; value: string[]; type: 'text' };
}

export interface IEditTeacherProfileForm {
  bio: IFormField;
  yearsSnowboarding: IFormField;
  city: IFormField;
  state: IFormField;
  stance: IFormField;
  homeMountain: IFormField;
  terrain: { name: string; error: string; value: string[]; type: string };
  firstLessonFree: { name: string; error: string; value: boolean; type: string };
  perHour: IFormField;
  travelUpTo: IFormField;
  tags: { name: string; error: string; value: string[]; type: string };
}

export interface IPasswordResetForm {
  password: IFormField;
  confirmPassword: IFormField;
}

export interface IRegisterForm {
  firstName: IFormField;
  lastName: IFormField;
  email: IFormField;
  password: IFormField;
  confirmPassword: IFormField;
}

export interface ILoginForm {
  email: IFormField;
  password: IFormField;
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
  fullName: string;
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
