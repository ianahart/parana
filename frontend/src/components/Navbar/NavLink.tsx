import { ListItem, Box } from '@chakra-ui/react';
import { Link as RouterLink } from 'react-router-dom';
import { BsChevronRight } from 'react-icons/bs';

export interface INavLinkProps {
  link: string;
  linkText: string;
  mobile: boolean;
}

const NavLink = ({ link, linkText, mobile }: INavLinkProps) => {
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
      <RouterLink to={link}>{linkText}</RouterLink>
      {mobile && (
        <Box>
          <BsChevronRight />
        </Box>
      )}
    </ListItem>
  );
};

export default NavLink;
