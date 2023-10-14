import { Flex, Button, Box } from '@chakra-ui/react';
import { useContext } from 'react';
import { AiOutlineLogout } from 'react-icons/ai';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
import { Client } from '../../util/client';

const LogoutButton = () => {
  const navigate = useNavigate();
  const { tokens, logout } = useContext(UserContext) as IUserContext;

  const logoutUser = () => {
    Client.logout(tokens.refreshToken)
      .then(() => {
        logout();
        navigate('/login');
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
      });
  };

  return (
    <Flex align="center" px="0.5rem" mx="0.5rem">
      <Box fontSize="1.2rem">
        <AiOutlineLogout />
      </Box>
      <Button
        onClick={logoutUser}
        mx="0.25rem"
        p="0"
        fontSize="0.9rem"
        _hover={{ background: 'transparent' }}
        background="transparent"
        color="text.secondary"
      >
        Log out
      </Button>
    </Flex>
  );
};

export default LogoutButton;
