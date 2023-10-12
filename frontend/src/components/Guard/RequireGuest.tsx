import { useLocation, Navigate } from 'react-router-dom';
import { retreiveTokens } from '../../util';
import { useContext } from 'react';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
interface Props {
  children: JSX.Element;
}

const RequireGuest: React.FC<Props> = ({ children }) => {
  const { user } = useContext(UserContext) as IUserContext;

  const location = useLocation();
  const guestRoutes = ['/', '/login', '/register', '/forgot-password', '/reset-password'];
  const storage = retreiveTokens();
  if (storage === undefined && guestRoutes.includes(location.pathname)) {
    return children;
  } else {
    if (user.id !== 0) {
      return <Navigate to="/" replace state={{ path: location.pathname }} />;
    }
  }
};

export default RequireGuest;
