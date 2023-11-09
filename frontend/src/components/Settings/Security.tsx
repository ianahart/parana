import { Box } from '@chakra-ui/react';
import Header from './Header';
import { AiOutlineLock } from 'react-icons/ai';
import ChangePasswordForm from './ChangePasswordForm';
import ChangeEmailForm from './ChangeEmailForm';
import { useRef, useState, useEffect, useContext } from 'react';
import { Client } from '../../util/client';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
import ChangeRememberMeForm from './ChangeRememberMeForm';
import { retreiveTokens } from '../../util';
const Security = () => {
  const shouldRun = useRef(true);
  const { user, stowTokens } = useContext(UserContext) as IUserContext;
  const [passwordLastUpdated, setPasswordLastUpdated] = useState('');
  const [emailLastUpdated, setEmailLastUpdated] = useState('');
  const [rememberMe, setRememberMe] = useState(false);
  const [settingId, setSettingId] = useState(0);

  const handleSetRememberMe = (rememberMe: boolean) => {
    setRememberMe(rememberMe);
    Client.updateRememberMe(settingId, rememberMe)
      .then((res) => {
        const { data } = res.data;
        if (data.length) {
          const tokens = retreiveTokens();
          tokens.token = data;
          stowTokens(tokens);
          localStorage.setItem('tokens', JSON.stringify(tokens));
        }
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const getSettings = (settingId: number) => {
    Client.getSettings(settingId)
      .then((res) => {
        const { id, updatedFormattedDate, emailUpdatedFormattedDate, rememberMe } =
          res.data.data;
        setEmailLastUpdated(emailUpdatedFormattedDate ? emailUpdatedFormattedDate : '');
        setPasswordLastUpdated(updatedFormattedDate ? updatedFormattedDate : '');
        setRememberMe(rememberMe);
        setSettingId(id);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && user.settingId !== 0) {
      shouldRun.current = false;
      getSettings(user.settingId);
    }
  }, [shouldRun.current, user.settingId]);

  return (
    <Box color="text.secondary">
      <Header heading="Security & Login" icon={<AiOutlineLock />} />
      <ChangeRememberMeForm
        handleSetRememberMe={handleSetRememberMe}
        rememberMe={rememberMe}
      />
      <ChangeEmailForm emailLastUpdated={emailLastUpdated} />
      <ChangePasswordForm passwordLastUpdated={passwordLastUpdated} />
    </Box>
  );
};

export default Security;
