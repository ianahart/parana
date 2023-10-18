import { Box, Flex, Text } from '@chakra-ui/react';
import { nanoid } from 'nanoid';
import { AiOutlineCloseCircle } from 'react-icons/ai';

interface IHashTagsProps {
  name: string;
  hashtags: string[];
  removeHashTag: (name: string, hashtag: string) => void;
}

const HashTags = ({ name, hashtags, removeHashTag }: IHashTagsProps) => {
  return (
    <Box my="0.5rem">
      <Flex flexWrap="wrap">
        {hashtags.map((hashtag) => {
          return (
            <Box key={nanoid()} pos="relative">
              <Box
                mx="0.5rem"
                p="0.35rem"
                border="1px solid"
                borderColor="border.primary"
                borderRadius={20}
              >
                <Text>#{hashtag}</Text>
              </Box>
              <Box
                onClick={() => removeHashTag(name, hashtag)}
                cursor="pointer"
                pos="absolute"
                top="0"
                right="0"
              >
                <AiOutlineCloseCircle />
              </Box>
            </Box>
          );
        })}
      </Flex>
    </Box>
  );
};

export default HashTags;
