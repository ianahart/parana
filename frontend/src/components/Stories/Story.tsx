import { Box, Flex, Heading, Tooltip } from '@chakra-ui/react';
import { IConnectionStory } from '../../interfaces';
import { useState } from 'react';
import StoryViewer from './StoryViewer';
import { AiOutlineClose } from 'react-icons/ai';

interface IStoryProps {
  connectionStory: IConnectionStory;
}

const Story = ({ connectionStory }: IStoryProps) => {
  const { avatarUrl, fullName } = connectionStory;
  const [isHovered, setIsHovered] = useState(false);
  const [storyViewerOpen, setStoryViewerOpen] = useState(false);

  const closeStoryViewer = () => {
    setStoryViewerOpen(false);
  };

  return (
    <Box minH="100vh">
      <Flex
        pos="relative"
        p="0.25rem"
        mx="0.5rem"
        onClick={() => setStoryViewerOpen(true)}
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
        background={!avatarUrl ? 'primary.light' : 'unset'}
        backgroundImage={avatarUrl ? avatarUrl : 'none'}
        backgroundSize="cover"
        boxShadow="md"
        width="120px"
        height="160px"
        borderRadius={8}
      >
        <Flex
          cursor="pointer"
          dir="column"
          align="flex-end"
          pos="absolute"
          width="100%"
          height="100%"
          top="0"
          left="0"
          bg={isHovered ? 'transparent' : 'rgba(0,0,0,0.5)'}
          borderRadius={8}
        >
          <Heading m="0.25rem" justifySelf="flex-end" color="#fff" fontSize="0.9rem">
            {fullName}
          </Heading>
        </Flex>
      </Flex>
      {storyViewerOpen && (
        <Box
          pt="5rem"
          zIndex={5}
          pos="absolute"
          top="0"
          left="0"
          width="100%"
          height="100%"
          bg="rgba(0, 0, 0, 0.9)"
        >
          <Box width={['95%', '95%', '760px']} mx="auto">
            <Flex justify="flex-end" color="#fff" fontSize="2rem">
              <Tooltip label="Exit Story">
                <Box onClick={() => setStoryViewerOpen(false)}>
                  <AiOutlineClose />
                </Box>
              </Tooltip>
            </Flex>
            <Flex justify="center" minH="100vh">
              <StoryViewer
                closeStoryViewer={closeStoryViewer}
                connectionStory={connectionStory}
              />
            </Flex>
          </Box>
        </Box>
      )}
    </Box>
  );
};
export default Story;
