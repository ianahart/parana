import { Box, Button } from '@chakra-ui/react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import WithAxios from './util/WithAxios';
import HomeRoute from './routes/HomeRoute';
import RequireGuest from './components/Guard/RequireGuest';
import LoginRoute from './routes/LoginRoute';
import RegisterRoute from './routes/RegisterRoute';
import Footer from './components/Footer';
import Navbar from './components/Navbar';
import { useCallback, useContext, useState } from 'react';
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
import ConnectionRoute from './routes/ConnectionRoute';
import ConnectionRequests from './components/Connection/ConnectionRequests';
import Connections from './components/Connection/Connections';
import Favorites from './components/Connection/Favorites';
import Suggestions from './components/Connection/Suggestions';
import { BsChatDots } from 'react-icons/bs';
import MessagesConnectionList from './components/Messages/MessagesConnectionList';
import { AiOutlineClose } from 'react-icons/ai';
import SettingsRoute from './routes/SettingsRoute';
import General from './components/Settings/General';
import Security from './components/Settings/Security';
import Privacy from './components/Settings/Privacy';
import Notifications from './components/Settings/Notifications';
import FeedRoute from './routes/FeedRoute';
import StoriesRoute from './routes/StoriesRoute';
import CreateStory from './components/Stories/CreateStory';
import Stories from './components/Stories/Stories';

function App() {
  const shouldRun = useRef(true);
  const { updateUser, stowTokens } = useContext(UserContext) as IUserContext;
  const { user } = useContext(UserContext) as IUserContext;
  const [messagesOpen, setMessagesOpen] = useState(false);

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

  return (
    <Box className="app">
      <Router>
        {!user.loggedIn && <Navbar />}
        {user.loggedIn && <AuthNavbar />}
        <Box className="content" minH="100vh">
          {user.loggedIn && (
            <>
              <Box
                transform="rotate(-90deg)"
                onClick={() => setMessagesOpen((prevState) => !prevState)}
                position="fixed"
                zIndex={20}
                top="50%"
                right="-40px"
              >
                <Button colorScheme="blackAlpha">
                  <Box mr="0.25rem">
                    {messagesOpen ? <AiOutlineClose /> : <BsChatDots />}
                  </Box>
                  Messages
                </Button>
              </Box>
              {user.loggedIn && messagesOpen && <MessagesConnectionList />}
            </>
          )}
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
                path="/stories"
                element={
                  <RequireAuth>
                    <StoriesRoute />
                  </RequireAuth>
                }
              >
                <Route
                  path="create"
                  element={
                    <RequireAuth>
                      <CreateStory />
                    </RequireAuth>
                  }
                />
                <Route
                  path="all"
                  element={
                    <RequireAuth>
                      <Stories />
                    </RequireAuth>
                  }
                />
              </Route>
              <Route
                path="/feed"
                element={
                  <RequireAuth>
                    <FeedRoute />
                  </RequireAuth>
                }
              />
              <Route
                path="/connections"
                element={
                  <RequireAuth>
                    <ConnectionRoute />
                  </RequireAuth>
                }
              >
                <Route
                  path="requests"
                  element={
                    <RequireAuth>
                      <ConnectionRequests />
                    </RequireAuth>
                  }
                />

                <Route
                  path="all"
                  element={
                    <RequireAuth>
                      <Connections />
                    </RequireAuth>
                  }
                />
                <Route
                  path="favorites"
                  element={
                    <RequireAuth>
                      <Favorites />
                    </RequireAuth>
                  }
                />
                <Route
                  path="suggestions"
                  element={
                    <RequireAuth>
                      <Suggestions />
                    </RequireAuth>
                  }
                />
              </Route>
              <Route
                path="/settings"
                element={
                  <RequireAuth>
                    <SettingsRoute />
                  </RequireAuth>
                }
              >
                <Route
                  path="general"
                  element={
                    <RequireAuth>
                      <General />
                    </RequireAuth>
                  }
                />
                <Route
                  path="security"
                  element={
                    <RequireAuth>
                      <Security />
                    </RequireAuth>
                  }
                />
                <Route
                  path="privacy"
                  element={
                    <RequireAuth>
                      <Privacy />
                    </RequireAuth>
                  }
                />
                <Route
                  path="notifications"
                  element={
                    <RequireAuth>
                      <Notifications />
                    </RequireAuth>
                  }
                />
              </Route>
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
