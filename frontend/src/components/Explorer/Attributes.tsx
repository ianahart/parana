import { Box, Flex, Text } from '@chakra-ui/react';
import { AiOutlineGift } from 'react-icons/ai';

interface IAttributesProps {
  perHour: string;
  firstLessonFree: boolean;
}

const Attributes = ({ perHour, firstLessonFree }: IAttributesProps) => {
  return (
    <Flex color="text.secondary">
      <Flex
        borderRadius={20}
        bg="primary.light"
        p="0.35rem"
        mx="1rem"
        fontSize="0.85rem"
        fontWeight="bold"
      >
        <Text>${perHour}/h</Text>
      </Flex>

      {firstLessonFree && (
        <Flex
          borderRadius={20}
          bg="primary.light"
          p="0.35rem"
          align="center"
          mx="1rem"
          fontSize="0.85rem"
          fontWeight="bold"
        >
          <Box>
            <AiOutlineGift />
          </Box>
          <Text>1st lesson free</Text>
        </Flex>
      )}
    </Flex>
  );
};

export default Attributes;
