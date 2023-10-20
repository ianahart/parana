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
import AuthNavbar from './components/Navbar/AuthNavbar';
import ForgotPasswordRoute from './routes/ForgotPasswordRoute';
import ResetPasswordRoute from './routes/ResetPasswordRoute';
import RequireAuth from './components/Guard/RequireAuth';
import EditProfileRoute from './routes/EditProfileRoute';
import ProfileRoute from './routes/ProfileRoute';
import NotFoundRoute from './routes/NotFoundRoute';
import ExplorerRoute from './routes/ExplorerRoute';

function App() {
  const shouldRun = useRef(true);
  const { updateUser, stowTokens } = useContext(UserContext) as IUserContext;

  const syncUser = useCallback(() => {
    Client.syncUser(retreiveTokens()?.token)
      .then((res) => {
        updateUser(res.data.data);
        stowTokens(retreiveTokens());
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
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
        {user.loggedIn && <AuthNavbar />}
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
                path="/forgot-password"
                element={
                  <RequireGuest>
                    <ForgotPasswordRoute />
                  </RequireGuest>
                }
              />
              <Route
                path="/reset-password"
                element={
                  <RequireGuest>
                    <ResetPasswordRoute />
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
              <Route
                path="/explore"
                element={
                  <RequireAuth>
                    <ExplorerRoute />
                  </RequireAuth>
                }
              />
              <Route
                path="/profiles/:id/edit"
                element={
                  <RequireAuth>
                    <EditProfileRoute />
                  </RequireAuth>
                }
              />
              <Route
                path="/profiles/:id"
                element={
                  <RequireAuth>
                    <ProfileRoute />
                  </RequireAuth>
                }
              />
              <Route path="not-found" element={<NotFoundRoute />} />
              <Route path="*" element={<NotFoundRoute />} />
            </Routes>
          </WithAxios>
        </Box>
      </Router>
      <Footer />
    </Box>
  );
}

export default App;
