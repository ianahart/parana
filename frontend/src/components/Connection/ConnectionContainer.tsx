import { Box, Flex, Text } from '@chakra-ui/react';
import ConnectionAvatar from './ConnectionAvatar';
interface IConnectionContainerProps {
  data: any;
  children: React.ReactNode;
}

const ConnectionContainer = ({ data, children }: IConnectionContainerProps) => {
  return (
    <Box
      my="1rem"
      width="200px"
      color="text.secondary"
      borderRadius={8}
      mx="auto"
      bg="blackAlpha.500"
      minH="275px"
    >
      <Flex flexDir="column" justify="space-between" minH="275px">
        <Box>
          <ConnectionAvatar
            width="200px"
            height="160px"
            avatarUrl={data.avatarUrl}
            fullName={data.fullName}
          />
          <Flex mt="0.5rem" flexDir="column" align="center">
            <Text fontWeight="bold">{data.fullName}</Text>
          </Flex>
        </Box>
        {children}
      </Flex>
    </Box>
  );
};

export default ConnectionContainer;
