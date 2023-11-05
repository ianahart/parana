import { Box } from '@chakra-ui/react';
import Header from './Header';
import { AiOutlineLock } from 'react-icons/ai';
import ChangePasswordForm from './ChangePasswordForm';
const Security = () => {
  return (
    <Box color="text.secondary">
      <Header heading="Security & Login" icon={<AiOutlineLock />} />
      <ChangePasswordForm />
    </Box>
  );
};

export default Security;
