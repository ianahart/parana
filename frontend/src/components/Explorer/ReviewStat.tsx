import { Flex, Text, Box } from '@chakra-ui/react';
import { AiFillStar } from 'react-icons/ai';

const ReviewStat = () => {
  return (
    <Flex fontSize="0.85rem" color="text.secondary">
      <Flex align="center">
        <Box color="gold" fontSize="0.9rem" mr="0.25rem">
          <AiFillStar />
        </Box>
        <Box>
          <Text fontWeight="bold">5</Text>
        </Box>
      </Flex>
      <Flex fontSize="0.85em" mx="0.5rem">
        <Text>(24 reviews)</Text>
      </Flex>
    </Flex>
  );
};

export default ReviewStat;
