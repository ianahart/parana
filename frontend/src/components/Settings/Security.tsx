import { Box } from '@chakra-ui/react';
import Header from './Header';
import { AiOutlineLock } from 'react-icons/ai';
import ChangePasswordForm from './ChangePasswordForm';
import ChangeEmailForm from './ChangeEmailForm';
import { useRef, useState, useEffect, useContext } from 'react';
import { Client } from '../../util/client';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
const Security = () => {
  const shouldRun = useRef(true);
  const { user } = useContext(UserContext) as IUserContext;
  const [passwordLastUpdated, setPasswordLastUpdated] = useState('');
  const [emailLastUpdated, setEmailLastUpdated] = useState('');

  const getSettings = (settingId: number) => {
    Client.getSettings(settingId)
      .then((res) => {
        console.log(res);
        const { updatedFormattedDate, emailUpdatedFormattedDate } = res.data.data;
        setEmailLastUpdated(emailUpdatedFormattedDate ? emailUpdatedFormattedDate : '');
        setPasswordLastUpdated(updatedFormattedDate ? updatedFormattedDate : '');
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
      <ChangeEmailForm emailLastUpdated={emailLastUpdated} />
      <ChangePasswordForm passwordLastUpdated={passwordLastUpdated} />
    </Box>
  );
};

export default Security;
