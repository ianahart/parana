import { Box, Flex, Text } from '@chakra-ui/react';
import { BsArrowLeft } from 'react-icons/bs';

interface IGoBackButtonProps {
  resetStoryType: () => void;
  resetForm: () => void;
}

const GoBackButton = ({ resetStoryType, resetForm }: IGoBackButtonProps) => {
  return (
    <Flex
      onClick={() => {
        resetStoryType();
        resetForm();
      }}
      cursor="pointer"
      align="center"
    >
      <Box mr="0.5rem">
        <BsArrowLeft />
      </Box>
      <Text>Go Back</Text>
    </Flex>
  );
};

export default GoBackButton;
