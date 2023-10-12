import registerImg from '../../assets/register.jpg';
import { Box, Flex, Heading, Image, Text } from '@chakra-ui/react';
import { GoVerified } from 'react-icons/go';
import { GiMountains } from 'react-icons/gi';

const Hero = () => {
  return (
    <Box position="relative" color="light.primary">
      <Image minH="100vh" src={registerImg} alt="snowy chairlift" />
      <Flex
        flexDir="column"
        align="center"
        justify="center"
        bg="rgba(0,0,0,0.6)"
        position="absolute"
        top="0"
        left="0"
        height="100%"
        width="100%"
      >
        <Flex bg="rgba(0, 0, 0, 0.5)" justify="center" flexDir="column" align="center">
          <Heading mb="0.5rem" as="h2">
            Discover New Experiences
          </Heading>
          <Text width="70%" lineHeight="1.8">
            Our goal is to match you with a snowboard instructor that fits your learning
            style. If you're a snowboard instructor we want to be able to help you earn a
            little extra cash while helping someone fulfill their goals.
          </Text>
          <Flex my="1.5rem">
            <Flex
              fontSize="0.85rem"
              mx="0.5rem"
              align="center"
              border="1px solid"
              borderRadius={20}
              borderColor="light.primary"
              p="0.35rem"
            >
              <GoVerified />
              <Text ml="0.25rem">100% Gaurantee</Text>
            </Flex>
            <Flex
              mx="0.5rem"
              fontSize="0.85rem"
              align="center"
              border="1px solid"
              borderRadius={20}
              borderColor="light.primary"
              p="0.35rem"
            >
              <GiMountains />
              <Text ml="0.25rem">Available At Any Mountain</Text>
            </Flex>
          </Flex>
        </Flex>
      </Flex>
    </Box>
  );
};

export default Hero;
