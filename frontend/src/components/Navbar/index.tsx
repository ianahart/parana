import { Flex, Box, UnorderedList } from '@chakra-ui/react';
import { RxHamburgerMenu } from 'react-icons/rx';
import { AiOutlineClose } from 'react-icons/ai';
import NavLink from './NavLink';
import { useState } from 'react';
import LogoWithName from '../Logo/LogoWithName';
import Logo from '../Logo/Logo';

const Navbar = () => {
  const [mobile, setMobile] = useState(false);

  const toggleNav = () => {
    setMobile((prevState) => !prevState);
  };

  return (
    <Flex justifyContent="space-between" p="0.25rem">
      <LogoWithName />
      <Box
        display="flex"
        flexDirection={['column-reverse', 'column-reverse', 'row']}
        alignItems={['unset', 'unset', 'center']}
        justifyContent={['flex-end']}
        flexGrow={2}
        as="nav"
        p="0.5rem"
        color="light.primary"
        fontWeight="bold"
      >
        <UnorderedList
          className={mobile ? 'fade-in' : ''}
          width={['95%', '80%', '100%']}
          minH={['600px', '600px', 'unset']}
          top={['50px', '50px', '0']}
          right={['30px', '30px', '0']}
          borderRadius="8px"
          boxShadow={[
            'rgba(0, 0, 0, 0.16) 0px 1px 4px',
            'rgba(0,0,0,0.16) 0px 1px 4px',
            'none',
          ]}
          background={['primary.dark', 'primary.dark', 'unset']}
          position={['absolute', 'absolute', 'relative']}
          display={[mobile ? 'flex' : 'none', mobile ? 'flex' : 'none', 'flex']}
          flexDir={['column', 'column', 'row']}
          justifyContent={['unset', 'unset', 'flex-end']}
          listStyleType="none"
        >
          {mobile && (
            <Box p="0.25rem" height="45px" bg="text.primary" borderTopRadius={8}>
              <Logo width="35px" height="35px" />
            </Box>
          )}
          <NavLink mobile={mobile} link="/" linkText="Home" />
          <NavLink mobile={mobile} link="/login" linkText="Login" />
          <NavLink mobile={mobile} link="/register" linkText="Create Account" />
          <NavLink mobile={mobile} link="/Reviews" linkText="Reviews" />
          <NavLink mobile={mobile} link="/About" linkText="About" />
        </UnorderedList>
        <Box
          display={['flex', 'flex', 'none']}
          onClick={toggleNav}
          borderRadius={8}
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          marginLeft="auto"
          background="text.primary"
          width="35px"
          height="35px"
          color="light.primary"
          fontSize="1.3rem"
          cursor="pointer"
        >
          {mobile ? <AiOutlineClose /> : <RxHamburgerMenu />}
        </Box>
      </Box>
    </Flex>
  );
};

export default Navbar;
