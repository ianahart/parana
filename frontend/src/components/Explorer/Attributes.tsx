import { Badge, Box, Flex, Text } from '@chakra-ui/react';
import { AiOutlineGift } from 'react-icons/ai';

interface IAttributesProps {
  perHour: string;
  firstLessonFree: boolean;
  isNewTeacher: boolean;
}

const Attributes = ({ perHour, firstLessonFree, isNewTeacher }: IAttributesProps) => {
  return (
    <Flex color="text.secondary" align="center">
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
      {isNewTeacher && (
        <Flex flexDir="column">
          <Badge mx="1rem" colorScheme="orange" variant="solid">
            New
          </Badge>
        </Flex>
      )}
    </Flex>
  );
};

export default Attributes;
