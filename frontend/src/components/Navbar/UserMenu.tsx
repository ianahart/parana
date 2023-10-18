import { Box, Flex, useOutsideClick, UnorderedList } from '@chakra-ui/react';
import { useContext, useRef } from 'react';
import UserMenuHeader from './UserMenuHeader';
import NavLink from './NavLink';
import { AiOutlineEdit, AiOutlineUser } from 'react-icons/ai';
import { MdOutlineFeed } from 'react-icons/md';
import { HiOutlineUsers } from 'react-icons/hi';
import { IoSettingsOutline } from 'react-icons/io5';
import LogoutButton from '../Auth/LogoutButton';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';

interface IUserMenuProps {
  closeUserMenu: () => void;
}

const UserMenu = ({ closeUserMenu }: IUserMenuProps) => {
  const { user } = useContext(UserContext) as IUserContext;

  const menuRef = useRef<HTMLDivElement>(null);

  useOutsideClick({
    ref: menuRef,
    handler: () => {
      closeUserMenu();
    },
  });

  return (
    <Box
      ref={menuRef}
      className="fade-in"
      position="absolute"
      top="60px"
      borderRadius={8}
      boxShadow=" rgba(0, 0, 0, 0.16) 0px 1px 4px"
      zIndex={10}
      right={['0', '0', '30px']}
      bg="primary.dark"
      width={['95%', '300px', '300px']}
      minH="600px"
    >
      <Flex p="0.25rem" justify="flex-start" my="1.5rem">
        <UserMenuHeader />
      </Flex>
      <Box p="0.25rem">
        <Box borderBottom="1px solid" borderColor="border.primary"></Box>
        <UnorderedList color="light.primary" margin="0" fontSize="0.9rem">
          <NavLink
            icon={<AiOutlineUser />}
            link="/profile"
            linkText="Profile"
            mobile={true}
          />
          <NavLink
            icon={<AiOutlineEdit />}
            link={`/profiles/${user.id}/edit`}
            linkText="Edit Profile"
            mobile={true}
          />
          <Box my="1rem" borderBottom="1px solid" borderColor="border.primary"></Box>
          <NavLink icon={<MdOutlineFeed />} link="/feed" linkText="Feed" mobile={true} />
          <NavLink
            icon={<HiOutlineUsers />}
            link="/connections"
            linkText="Connections"
            mobile={true}
          />
          <Box my="1rem" borderBottom="1px solid" borderColor="border.primary"></Box>
          <NavLink
            icon={<IoSettingsOutline />}
            link="/settings"
            linkText="Settings"
            mobile={true}
          />
          <Box my="1rem" borderBottom="1px solid" borderColor="border.primary"></Box>
          <LogoutButton />
        </UnorderedList>
      </Box>
    </Box>
  );
};

export default UserMenu;
