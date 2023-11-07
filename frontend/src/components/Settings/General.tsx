import { Box } from '@chakra-ui/react';
import DeleteAccountForm from './DeleteAccountForm';
import Header from './Header';
import { IoSettingsOutline } from 'react-icons/io5';
const General = () => {
  return (
    <Box color="text.secondary">
      <Header heading="General" icon={<IoSettingsOutline />} />
      <DeleteAccountForm />
    </Box>
  );
};

export default General;
