import { Box } from '@chakra-ui/react';
import { Client } from '../util/client';
import { useEffect, useRef, useState } from 'react';
import { ILatestReview, ITopReview } from '../interfaces';
import Hero from '../components/Home/Hero';
import Reviews from '../components/Home/Reviews';
import Steps from '../components/Home/Steps';
import BecomeTeacher from '../components/Home/BecomeTeacher';

const HomeRoute = () => {
  const shouldRun = useRef(true);
  const [latestReviews, setLatestReviews] = useState<ILatestReview[]>([]);
  const [topReviews, setTopReviews] = useState<ITopReview[]>([]);
  const [fiveStarReviews, setFiveStarReviews] = useState(0);
  const [userCount, setUserCount] = useState(0);

  const pagination = {
    topReviewSize: 4,
    reviewSize: 6,
  };

  const getReviewStats = () => {
    Client.getReviewStats(pagination.topReviewSize, pagination.reviewSize)
      .then((res) => {
        const { latestReviews, topReviews, fiveStarReviews } = res.data.data;
        setFiveStarReviews(fiveStarReviews);
        setLatestReviews(latestReviews);
        setTopReviews(topReviews);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const getUserStats = () => {
    Client.getUserStats()
      .then((res) => {
        setUserCount(res.data.data);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current) {
      shouldRun.current = false;
      getReviewStats();
      getUserStats();
    }
  }, [shouldRun.current]);

  return (
    <Box minH="100vh">
      <Box mx="auto" maxW="1440px">
        <Box mt="8rem" p="0.5rem" height="1000px">
          <Hero topReviews={topReviews} />
        </Box>
      </Box>
      <Box py="10rem" mx="auto" maxW="1440px">
        <Reviews
          userCount={userCount}
          latestReviews={latestReviews}
          fiveStarReviews={fiveStarReviews}
        />
      </Box>
      <Box py="10rem" mx="auto" maxW="1440px">
        <Steps />
      </Box>
      <Box py="10rem" mx="auto" maxW="1440px">
        <BecomeTeacher />
      </Box>
    </Box>
  );
};

export default HomeRoute;
