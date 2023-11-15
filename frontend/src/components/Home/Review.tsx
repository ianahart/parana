import { Box, Flex, Image, Text } from '@chakra-ui/react';
import { ILatestReview } from '../../interfaces';
import { AiFillStar } from 'react-icons/ai';

interface IReviewProps {
  review: ILatestReview;
}

const Review = ({ review }: IReviewProps) => {
  return (
    <Box
      p="0.5rem"
      borderRadius={8}
      border="1px solid"
      borderColor="border.primary"
      color="text.secondary"
    >
      <Flex>
        {!review.avatarUrl && (
          <Flex
            dir="column"
            align="center"
            justify="center"
            borderRadius={50}
            width="45px"
            height="45px"
            bg="primary.light"
          >
            {review.fullName.slice(0, 1)}
          </Flex>
        )}
        {review.avatarUrl && (
          <Image
            width="45px"
            height="45px"
            borderRadius={50}
            src={review.avatarUrl}
            alt={review.fullName}
          />
        )}
      </Flex>
      <Flex align="center" my="0.5rem">
        <Text mr="0.5rem">{review.fullName}</Text>
        {[...Array(review.rating)].map((_, index) => {
          return (
            <Box color="orange" key={index}>
              <AiFillStar />
            </Box>
          );
        })}
      </Flex>
      <Box>
        <Text>{review.review.split(' ').slice(0, 15).join(' ')}...</Text>
      </Box>
    </Box>
  );
};

export default Review;
