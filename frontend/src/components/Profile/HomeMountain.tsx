import { Box, Heading, Text } from '@chakra-ui/react';

interface IHomeMountainProps {
  homeMountain: string;
  firstName: string;
}

const HomeMountain = ({ homeMountain, firstName }: IHomeMountainProps) => {
  return (
    <Box mt="3rem">
      <Heading mb="2rem" fontSize="1.5rem">
        {firstName}'s home mountain
      </Heading>
      <Text>{homeMountain}</Text>
    </Box>
  );
};

export default HomeMountain;
