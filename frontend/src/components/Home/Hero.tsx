import { Box, Button, Flex, Heading, Image, SlideFade, Text } from '@chakra-ui/react';
import { ITopReview } from '../../interfaces';
import { useNavigate } from 'react-router-dom';
import { MdOutlineSnowboarding } from 'react-icons/md';
import { AiFillStar } from 'react-icons/ai';
import { useInView } from 'react-intersection-observer';

interface IHeroProps {
  topReviews: ITopReview[];
}

const Hero = ({ topReviews }: IHeroProps) => {
  const navigate = useNavigate();

  const { ref, inView } = useInView({
    trackVisibility: true,
    delay: 600,
    threshold: 0.5,
  });

  const navigateToLogin = () => {
    navigate('/register');
  };

  return (
    <Flex className="home-hero-container">
      <Box className="home-hero-search-container">
        <Heading as="h1" mb="0.25rem" width="300px" color="text.secondary">
          Find the perfect
        </Heading>
        <Heading as="h2" mb="0.25rem" width="360px" color="text.secondary">
          snowboard teacher
        </Heading>

        <Text color="text.primary" fontWeight="bold">
          Meet up at your favorite mountain,
        </Text>
        <Text color="purple.300" fontWeight="bold">
          make lasting experiences
        </Text>
        <Box cursor="pointer" onClick={navigateToLogin} my="3rem">
          <Flex justify="space-between" p="1rem" borderRadius={16} bg="#fff">
            <Flex align="center">
              <Box fontSize="1.5rem" color="purple.300">
                <MdOutlineSnowboarding />
              </Box>
              <Box ml="1rem">
                <Text color="text.primary" fontWeight="bold">
                  Find a teacher in your area
                </Text>
              </Box>
            </Flex>
            <Box>
              <Button colorScheme="purple">Search</Button>
            </Box>
          </Flex>
        </Box>
      </Box>
      <SlideFade in={inView} offsetY="200px">
        <Flex ref={ref} className="home-top-reviews-container">
          {topReviews.map((topReview, index) => {
            return (
              <Box pos="relative" key={topReview.userId}>
                <Box
                  mx="1rem"
                  background="primary.light"
                  backgroundImage={topReview.avatarUrl}
                  backgroundPosition="center"
                  backgroundSize="cover"
                  mt={index % 2 === 0 ? '3rem' : '0'}
                  height="600px"
                  width="140px"
                  borderRadius={60}
                  filter="blur(3px)"
                ></Box>
                <Flex
                  flexDir="column"
                  bottom={index % 2 === 0 ? '250px' : '220px'}
                  align="center"
                  justify="space-between"
                  left="15px"
                  width="140px"
                  pos="absolute"
                  zIndex="3"
                >
                  <Image borderRadius={8} src={topReview.avatarUrl} />
                  <Box
                    bg="#fff"
                    mt={index % 2 === 0 ? '1.5rem' : '0.25rem'}
                    borderRadius={8}
                    p="1rem"
                  >
                    <Text fontWeight="bold" fontSize="0.8rem">
                      {topReview.fullName}
                    </Text>
                    <Flex justify="center">
                      <Flex align="center" mr="0.5rem">
                        <Box>
                          <AiFillStar color="orange" />
                        </Box>
                        <Text color="black.tertiary" fontSize="0.8rem" fontWeight="bold">
                          {topReview.averageRating}
                        </Text>
                      </Flex>

                      <Text color="text.primary" fontSize="0.75rem">
                        ({topReview.numReviews} reviews)
                      </Text>
                    </Flex>
                  </Box>
                </Flex>
              </Box>
            );
          })}
        </Flex>
      </SlideFade>
    </Flex>
  );
};

export default Hero;
