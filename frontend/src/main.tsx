import ReactDOM from 'react-dom/client';
import App from './App.tsx';
import './index.css';
import { ChakraProvider } from '@chakra-ui/react';
import { theme } from './theme/theme.ts';
import UserContextProvider from './context/user.tsx';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <ChakraProvider theme={theme}>
    <UserContextProvider>
      <App />
    </UserContextProvider>
  </ChakraProvider>
);
