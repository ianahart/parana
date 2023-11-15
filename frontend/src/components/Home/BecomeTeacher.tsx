import { Box, Flex, Fade, Heading, Text, Button } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';
import { useInView } from 'react-intersection-observer';
import becomeTeacherImage from '../../assets/become_teacher.jpg';
import { useState } from 'react';

const BecomeTeacher = () => {
  const navigate = useNavigate();
  const [isHovered, setIsHovered] = useState(false);

  const { ref, inView } = useInView({
    trackVisibility: true,
    delay: 600,
    threshold: 0.5,
    triggerOnce: true,
  });

  const navigateToCreateAccount = () => navigate('/register');

  return (
    <Box>
      <Fade in={inView}>
        <Flex
          position="relative"
          dir="column"
          align="center"
          color="text.secondary"
          mx="auto"
          ref={ref}
          width={['95%', '95%', '760px']}
          borderRadius={20}
          height="400px"
          background="primary.light"
          backgroundImage={`url(${becomeTeacherImage})`}
        >
          <Box
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
            zIndex={3}
            pos="absolute"
            top="0"
            left="0"
            height="100%"
            width="100%"
            borderRadius={20}
            bg={isHovered ? 'transparent' : 'rgba(0,0,0,0.4)'}
          >
            <Box p="1rem" width={['95%', '95%', '500px']}>
              <Heading>You can become a great teacher too!</Heading>
              <Text mt="0.5rem" fontWeight="bold">
                Share your skills, live off your passion and become self-sustaining
              </Text>
              <Button onClick={navigateToCreateAccount} colorScheme="purple" my="3rem">
                Find out more
              </Button>
            </Box>
          </Box>
        </Flex>
      </Fade>
    </Box>
  );
};

export default BecomeTeacher;
