import { createGradient, populateYears } from '../util';
import searchIconImage from '../assets/search-icon.png';
import calendarIconImage from '../assets/calendar.png';
import chatIconImage from '../assets/chat_message.png';

export const storyFormState = {
  color: { name: 'color', error: '', value: 'white', type: 'text' },
  file: { name: 'file', error: '', value: null, type: 'text' },
  text: { name: 'text', error: '', value: '', type: 'text' },
  duration: { name: 'duration', error: '', value: '10000', type: 'text' },
  background: {
    name: 'background',
    error: '',
    value: 'radial-gradient(circle, rgba(128,90,213,1) 29%, rgba(213,63,140,1) 100%)',
    type: 'text',
  },
  fontSize: { name: 'fontSize', error: '', value: '18px', type: 'text' },
  alignment: { name: 'alignment', error: '', value: 'center', type: 'text' },
};

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
  fullName: '',
  settingId: 0,
};

export const blockState = {
  posts: false,
  messages: false,
  comments: false,
};

export interface IConnectionRequest {
  avatarUrl: string;
  createdAt: Date;
  fullName: string;
  id: number;
  readableDate: string;
  userId: number;
  profileId: number;
}

export const connectionState = {
  loggedIn: false,
  avatarUrl: '',
  createdAt: new Date(),
  fullName: '',
  id: 0,
  readableDate: '',
  userId: 0,
  profileId: 0,
};

export const favoriteState = {
  id: 0,
  isFavorited: false,
};

export const tokenState = {
  refreshToken: '',
  token: '',
};

export const connectionPaginationState = {
  page: 0,
  pageSize: 2,
  totalPages: 0,
  direction: 'next',
  totalElements: 0,
};

export const storyPaginationState = {
  page: 0,
  pageSize: 4,
  totalPages: 0,
  direction: 'next',
  totalElements: 0,
};

export const notificationPaginationState = {
  page: 0,
  pageSize: 4,
  totalPages: 0,
  direction: 'next',
  totalElements: 0,
};

export const postPaginationState = {
  page: 0,
  pageSize: 4,
  totalPages: 0,
  direction: 'next',
  totalElements: 0,
};

export const commentPaginationState = {
  page: 0,
  pageSize: 2,
  totalPages: 0,
  direction: 'next',
  totalElements: 0,
};

export const blockPaginationState = {
  page: 0,
  pageSize: 2,
  totalPages: 0,
  direction: 'next',
  totalElements: 0,
};

