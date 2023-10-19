import { Box, Heading, Text } from '@chakra-ui/react';

interface IBiographyProps {
  bio: string;
  firstName: string;
}

const Biography = ({ bio, firstName }: IBiographyProps) => {
  return (
    <Box>
      <Heading
        mb="2rem"
        wordBreak="break-word"
        lineHeight="1.6"
        fontSize="1.5rem"
        as="h3"
      >
        About {firstName}
      </Heading>
      <Text lineHeight="1.8">{bio}</Text>
    </Box>
  );
};

export default Biography;
