import { Box, Image, Heading, Text, Flex } from '@chakra-ui/react';
import notFoundImage from '../assets/notfound.svg';

const NotFoundRoute = () => {
  return (
    <Box color="text.secondary">
      <Box as="main">
        <Image src={notFoundImage} alt="404 page picture" />
      </Box>
      <Flex mb="1.5rem" flexDir="column" align="center" justify="center" as="header">
        <Heading mb="0.25rem" as="h1">
          We've lost this page
        </Heading>
        <Text color="text.primary">
          Sorry, the page you are looking for doesn't exist or has been moved.
        </Text>
      </Flex>
    </Box>
  );
};

export default NotFoundRoute;
