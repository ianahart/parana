import { ListItem, Box, Flex, Text } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';
import { BsChevronRight } from 'react-icons/bs';
import { useContext } from 'react';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';

export interface INavLinkProps {
  link: string;
  linkText: string;
  mobile: boolean;
  icon?: React.ReactNode;
  closeUserMenu: () => void;
}

const NavLink = ({
  link,
  linkText,
  mobile,
  icon = undefined,
  closeUserMenu,
}: INavLinkProps) => {
  const navigate = useNavigate();
  const { user } = useContext(UserContext) as IUserContext;

  const handleOnClick = () => {
    navigate(link);
    closeUserMenu();
  };

  return (
    <ListItem
      onClick={handleOnClick}
      cursor="pointer"
      _hover={{
        opacity: 0.7,
        background: mobile ? 'blackAlpha.500' : 'unset',
      }}
      p={mobile ? '0.5rem' : 0}
      borderRadius={4}
      transition="ease-in-out 0.5s opacity"
      display="flex"
      alignItems="center"
      justifyContent="space-between"
      mx="0.5rem"
      my={['0.5rem', '0.5rem', 0]}
    >
      <Flex>
        {icon !== undefined && (
          <Box mr="0.25rem" fontSize="1.2rem">
            {icon}
          </Box>
        )}
        <Text>{linkText}</Text>
      </Flex>
      {mobile && (
        <Box>
          <BsChevronRight />
        </Box>
      )}
    </ListItem>
  );
};

export default NavLink;
