import { Box, Heading, Flex, Button } from '@chakra-ui/react';
import { AiFillStar, AiOutlineInfoCircle } from 'react-icons/ai';
import WriteReview from './WriteReview';
import { useEffect, useRef, useState } from 'react';
import { Client } from '../../util/client';
import { IReview } from '../../interfaces';
import Review from './Review';
import { remove } from 'lodash';

interface IReviewsProps {
  teacherId: number;
}

const Reviews = ({ teacherId }: IReviewsProps) => {
  const shouldRun = useRef(true);
  const [reviews, setReviews] = useState<IReview[]>([]);
  const [avgRating, setAvgRating] = useState(1);
  const [pagination, setPagination] = useState({
    page: 0,
    totalPages: 0,
    totalElements: 0,
    direction: 'next',
    pageSize: 2,
  });

  const getReviews = (paginate: boolean, teacherId: number) => {
    const pageNum = paginate ? pagination.page : -1;

    Client.getReviews(teacherId, pageNum, pagination.pageSize, pagination.direction)
      .then((res) => {
        const {
          avgRating,
          direction,
          page,
          pageSize,
          reviews,
          totalPages,
          totalElements,
        } = res.data.data;

        setPagination((prevState) => ({
          ...prevState,
          direction,
          page,
          pageSize,
          totalElements,
          totalPages,
        }));

        setReviews((prevState) => [...prevState, ...reviews]);
        setAvgRating(avgRating);
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
      });
  };

  const removeReview = (reviewId: number) => {
    setReviews((prevState) => prevState.filter((r) => r.id !== reviewId));
  };

  useEffect(() => {
    if (shouldRun.current) {
      shouldRun.current = false;
      getReviews(false, teacherId);
    }
  }, [shouldRun.current, teacherId]);

  return (
    <Box>
      <Box mt="3rem">
        <Flex align="center" justify="space-between">
          <Heading
            display="flex"
            alignItems="center"
            wordBreak="break-word"
            lineHeight="1.6"
            fontSize="1.5rem"
            as="h3"
          >
            Review
            <Box ml="0.25rem">
              <AiOutlineInfoCircle />
            </Box>
          </Heading>

          <Flex align="center" fontSize="0.85rem">
            <Box mr="0.25rem" color="orange">
              <AiFillStar />
            </Box>
            <Box fontWeight="bold">{avgRating}</Box>
            <Box fontWeight="bold" mx="0.25rem">
              ({pagination.totalElements} reviews)
            </Box>
          </Flex>
        </Flex>
      </Box>
      <WriteReview teacherId={teacherId} />
      <Box my="2rem">
        {reviews.map((review) => {
          return <Review removeReview={removeReview} key={review.id} review={review} />;
        })}
      </Box>
      <Flex justify="flex-end">
        <Button
          onClick={() => getReviews(true, teacherId)}
          fontSize="0.9rem"
          fontWeight="bold"
          color="text.secondary"
          variant="ghost"
          _hover={{ background: 'transparent' }}
        >
          See more
        </Button>
      </Flex>
    </Box>
  );
};

export default Reviews;
