import Logo from './Logo';

import { Flex, Heading } from '@chakra-ui/react';
import { Link as RouterLink } from 'react-router-dom';

const LogoWithName = () => {
  return (
    <RouterLink to="/">
      <Flex align="center" p="0.25rem">
        <Logo height="35px" width="35px" />
        <Heading ml="0.5rem" fontSize="1.2rem" color="light.primary">
          Parana
        </Heading>
      </Flex>
    </RouterLink>
  );
};

export default LogoWithName;
