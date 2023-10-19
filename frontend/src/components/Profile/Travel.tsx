import { Heading, Box, Text, Flex } from '@chakra-ui/react';
import { AiOutlineCar } from 'react-icons/ai';
import { PiMountainsLight } from 'react-icons/pi';

interface ITravelProps {
  city: string;
  state: string;
  travelUpTo: string;
  homeMountain: string;
}

const Travel = ({ city, state, travelUpTo, homeMountain }: ITravelProps) => {
  return (
    <Box my="3rem">
      <Heading as="h3" fontSize="1.5rem">
        Travel
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
            Home mountain: {homeMountain}
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
            <AiOutlineCar />
          </Box>
          <Box>
            <Text fontSize="0.8rem" fontWeight="bold">
              willing travel up to{' '}
              {travelUpTo.includes('miles') ? travelUpTo : `${travelUpTo} miles`}
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

export default Travel;
