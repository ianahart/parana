import { ListItem, Box, Flex } from '@chakra-ui/react';
import { Link as RouterLink } from 'react-router-dom';
import { BsChevronRight } from 'react-icons/bs';

export interface INavLinkProps {
  link: string;
  linkText: string;
  mobile: boolean;
  icon?: React.ReactNode;
}

const NavLink = ({ link, linkText, mobile, icon = undefined }: INavLinkProps) => {
  return (
    <ListItem
      cursor="pointer"
      _hover={{
        opacity: 0.7,
        background: mobile ? 'primary.dark' : 'unset',
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
        <RouterLink to={link}>{linkText}</RouterLink>
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