export const replyCommentPaginationState = {
  page: -1,
  pageSize: 2,
  totalPages: 0,
  direction: 'next',
  totalElements: 0,
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

export const emailChangeState = {
  email: { name: 'email', error: '', value: '', type: 'email' },
  password: { name: 'password', error: '', value: '', type: 'password' },
};

export const passwordResetState = {
  password: { name: 'password', error: '', value: '', type: 'password' },
  confirmPassword: { name: 'confirmPassword', error: '', value: '', type: 'password' },
};

export const changePasswordState = {
  oldPassword: { name: 'oldPassword', error: '', value: '', type: 'password' },
  password: { name: 'password', error: '', value: '', type: 'password' },
  confirmPassword: { name: 'confirmPassword', error: '', value: '', type: 'password' },
};

export const editTeacherProfileFormState = {
  bio: { name: 'bio', error: '', value: '', type: 'text' },
  aboutLesson: { name: 'aboutLesson', error: '', value: '', type: 'text' },
  yearsSnowboarding: { name: 'yearsSnowboarding', error: '', value: '', type: 'text' },
  city: { name: 'city', error: '', value: '', type: 'text' },
  state: { name: 'state', error: '', value: 'Alabama', type: 'text' },
  stance: { name: 'stance', error: '', value: 'REGULAR', type: 'text' },
  homeMountain: { name: 'homeMountain', error: '', value: '', type: 'text' },
  terrain: { name: 'terrain', error: '', value: [], type: 'text' },
  firstLessonFree: { name: 'firstLessonFree', error: '', value: false, type: 'text' },
  perHour: { name: 'perHour', error: '', value: '', type: 'text' },
  travelUpTo: { name: 'travelUpTo', error: '', value: '', type: 'text' },
  tags: { name: 'tags', error: '', value: [], type: 'text' },
};

export const editUserProfileFormState = {
  bio: { name: 'bio', error: '', value: '', type: 'text' },
  yearsSnowboarding: { name: 'yearsSnowboarding', error: '', value: '', type: 'text' },
  city: { name: 'city', error: '', value: '', type: 'text' },
  state: { name: 'state', error: '', value: 'Alabama', type: 'text' },
  stance: { name: 'stance', error: '', value: 'REGULAR', type: 'text' },
  homeMountain: { name: 'homeMountain', error: '', value: '', type: 'text' },
  travelUpTo: { name: 'travelUpTo', error: '', value: '', type: 'text' },
  terrain: { name: 'terrain', error: '', value: [], type: 'text' },
};

export const statesState = [
  'Alabama',
  'Alaska',
  'Arizona',
  'Arkansas',
  'California',
  'Colorado',
  'Connecticut',
  'Delaware',
  'District Of Columbia',
  'Florida',
  'Georgia',
  'Guam',
  'Hawaii',
  'Idaho',
  'Illinois',
  'Indiana',
  'Iowa',
  'Kansas',
  'Kentucky',
  'Louisiana',
  'Maine',
  'Maryland',
  'Massachusetts',
  'Michigan',
  'Minnesota',
  'Mississippi',
  'Missouri',
  'Montana',
  'Nebraska',
  'Nevada',
  'New Hampshire',
  'New Jersey',
  'New Mexico',
  'New York',
  'North Carolina',
  'North Dakota',
  'Ohio',
  'Oklahoma',
  'Oregon',
  'Pennsylvania',
  'Rhode Island',
  'South Carolina',
  'South Dakota',
  'Tennessee',
  'Texas',
  'Utah',
  'Vermont',
  'Virginia',
  'Washington',
  'West Virginia',
  'Wisconsin',
  'Wyoming',
];

export const terrainState = [
  'Groomed',
  'Moguls',
  'Tree Skiing',
  'Powder',
  'Steeps',
  'Backcountry',
  'Terrain Parks',
];

export const profileState = {
  avatarUrl: '',
  bio: '',
  city: '',
  firstLessonFree: false,
  homeMountain: '',
  id: 0,
  perHour: '',
  role: '',
  stance: '',
  state: '',
  tags: '',
  terrain: '',
  travelUpTo: '',
  userId: 0,
  yearsSnowboarding: 0,
  fullName: '',
  firstName: '',
  aboutLesson: '',
};

export const reccomendationWordState = [
  { id: 1, word: 'Acclaimed' },
  { id: 2, word: 'Accommodative' },
  { id: 3, word: 'Accessible' },
  { id: 4, word: 'Awesome' },
  { id: 5, word: 'Centered' },
  { id: 6, word: 'Clever' },
  { id: 7, word: 'Communicative' },
  { id: 8, word: 'Dependable' },
  { id: 9, word: 'Enthusiatic' },
  { id: 10, word: 'Genuine' },
  { id: 11, word: 'Insightful' },
  { id: 12, word: 'Knowledgeable' },
  { id: 13, word: 'Organized' },
  { id: 14, word: 'Outstanding' },
  { id: 15, word: 'Reccomendable' },
  { id: 16, word: 'Reliable' },
  { id: 17, word: 'Upbeat' },
  { id: 18, word: 'Well-read' },
];

export const yearState = populateYears(new Date().getFullYear());

export const monthState = [
  { id: 1, name: 'Jan', value: 0 },
  { id: 2, name: 'Feb', value: 1 },
  { id: 3, name: 'Mar', value: 2 },
  { id: 4, name: 'Apr', value: 3 },
  { id: 5, name: 'May', value: 4 },
  { id: 6, name: 'Jun', value: 5 },
  { id: 7, name: 'Jul', value: 6 },
  { id: 8, name: 'Aug', value: 7 },
  { id: 9, name: 'Sep', value: 8 },
  { id: 10, name: 'Oct', value: 9 },
  { id: 11, name: 'Nov', value: 10 },
  { id: 12, name: 'Dec', value: 11 },
];

export const textColors = [
  { id: 1, value: 'black' },
  { id: 2, value: 'white' },
  { id: 3, value: 'pink' },
  { id: 4, value: 'yellow' },
  { id: 5, value: 'red' },
  { id: 6, value: 'green' },
  { id: 7, value: 'blue' },
  { id: 8, value: 'orange' },
  { id: 9, value: 'purple' },
];

const createBackgroundColors = (size: number) => {
  const backgroundColors = [];
  for (let i = 0; i < size; i++) {
    backgroundColors.push(createGradient());
  }
  return backgroundColors;
};

export const backgroundColors = createBackgroundColors(8);

export const connectionStoryState = {
  avatarUrl: '',
  fullName: '',
  userId: 0,
  stories: [],
};

export const steps = [
  {
    id: 1,
    name: 'Search',
    image: searchIconImage,
    text: 'View teacher profiles and contact a teacher according to your criteria including rates, terrain in common, and reviews.',
  },
  {
    id: 2,
    name: 'Reach out',
    image: chatIconImage,
    text: 'It is quick and easy, teachers will get back to you within hours.',
  },
  {
    id: 3,
    name: 'Organize',
    image: calendarIconImage,
    text: 'Easily communicate and schedule your lessons from your private messages with your teacher.',
  },
];
