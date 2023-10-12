import { Box } from '@chakra-ui/react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import WithAxios from './util/WithAxios';
import HomeRoute from './routes/HomeRoute';
import RequireGuest from './components/Guard/RequireGuest';
import LoginRoute from './routes/LoginRoute';
import RegisterRoute from './routes/RegisterRoute';
import Footer from './components/Footer';
import Navbar from './components/Navbar';
import { useContext } from 'react';
import { UserContext } from './context/user';
import { IUserContext } from './interfaces';
import ReviewRoute from './routes/ReviewRoute';
import AboutRoute from './routes/AboutRoute';

function App() {
  const { user } = useContext(UserContext) as IUserContext;
  return (
    <Box className="app">
      <Router>
        {!user.loggedIn && <Navbar />}
        <Box className="content" minH="100vh">
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
              <Route
                path="/reviews"
                element={
                  <RequireGuest>
                    <ReviewRoute />
                  </RequireGuest>
                }
              />
              <Route
                path="/about"
                element={
                  <RequireGuest>
                    <AboutRoute />
                  </RequireGuest>
                }
              />
            </Routes>
          </WithAxios>
        </Box>
      </Router>
      <Footer />
    </Box>
  );
}

export default App;
