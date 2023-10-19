import { Box, Heading, Text, Flex } from '@chakra-ui/react';
import { PiMountainsLight } from 'react-icons/pi';
import { BsMap } from 'react-icons/bs';

interface ILessonLocationProps {
  city: string;
  state: string;
  travelUpTo: string;
}

const LessonLocation = ({ city, state, travelUpTo }: ILessonLocationProps) => {
  return (
    <Box my="3rem">
      <Heading as="h3" fontSize="1.5rem">
        Lesson location
      </Heading>
      <Flex mt="1.5rem" mb="3rem">
        <Flex
          mx="0.25rem"
          p="0.5rem"
          borderRadius={20}
          border="1px solid"
          borderColor="border.primary"
          align="center"
        >
          <Box mr="0.5rem">
            <PiMountainsLight />
          </Box>
          <Text fontWeight="bold" fontSize="0.8rem">
            At the mountain
          </Text>
        </Flex>
        <Flex
          border="1px solid"
          borderColor="border.primary"
          borderRadius={20}
          p="0.5rem"
          mx="0.25rem"
          align="center"
        >
          <Box mr="0.5rem">
            <BsMap />
          </Box>
          <Box>
            <Text fontSize="0.8rem" fontWeight="bold">
              At your home mountain or: will travel up to {travelUpTo}
              <Box ml="0.25rem" color="text.primary" as="span">
                from {city}, {state}
              </Box>
            </Text>
          </Box>
        </Flex>
      </Flex>
    </Box>
  );
};

export default LessonLocation;
