import {
  Box,
  Text,
  Button,
  Heading,
  Flex,
  Grid,
  GridItem,
  SlideFade,
} from '@chakra-ui/react';
import { ILatestReview } from '../../interfaces';
import { useNavigate } from 'react-router-dom';
import { useInView } from 'react-intersection-observer';

import Review from './Review';
interface IReviewsProps {
  latestReviews: ILatestReview[];
  fiveStarReviews: number;
  userCount: number;
}

const Reviews = ({ latestReviews, fiveStarReviews, userCount }: IReviewsProps) => {
  const navigate = useNavigate();

  const { ref, inView } = useInView({
    trackVisibility: true,
    delay: 600,
    threshold: 0.5,
    triggerOnce: true,
  });

  const navigateToLogin = () => navigate('/register');

  return (
    <Box p="0.5rem">
      <Flex className="home-reviews-page-wrapper">
        <Box className="home-reviews-container">
          <Heading as="h1" mb="0.25rem" width="300px" color="text.secondary">
            Skilled and
          </Heading>
          <Heading as="h2" mb="0.25rem" width="360px" color="text.secondary">
            top-rated teachers
          </Heading>
          <Text color="text.primary" fontWeight="bold"></Text>
          <Text color="purple.300" fontWeight="bold">
            More than {userCount} users{' '}
            <Box color="text.primary" fontWeight="normal" as="span">
              currently learning
            </Box>
          </Text>
          <Text color="purple.300" fontWeight="bold">
            <Box fontWeight="normal" as="span" color="text.primary">
              Over
            </Box>{' '}
            {fiveStarReviews} 5 star reviews
          </Text>
          <Box cursor="pointer" onClick={navigateToLogin} my="3rem">
            <Button colorScheme="purple">Find your teacher</Button>
          </Box>
        </Box>
        <SlideFade in={inView} offsetY="200px">
          <Grid ref={ref} className="home-reviews-grid">
            {latestReviews.map((review) => {
              return (
                <GridItem m="1rem" key={review.id} transform="rotate(-10deg)">
                  <Review review={review} />
                </GridItem>
              );
            })}
          </Grid>
        </SlideFade>
      </Flex>
    </Box>
  );
};

export default Reviews;
