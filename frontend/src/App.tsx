import { Box } from '@chakra-ui/react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import WithAxios from './util/WithAxios';
import HomeRoute from './routes/HomeRoute';
import RequireGuest from './components/Guard/RequireGuest';
import LoginRoute from './routes/LoginRoute';
import RegisterRoute from './routes/RegisterRoute';
import Footer from './components/Footer';
import Navbar from './components/Navbar';
import { useCallback, useContext } from 'react';
import { UserContext } from './context/user';
import { IUserContext } from './interfaces';
import ReviewRoute from './routes/ReviewRoute';
import AboutRoute from './routes/AboutRoute';
import { useEffect, useRef } from 'react';
import { retreiveTokens } from './util';
import { Client } from './util/client';

function App() {
  const shouldRun = useRef(true);
  const { updateUser, stowTokens } = useContext(UserContext) as IUserContext;

  const syncUser = useCallback(() => {
    Client.syncUser(retreiveTokens()?.token)
      .then((res) => {
        console.log(res);
        updateUser(res.data.data);
        stowTokens(retreiveTokens());
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  useEffect(() => {
    if (shouldRun.current && retreiveTokens()?.token) {
      syncUser();
      shouldRun.current = false;
    }
  }, [shouldRun.current, syncUser]);

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
