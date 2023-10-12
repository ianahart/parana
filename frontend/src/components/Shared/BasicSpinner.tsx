import { Text, Flex, Spinner } from '@chakra-ui/react';

interface IBasicSpinnerProps {
  message?: string;
  color: string;
}

const BasicSpinner = ({ message, color = '#fff' }: IBasicSpinnerProps) => {
  return (
    <Flex flexDir="column" align="center" justify="center">
      <Spinner
        thickness="4px"
        speed="0.65s"
        emptyColor="gray.200"
        color="gray.700"
        size="xl"
      />

      <Text color={color} fontSize="0.9rem">
        {message}
      </Text>
    </Flex>
  );
};

export default BasicSpinner;
