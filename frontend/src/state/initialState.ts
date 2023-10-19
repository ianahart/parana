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

export const passwordResetState = {
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
