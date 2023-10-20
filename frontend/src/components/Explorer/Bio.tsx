import { Flex, Text } from '@chakra-ui/react';

interface IBioProps {
  bio: string;
}

const Bio = ({ bio }: IBioProps) => {
  const elipses = bio.split(' ').slice(0, 10).join(' ') + '...';

  return (
    <Flex my="1rem" justify="center" color="text.secondary" fontSize="0.9rem">
      <Text lineHeight="1.8">{elipses}</Text>
    </Flex>
  );
};

export default Bio;
