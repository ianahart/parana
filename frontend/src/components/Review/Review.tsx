import { Box, Flex, Text } from '@chakra-ui/react';
import { IReview, IUserContext } from '../../interfaces';
import UserAvatar from '../Shared/UserAvatar';
import { AiFillStar, AiOutlineClose, AiOutlineEdit } from 'react-icons/ai';
import { BsThreeDots, BsTrash } from 'react-icons/bs';
import { useContext, useState } from 'react';
import { UserContext } from '../../context/user';
import WriteReview from './WriteReview';

interface IReviewProps {
  review: IReview;
  removeReview: (reviewId: number) => void;
  teacherId: number;
  resetReviewState: () => void;
}

const Review = ({ teacherId, review, removeReview, resetReviewState }: IReviewProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  const [isOptionsOpen, setIsOptionsOpen] = useState(false);

  return (
    <Box
      my="1.5rem"
      p="0.5rem"
      border="1px solid"
      borderColor="border.primary"
      borderRadius={12}
    >
      <Flex position="relative" cursor="pointer" justify="flex-end">
        <Box
          fontSize="1.4rem"
          color="text.secondary"
          onClick={() => setIsOptionsOpen(true)}
        >
          <BsThreeDots />
        </Box>
        {user.id === review.userId && isOptionsOpen && (
          <Box
            p="0.5rem"
            borderRadius={8}
            boxShadow="primary.boxShadow"
            width="120px"
            bg="border.primary"
            position="absolute"
            top="0"
            right="0"
            zIndex={10}
          >
            <Flex onClick={() => setIsOptionsOpen(false)} justify="flex-end">
              <Box>
                <AiOutlineClose />
              </Box>
            </Flex>

            <Flex
              onClick={() => removeReview(review.id)}
              fontWeight="bold"
              fontSize="0.9rem"
              p="0.25rem"
              _hover={{ background: 'blackAlpha.500' }}
              borderRadius={8}
              align="center"
              my="0.25rem"
            >
              <Box mr="0.25rem">
                <BsTrash />
              </Box>
              <Text>Delete</Text>
            </Flex>
            <Flex
              fontWeight="bold"
              fontSize="0.9rem"
              p="0.25rem"
              _hover={{ background: 'blackAlpha.500' }}
              borderRadius={8}
              align="center"
              my="0.25rem"
            >
              <Box mr="0.25rem">
                <AiOutlineEdit />
              </Box>
              <WriteReview
                reviewId={review.id}
                buttonText="Edit"
                method="update"
                resetReviewState={resetReviewState}
                teacherId={teacherId}
              />
            </Flex>
          </Box>
        )}
      </Flex>
      <Flex align="center" justify="space-between">
        <Flex align="center" justify="flex-start">
          <UserAvatar
            width="45px"
            height="45px"
            fullName={review.fullName}
            avatarUrl={review.avatarUrl}
          />
          <Box mx="1rem">
            <Text fontWeight="bold">{review.firstName}</Text>
          </Box>
        </Flex>
        <Flex align="center">
          <Box color="orange">
            <AiFillStar />
          </Box>
          <Text fontWeight="bold">{review.rating}</Text>
        </Flex>
      </Flex>
      {review.isEdited && (
        <Flex my="1rem">
          <Text color="text.primary" fontSize="0.85rem">
            (edited)
          </Text>
        </Flex>
      )}
      <Box minH="100px" my="0.5rem">
        <Text>{review.review}</Text>
      </Box>
    </Box>
  );
};

export default Review;
