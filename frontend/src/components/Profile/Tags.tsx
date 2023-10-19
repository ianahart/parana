import { Box, Flex, Heading, Text } from '@chakra-ui/react';
import { nanoid } from 'nanoid';

interface ITagsProps {
  heading: string;
  tags: string;
}

const Tags = ({ tags, heading }: ITagsProps) => {
  return (
    <Box my="3rem">
      <Heading mb="0.25rem" fontWeight="bold" fontSize="1.5rem" as="h3">
        {heading}
      </Heading>
      {tags !== null && (
        <Flex flexWrap="wrap">
          {tags.split(',').map((item) => {
            return (
              <Box
                py="0.45rem"
                px="1rem"
                borderRadius={20}
                fontWeight="bold"
                border="1px solid"
                borderColor="border.primary"
                key={nanoid()}
                m="0.5rem"
              >
                <Text>#{item}</Text>
              </Box>
            );
          })}
        </Flex>
      )}
    </Box>
  );
};

export default Tags;
