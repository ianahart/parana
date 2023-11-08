import { Box } from '@chakra-ui/react';
import { useContext } from 'react';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
import BlockUsers from './BlockUsers';
import Header from './Header';
import { BsShieldLock } from 'react-icons/bs';

const Privacy = () => {
  const { user } = useContext(UserContext) as IUserContext;
  return (
    <Box color="text.secondary">
      <Header heading="User Privacy" icon={<BsShieldLock />} />
      {user.role === 'TEACHER' && (
        <Box pt="3rem">
          <BlockUsers />
        </Box>
      )}
    </Box>
  );
};
export default Privacy;
