import { Box, Flex, Heading, Text } from '@chakra-ui/react';
import { nanoid } from 'nanoid';

interface ITerrainProps {
  heading: string;
  terrain: string;
}

const Terrain = ({ heading, terrain }: ITerrainProps) => {
  return (
    <Box>
      <Heading mb="0.25rem" fontWeight="bold" fontSize="1.5rem" as="h3">
        {heading}
      </Heading>
      {terrain !== null && (
        <Flex flexWrap="wrap">
          {terrain.split(',').map((item) => {
            return (
              <Box
                py="0.45rem"
                px="1rem"
                borderRadius={20}
                fontWeight="bold"
                bg="rgba(43, 67, 101, 0.5)"
                key={nanoid()}
                m="0.5rem"
              >
                <Text>{item}</Text>
              </Box>
            );
          })}
        </Flex>
      )}
    </Box>
  );
};

export default Terrain;
