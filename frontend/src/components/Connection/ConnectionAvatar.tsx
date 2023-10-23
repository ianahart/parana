import { Box, Flex, Image, Text } from '@chakra-ui/react';
import { abbreviate } from '../../util';

interface IConnectionAvatarProps {
  width: string;
  height: string;
  avatarUrl: string;
  fullName: string;
}

const ConnectionAvatar = ({
  avatarUrl,
  width,
  height,
  fullName,
}: IConnectionAvatarProps) => {
  return (
    <Box width={width} height={height}>
      {avatarUrl ? (
        <Image
          borderTopRadius={8}
          width="100%"
          height="100%"
          src={avatarUrl}
          alt={fullName}
        />
      ) : (
        <Flex
          align="center"
          justify="center"
          flexDir="column"
          borderTopRadius={8}
          width="100%"
          height="100%"
          bg="primary.light"
        >
          <Flex
            flexDir="column"
            align="center"
            justify="center"
            width="80px"
            height="80px"
            borderRadius="50%"
            bg="blackAlpha.500"
          >
            <Text fontSize="2rem">{abbreviate(fullName)}</Text>
          </Flex>
        </Flex>
      )}
    </Box>
  );
};

export default ConnectionAvatar;
