import { Box, Heading, Text, Flex } from '@chakra-ui/react';
import { FiTarget } from 'react-icons/fi';
import { BsFlag } from 'react-icons/bs';

interface IAboutLessonProps {
  about: string;
  level: string;
  lang: string;
}

const AboutLesson = ({ about, level, lang }: IAboutLessonProps) => {
  return (
    <Box mt="3rem">
      <Heading
        mb="2rem"
        wordBreak="break-word"
        lineHeight="1.6"
        fontSize="1.5rem"
        as="h3"
      >
        About the lesson
      </Heading>
      <Flex my="1.5rem" align="center">
        <Flex
          align="center"
          fontSize="0.85rem"
          fontWeight="bold"
          p="0.5rem"
          border="1px solid"
          borderColor="border.primary"
          borderRadius={20}
          mx="0.5rem"
        >
          <Box mr="0.25rem">
            <FiTarget />
          </Box>
          <Text>{level}</Text>
        </Flex>
        <Flex
          align="center"
          fontSize="0.85rem"
          fontWeight="bold"
          p="0.5rem"
          border="1px solid"
          borderColor="border.primary"
          borderRadius={20}
          mx="0.5rem"
        >
          <Box mr="0.25rem">
            <BsFlag />
          </Box>
          <Text>{lang}</Text>
        </Flex>
      </Flex>
      <Text lineHeight="1.8">{about}</Text>
    </Box>
  );
};

export default AboutLesson;
