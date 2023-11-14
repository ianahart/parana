import { Box, Flex, Image, Text } from '@chakra-ui/react';
import { IConnectionStory } from '../../interfaces';
import UserAvatar from '../Shared/UserAvatar';
import { useEffect, useState } from 'react';
import DurationBar from './DurationBar';
import { BsChevronLeft, BsChevronRight } from 'react-icons/bs';

interface IStoryViewerProps {
  connectionStory: IConnectionStory;
}

const StoryViewer = ({ connectionStory }: IStoryViewerProps) => {
  const { avatarUrl, fullName, stories } = connectionStory;
  const [activeIndex, setActiveIndex] = useState(0);
  const [secondsElapsed, setSecondsElapsed] = useState(0);

  const goToPrevIndex = () => {
    setSecondsElapsed(0);
    if (activeIndex > 0) {
      setActiveIndex((prevState) => prevState - 1);
    } else {
      setActiveIndex(stories.length - 1);
    }
  };

  const goToNextIndex = () => {
    setSecondsElapsed(0);
    if (activeIndex < stories.length - 1) {
      setActiveIndex((prevState) => prevState + 1);
    } else {
      setActiveIndex(0);
    }
  };

  useEffect(() => {
    const interval = setInterval(() => {
      setSecondsElapsed((prevState) => prevState + 1000);
    }, 1000);
    if (secondsElapsed > parseInt(stories[activeIndex].duration) - 1000) {
      goToNextIndex();
      setSecondsElapsed(0);
      clearInterval(interval);
    }

    return () => clearInterval(interval);
  }, [secondsElapsed]);

  const hoursSincePosted = (date: Date) => {
    const posted = new Date(date);
    const now = new Date();
    return `${now.getHours() - posted.getHours() + 1}h`;
  };

  return (
    <Box p="0.5rem" minH={['100vh', '600px', '600px']} width={['95%', '95%', '450px']}>
      <Flex align="center">
        <UserAvatar
          width="45px"
          height="45px"
          fullName={fullName}
          avatarUrl={avatarUrl}
        />
        <Text ml="0.5rem" fontSize="0.9rem" color="#fff">
          {fullName}
        </Text>
      </Flex>
      <Flex flexWrap="wrap" justify="space-around" my="1rem">
        {stories.map((story, index) => {
          return (
            <DurationBar
              isActive={activeIndex === index}
              key={index}
              duration={parseInt(story.duration)}
            />
          );
        })}
      </Flex>

      {stories[activeIndex].photoUrl && (
        <Box
          display="flex"
          justifyContent="center"
          alignItems="center"
          pos="relative"
          my="1rem"
          minH="600px"
          width={['95%', '95%', '400px']}
        >
          <Box zIndex={10} color="#fff" position="absolute" top="10px" right="10px">
            <Text fontWeight="bold">
              {hoursSincePosted(stories[activeIndex].createdAt)}
            </Text>
          </Box>
          <Flex
            dir="column"
            mx="auto"
            justify="center"
            align={stories[activeIndex].alignment}
            my="1rem"
            minH="600px"
            width={['95%', '95%', '400px']}
            borderRadius={8}
            backgroundImage={stories[activeIndex].photoUrl}
            backgroundSize="cover"
            filter="blur(5px)"
          ></Flex>
          <Image
            borderRadius={8}
            top="100px"
            zIndex={10}
            pos="absolute"
            src={stories[activeIndex].photoUrl}
            height="300px"
            width="95%"
          />
          <Box width="95%" position="absolute" top="200px" zIndex={11}>
            <Box
              cursor="pointer"
              onClick={goToPrevIndex}
              pos="absolute"
              left="0"
              fontSize="2rem"
              color="#fff"
            >
              <BsChevronLeft />
            </Box>
            <Box
              cursor="pointer"
              onClick={goToNextIndex}
              pos="absolute"
              right="0"
              fontSize="2rem"
              color="#fff"
            >
              <BsChevronRight />
            </Box>
          </Box>
        </Box>
      )}
      {!stories[activeIndex].photoUrl && (
        <Flex
          dir="column"
          mx="auto"
          justify="center"
          align={stories[activeIndex].alignment}
          my="1rem"
          minH="600px"
          width={['95%', '95%', '400px']}
          borderRadius={8}
          bg={stories[activeIndex].background}
          pos="relative"
        >
          <Box zIndex={10} color="#fff" position="absolute" top="10px" right="10px">
            <Text fontWeight="bold">
              {hoursSincePosted(stories[activeIndex].createdAt)}
            </Text>
          </Box>

          <Box
            cursor="pointer"
            onClick={goToPrevIndex}
            pos="absolute"
            left="0"
            fontSize="2rem"
            color="#fff"
          >
            <BsChevronLeft />
          </Box>
          <Box
            cursor="pointer"
            onClick={goToNextIndex}
            pos="absolute"
            right="0"
            fontSize="2rem"
            color="#fff"
          >
            <BsChevronRight />
          </Box>
          <Box>
            <Text
              fontWeight="bold"
              color={stories[activeIndex].color}
              fontSize={stories[activeIndex].fontSize}
              p="0.25rem"
              wordBreak="break-all"
            >
              {stories[activeIndex].text}
            </Text>
          </Box>
        </Flex>
      )}
    </Box>
  );
};

export default StoryViewer;
