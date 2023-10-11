import { Box } from '@chakra-ui/react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import WithAxios from './util/WithAxios';
import HomeRoute from './routes/HomeRoute';
import RequireGuest from './components/Guard/RequireGuest';
import LoginRoute from './routes/LoginRoute';
import RegisterRoute from './routes/RegisterRoute';

function App() {
  return (
    <Box className="app">
      <Router>
        <Box minH="100vh">
          <WithAxios>
            <Routes>
              <Route index element={<HomeRoute />} />
              <Route
                path="/login"
                element={
                  <RequireGuest>
                    <LoginRoute />
                  </RequireGuest>
                }
              />
              <Route
                path="/register"
                element={
                  <RequireGuest>
                    <RegisterRoute />
                  </RequireGuest>
                }
              />
            </Routes>
          </WithAxios>
        </Box>
      </Router>
    </Box>
  );
}

export default App;
