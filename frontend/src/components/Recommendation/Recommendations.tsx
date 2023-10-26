import { Box, Flex, Tooltip, Heading } from '@chakra-ui/react';
import { AiOutlineInfoCircle } from 'react-icons/ai';
import { HiOutlineThumbUp } from 'react-icons/hi';
import WriteRecomendation from './WriteRecommendation';

interface IRecommendationsProps {
  teacherId: number;
  teacherName: string;
}

const Recommendations = ({ teacherName, teacherId }: IRecommendationsProps) => {
  const tooltipLabel = 'Recomendations are given by other teachers.';

  return (
    <Box mt="3rem">
      <Flex align="center" justify="space-between">
        <Box position="relative">
          <Heading
            display="flex"
            alignItems="center"
            wordBreak="break-word"
            lineHeight="1.6"
            fontSize="1.5rem"
            as="h3"
          >
            Recommendations
            <Tooltip label={tooltipLabel} hasArrow bg="primary.dark">
              <Box ml="0.25rem">
                <AiOutlineInfoCircle />
              </Box>
            </Tooltip>
          </Heading>
        </Box>
        <Flex
          borderRadius={20}
          p="0.5rem"
          bg="primary.light"
          align="center"
          fontSize="0.85rem"
        >
          <Box mr="0.25rem" color="text.secondary" fontSize="1.1rem">
            <HiOutlineThumbUp />
          </Box>
          <Box fontWeight="bold">9</Box>
        </Flex>
      </Flex>
      <WriteRecomendation teacherName={teacherName} teacherId={teacherId} />
    </Box>
  );
};

export default Recommendations;
