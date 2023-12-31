export const retreiveTokens = () => {
  const storage = localStorage.getItem('tokens');
  let tokens;
  if (storage) {
    tokens = JSON.parse(storage);
  }
  return tokens;
};

export const slugifyTitle = (title: string) => {
  return title
    .split(' ')
    .map((word, index) => {
      if (index > 0) {
        return '-' + word;
      } else {
        return word;
      }
    })
    .join('');
};

export const slugify = (firstName: string, lastName: string) => {
  return (firstName + lastName).toLowerCase();
};

export const abbreviate = (fullName: string) => {
  const [firstName, lastName] = fullName.split(' ');
  if (firstName.length && lastName.length) {
    return firstName.slice(0, 1) + lastName.slice(0, 1);
  }
};

export const capitalize = (value: string) => {
  return value.slice(0, 1).toUpperCase() + value.slice(1).toLowerCase();
};

export const populateYears = (currentYear: number) => {
  const years: { id: number; name: number; value: number }[] = [];
  const earliestYear = 2000;
  let maxId = currentYear - earliestYear;
  while (currentYear >= earliestYear) {
    years.push({ id: maxId, name: currentYear, value: currentYear });
    currentYear -= 1;
    maxId -= 1;
  }
  return years;
};

const generateHexString = (limit: number) => {
  const hexValues = '0123456789abcdef';
  let hexString = '';
  for (let i = 0; i < limit; i++) {
    hexString += hexValues.charAt(Math.floor(Math.random() * hexValues.length));
  }
  return `#${hexString}`;
};

const generateDegree = (maxDegree: number) => {
  return Math.floor(Math.random() * maxDegree);
};

export const createGradient = () => {
  return `linear-gradient(${generateDegree(360)}deg, ${generateHexString(
    6
  )}, ${generateHexString(6)});`;
};
