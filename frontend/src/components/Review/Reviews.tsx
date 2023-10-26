import { Box, Heading, Flex, Button, Tooltip } from '@chakra-ui/react';
import { AiFillStar, AiOutlineInfoCircle } from 'react-icons/ai';
import WriteReview from './WriteReview';
import { useEffect, useRef, useState } from 'react';
import { Client } from '../../util/client';
import { IReview } from '../../interfaces';
import Review from './Review';

interface IReviewsProps {
  teacherId: number;
  teacherName: string;
}

const Reviews = ({ teacherId, teacherName }: IReviewsProps) => {
  const paginationInitialState = {
    page: 0,
    totalPages: 0,
    totalElements: 0,
    direction: 'next',
    pageSize: 2,
  };

  const shouldRun = useRef(true);
  const [reviews, setReviews] = useState<IReview[]>([]);
  const [avgRating, setAvgRating] = useState(1);
  const [pagination, setPagination] = useState(paginationInitialState);

  const tooltipLabel = `All of our reviews are collected by us and are 100% reliable. They correspond to a real experience lived by students of ${teacherName}.`;

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

        paginate
          ? setReviews((prevState) => [...prevState, ...reviews])
          : setReviews(reviews);

        setAvgRating(avgRating);
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
      });
  };

  const resetReviewState = () => {
    setPagination(paginationInitialState);
    getReviews(false, teacherId);
  };

  const removeReview = (reviewId: number) => {
    Client.deleteReview(reviewId)
      .then(() => {
        setReviews((prevState) => prevState.filter((r) => r.id !== reviewId));
        resetReviewState();
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
      });
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
          <Box position="relative">
            <Heading
              display="flex"
              alignItems="center"
              wordBreak="break-word"
              lineHeight="1.6"
              fontSize="1.5rem"
              as="h3"
            >
              Review
              <Tooltip label={tooltipLabel} hasArrow bg="primary.dark">
                <Box ml="0.25rem">
                  <AiOutlineInfoCircle />
                </Box>
              </Tooltip>
            </Heading>
          </Box>
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
      <WriteReview
        buttonText="Write a review"
        method="post"
        resetReviewState={resetReviewState}
        teacherId={teacherId}
      />
      <Box my="2rem">
        {reviews.map((review) => {
          return (
            <Review
              resetReviewState={resetReviewState}
              teacherId={teacherId}
              removeReview={removeReview}
              key={review.id}
              review={review}
            />
          );
        })}
      </Box>
      {pagination.page < pagination.totalPages - 1 && (
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
      )}
    </Box>
  );
};

export default Reviews;
