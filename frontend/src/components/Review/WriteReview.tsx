import {
  Modal,
  Box,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  ModalFooter,
  Flex,
  Button,
  useDisclosure,
  Text,
  ButtonGroup,
  FormControl,
  Textarea,
  FormLabel,
} from '@chakra-ui/react';
import { useContext, useState } from 'react';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
import { AiFillStar } from 'react-icons/ai';
import { nanoid } from 'nanoid';
import { Client } from '../../util/client';

interface IError {
  [key: string]: string;
}

interface IWriteReviewProps {
  teacherId: number;
}

const WriteReview = ({ teacherId }: IWriteReviewProps) => {
  const { isOpen, onClose, onOpen } = useDisclosure();
  const { user } = useContext(UserContext) as IUserContext;
  const [rating, setRating] = useState(0);
  const [review, setReview] = useState('');
  const [error, setError] = useState('');
  const [serverErrors, setServerErrors] = useState<string[]>([]);

  const validate = (rating: number, review: string) => {
    let validated = true;
    const isLengthInRange = review.trim().length > 0 && review.length < 400;
    if (!isLengthInRange) {
      setError('Review must be between 1 and 400 characters.');
      validated = false;
    }
    if (rating === 0) {
      setError('Please rate your experience');
      validated = false;
    }
    return validated;
  };

  const applyServerErrors = <T extends IError>(data: T) => {
    for (const key of Object.keys(data)) {
      setServerErrors((prevState) => [...prevState, data[key]]);
    }
  };

  const createReview = (
    rating: number,
    review: string,
    userId: number,
    teacherId: number
  ) => {
    Client.createReview(rating, review, userId, teacherId)
      .then(() => {
        onClose();
        setReview('');
        setRating(0);
      })
      .catch((err) => {
        applyServerErrors(err.response.data);
        console.log(err);
      });
  };

  const submitReview = () => {
    setError('');
    setServerErrors([]);
    if (!validate(rating, review)) {
      return;
    }
    createReview(rating, review, user.id, teacherId);
  };

  const handleOnMouseOver = (index: number) => {
    setRating(index);
  };

  const handleOnMouseLeave = () => {
    if (rating === 1) {
      setRating(0);
      return;
    }
    setRating(rating);
  };

  return (
    <>
      {user.role === 'USER' && (
        <Flex justify="flex-start">
          <Button
            p="0"
            fontWeight="bold"
            onClick={onOpen}
            color="text.secondary"
            _hover={{ background: 'transparent' }}
            variant="ghost"
          >
            Write a review
          </Button>
        </Flex>
      )}
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent bg="primary.dark" color="text.secondary">
          <ModalHeader>Write a review</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            {error.length > 0 && (
              <Flex my="1rem" justify="center">
                <Text fontSize="0.85rem" color="error.primary">
                  {error}
                </Text>
              </Flex>
            )}
            <Flex align="center" justify="space-between">
              <Text>Rate this review</Text>
              <Box>
                <Text textAlign="right" fontWeight="bold" fontSize="0.85rem">
                  {rating} out of 5
                </Text>
                <Flex>
                  {[...Array(5)].map((_, index) => {
                    index += 1;
                    return (
                      <Box key={nanoid()}>
                        <AiFillStar
                          onMouseEnter={() => handleOnMouseOver(index)}
                          onMouseLeave={handleOnMouseLeave}
                          fontSize="1.4rem"
                          color={rating >= index ? 'orange' : '#333'}
                        />
                      </Box>
                    );
                  })}
                </Flex>
              </Box>
            </Flex>
            <Box my="2rem">
              <FormControl>
                <FormLabel htmlFor="text">Tell us about your experience</FormLabel>
                <Textarea
                  resize="none"
                  value={review}
                  onChange={(e) => setReview(e.target.value)}
                  placeholder="Enter a review..."
                  id="text"
                  name="text"
                  minH="100px"
                  borderColor="border.primary"
                />
              </FormControl>
            </Box>
            {serverErrors.length > 0 && (
              <Flex my="1rem" align="center" flexDir="column">
                {serverErrors.map((serverError) => {
                  return (
                    <Text key={nanoid()} fontSize="0.8rem" color="error.primary">
                      {serverError}
                    </Text>
                  );
                })}
              </Flex>
            )}
          </ModalBody>

          <ModalFooter>
            <ButtonGroup>
              <Button colorScheme="blackAlpha" mr={3} onClick={submitReview}>
                Submit
              </Button>
              <Button colorScheme="gray" mr={3} onClick={onClose}>
                Close
              </Button>
            </ButtonGroup>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default WriteReview;
