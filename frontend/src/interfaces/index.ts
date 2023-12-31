type TFile = File | null;

export interface IStep {
  id: number;
  name: string;
  image: string;
  text: string;
}

export interface ITopReview {
  avatarUrl: string;
  averageRating: number;
  fullName: string;
  numReviews: number;
  userId: number;
}

export interface ILatestReview {
  id: number;
  avatarUrl: string;
  fullName: string;
  rating: number;
  review: string;
  userId: number;
}

export interface IStory {
  alignment: string;
  avatarUrl: string;
  background: string;
  color: string;
  duration: string;
  expiresIn: number;
  fontSize: string;
  fullName: string;
  id: number;
  photoUrl: string;
  text: string;
  userId: number;
  createdAt: Date;
}

export interface IConnectionStory {
  userId: number;
  fullName: string;
  avatarUrl: string;
  stories: IStory[];
}

export interface IStoryForm {
  color: IFormField;
  file: { name: string; error: string; value: TFile; type: string };
  text: IFormField;
  duration: IFormField;
  background: IFormField;
  fontSize: IFormField;
  alignment: IFormField;
}

export interface INotification {
  id: number;
  receiverId: number;
  senderId: number;
  text: string;
  fullName: string;
  avatarUrl: string;
  createdAt: Date;
}

export interface IPrivacy {
  id: number;
  blockedUserId: number;
  avatarUrl: string;
  fullName: string;
  messages: boolean;
  posts: boolean;
  comments: boolean;
  updatedAt: Date;
  readableDate: string;
}

export interface IBlock {
  messages: boolean;
  posts: boolean;
  comments: boolean;
}

export interface IMinimalUser {
  userId: number;
  fullName: string;
  avatarUrl: string;
}

export interface IComment {
  id: number;
  userId: number;
  postId: number;
  fullName: string;
  avatarUrl: string;
  text: string;
  createdAt: Date;
  likesCount: number;
  currentUserHasLikedComment: boolean;
  replyCommentsExist: boolean;
}

export interface IMessage {
  id: number;
  createdAt: Date;
  senderUserId: number;
  receiverUserId: number;
  receiverFullName: string;
  senderFullName: string;
  receiverAvatarUrl: string;
  senderAvatarUrl: string;
  readableDate: string;
  text: string;
}

export interface IReplyComment {
  avatarUrl: string;
  createdAt: Date;
  fullName: string;
  id: number;
  text: string;
  userId: number;
}

export interface IPost {
  createdAt: Date;
  fileUrl: string;
  gif: string;
  id: number;
  isEdited: boolean;
  ownerId: number;
  readableDate: string;
  text: string;
  authorFullName: string;
  authorAvatarUrl: string;
  authorId: number;
  ownerProfileId: number;
  ownerFullName: string;
  currentUserHasLikedPost: boolean;
  likesCount: number;
  comment: IComment;
  commentCount: number;
}

export interface IProfile {
  avatarUrl: string;
  bio: string;
  city: string;
  firstLessonFree: boolean;
  homeMountain: string;
  id: number;
  perHour: string;
  role: string;
  stance: string;
  state: string;
  tags: string;
  terrain: string;
  travelUpTo: string;
  userId: number;
  yearsSnowboarding: number;
  fullName: string;
  firstName: string;
  aboutLesson: string;
  isNewTeacher?: boolean;
}

export interface IFavorite {
  id: number;
  isFavorited: boolean;
}

export interface ISuggestion {
  avatarUrl: string;
  fullName: string;
  profileId: number;
  teacherId: number;
  terrain: string;
  terrainInCommon: string[];
}

export interface ISearch {
  id: number;
  profileId: number;
  avatarUrl: string;
  fullName: string;
  firstName: string;
  city: string;
  state: string;
}

export interface IRecentSearch {
  recentSearchId: number;
  term: string;
}

export interface IRecommendation {
  authorId: number;
  avatarUrl: string;
  createdAt: Date;
  date: string;
  firstName: string;
  fullName: string;
  id: number;
  recommendation: string;
  teacherId: number;
  words: string;
}

export interface IRecommendationWord {
  id: number;
  word: string;
}

export interface IReview {
  avatarUrl: string;
  firstName: string;
  id: number;
  isEdited: boolean;
  rating: number;
  review: string;
  userId: number;
  fullName: string;
}

export interface IConnectionRequest {
  avatarUrl: string;
  createdAt: Date;
  fullName: string;
  id: number;
  readableDate: string;
  userId: number;
  profileId: number;
}

export interface IConnection extends IConnectionRequest {
  loggedIn: boolean;
}

export interface IConnectionFavorite {
  id: number;
  teacherId: number;
  profileId: number;
  avatarUrl: string;
  fullName: string;
  createdAt: Date;
  readableDate: string;
  isConnected: boolean;
}

export interface ITeacher {
  avatarUrl: string;
  bio: string;
  city: string;
  firstName: string;
  perHour: string;
  profileId: number;
  state: string;
  userId: number;
  firstLessonFree: boolean;
  isNewTeacher: boolean;
  favorite: IFavorite;
}

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
  travelUpTo: IFormField;
  terrain: { name: string; error: string; value: string[]; type: string };
}

export interface IEditTeacherProfileForm {
  bio: IFormField;
  aboutLesson: IFormField;
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

export interface IChangePasswordForm {
  oldPassword: IFormField;
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

export interface IEmailChangeForm {
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
  settingId: number;
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
