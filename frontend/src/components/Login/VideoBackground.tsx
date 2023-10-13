import { Box, Flex } from '@chakra-ui/react';
import loginMp4 from '../../assets/login.mp4';

const VideoBackground = () => {
  return (
    <Box position="relative" minH="100vh">
      <video
        style={{ objectFit: 'fill', height: '100vh' }}
        src={loginMp4}
        autoPlay
        loop
        muted
      ></video>
      <Flex
        pos="absolute"
        top="0"
        left="0"
        width="100%"
        height="100%"
        bg="rgba(0, 0, 0, 0.7)"
      ></Flex>
    </Box>
  );
};

export default VideoBackground;
