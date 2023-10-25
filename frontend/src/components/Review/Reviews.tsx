import { Box, Heading } from '@chakra-ui/react';
import { AiOutlineInfoCircle } from 'react-icons/ai';
import WriteReview from './WriteReview';
import { useEffect, useRef, useState } from 'react';
import { Client } from '../../util/client';

interface IReviewsProps {
  teacherId: number;
}

const Reviews = ({ teacherId }: IReviewsProps) => {
  const shouldRun = useRef(true);
  const [reviews, setReviews] = useState([]);
  const [pagination, setPagination] = useState({
    page: 0,
    totalPages: 0,
    totalElements: 0,
    direction: 'next',
    pageSize: 1,
  });

  const getReviews = (paginate: boolean, teacherId: number) => {
    const { page, direction, pageSize } = pagination;
    const pageNum = paginate ? page : -1;

    Client.getReviews(teacherId, pageNum, pageSize, direction)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
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
        <Heading
          display="flex"
          alignItems="center"
          mb="2rem"
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
      </Box>
      <WriteReview teacherId={teacherId} />
    </Box>
  );
};

export default Reviews;
