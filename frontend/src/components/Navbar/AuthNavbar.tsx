import { Box, Flex, ListItem, UnorderedList } from '@chakra-ui/react';
import LogoWithName from '../Logo/LogoWithName';
import { Link as RouterLink } from 'react-router-dom';
import Searchbar from './Searchbar';
import { useContext, useState } from 'react';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
import UserAvatar from '../Shared/UserAvatar';
import UserMenu from './UserMenu';

const AuthNavbar = () => {
  const { user } = useContext(UserContext) as IUserContext;
  const [userMenuShowing, setUserMenuShowing] = useState(false);

  const openUserMenu = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    setUserMenuShowing(true);
  };

  const closeUserMenu = () => {
    setUserMenuShowing(false);
  };

  return (
    <Box p="0.25rem">
      <Flex position="relative" align="center" justify="space-between" as="nav">
        <LogoWithName />
        <Flex align="center">
          <UnorderedList
            display={['none', 'none', 'block']}
            listStyleType="none"
            color="light.primary"
            mx="1rem"
          >
            <ListItem>
              <RouterLink to="/explore">Explore</RouterLink>
            </ListItem>
          </UnorderedList>
          <Searchbar />
          <Box onClick={openUserMenu} cursor="pointer">
            <UserAvatar
              avatarUrl={user.avatarUrl}
              width="50px"
              height="50px"
              fullName={user.fullName}
            />
          </Box>
          {userMenuShowing && <UserMenu closeUserMenu={closeUserMenu} />}
        </Flex>
      </Flex>
    </Box>
  );
};

export default AuthNavbar;
