import { Box, Flex, Image, Text } from '@chakra-ui/react';

interface IHeroImageProps {
  firstName: string;
  avatarUrl: string;
}

const HeroImage = ({ firstName, avatarUrl }: IHeroImageProps) => {
  return (
    <Flex
      width="100%"
      borderRadius={20}
      display="inline-block"
      overflow="hidden"
      justify="center"
    >
      {avatarUrl ? (
        <Image
          transition="transform .2s"
          _hover={{ transform: 'scale(1.1)' }}
          width="100%"
          borderRadius={20}
          height="300px"
          src={avatarUrl}
          alt={firstName}
        />
      ) : (
        <Flex
          width="100%"
          height="300px"
          borderRadius={20}
          flexDir="column"
          justify="center"
          align="center"
          bg="primary.light"
        >
          <Text color="text.secondary" fontSize="5rem">
            {firstName.slice(0, 1)}
          </Text>
        </Flex>
      )}
    </Flex>
  );
};
export default HeroImage;
