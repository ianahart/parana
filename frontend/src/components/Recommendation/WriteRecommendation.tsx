import {
  Button,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  useDisclosure,
  ButtonGroup,
  Text,
  Tooltip,
  Flex,
  Box,
  FormControl,
  FormLabel,
  Textarea,
  Divider,
} from '@chakra-ui/react';
import { useContext, useState } from 'react';
import { UserContext } from '../../context/user';
import { IRecommendationWord, IUserContext } from '../../interfaces';
import { reccomendationWordState } from '../../state/initialState';
import { Client } from '../../util/client';
import { nanoid } from 'nanoid';

interface IError {
  [key: string]: string;
}

interface IWriteRecomendationProps {
  teacherId: number;
  teacherName: string;
  resetRecommendations: () => void;
}

const WriteRecomendation = ({
  teacherId,
  teacherName,
  resetRecommendations,
}: IWriteRecomendationProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [words, setWords] = useState<IRecommendationWord[]>(reccomendationWordState);
  const [selectedWords, setSelectedWords] = useState<IRecommendationWord[]>([]);
  const [errors, setErrors] = useState<string[]>([]);
  const [recommendation, setRecommendation] = useState('');

  const selectWord = (word: IRecommendationWord) => {
    setWords((prevState) => prevState.filter((w) => w.id !== word.id));
    setSelectedWords((prevState) => [...prevState, word]);
  };

  const deselectWord = (selectedWord: IRecommendationWord) => {
    setWords((prevState) => [...prevState, selectedWord]);
    setSelectedWords((prevState) => prevState.filter((sw) => sw.id !== selectedWord.id));
  };

  const resetReccomendation = () => {
    setSelectedWords([]);
    setWords(reccomendationWordState);
    setRecommendation('');
    onClose();
  };

  const validate = (recommendation: string) => {
    const isValidLength = recommendation.trim().length > 0 && recommendation.length < 400;
    if (!isValidLength) {
      const error = 'Recommendation must be between 1 and 400 characters';
      setErrors((prevState) => [...prevState, error]);
    }

    return isValidLength;
  };

  const stringifySelectedWords = () => {
    return selectedWords.map((selectedWord) => selectedWord.word).join(',');
  };

  const applyErrors = <T extends IError>(data: T) => {
    console.log(data);
    for (const key of Object.keys(data)) {
      setErrors((prevState) => [...prevState, data[key]]);
    }
  };

  const createRecommendation = () => {
    setErrors([]);
    if (!validate(recommendation)) {
      return;
    }
    Client.createRecommendation(
      user.id,
      teacherId,
      recommendation,
      stringifySelectedWords()
    )
      .then(() => {
        resetReccomendation();
        resetRecommendations();
      })
      .catch((err) => {
        applyErrors(err.response.data);
      });
  };

  return (
    <>
      {user.role === 'TEACHER' && (
        <Button
          _hover={{ background: 'transparent' }}
          color="text.secondary"
          variant="ghost"
          onClick={onOpen}
        >
          Write a recommendation
        </Button>
      )}
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent bg="primary.dark" color="text.secondary">
          <ModalHeader>Write a recommendation</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Box>
              <Text fontWeight="bold">Select all that apply</Text>
              <Flex flexWrap="wrap" gap={5} my="1rem">
                {words.map(({ id, word }) => {
                  return (
                    <Box
                      onClick={() => selectWord({ id, word })}
                      cursor="pointer"
                      _hover={{ opacity: 0.7 }}
                      borderRadius={20}
                      px="0.6rem"
                      py="0.35rem"
                      border="1px solid"
                      borderColor="border.primary"
                      bg={id % 2 === 0 ? 'primary.light' : 'transparent'}
                      key={id}
                    >
                      <Text>{word}</Text>
                    </Box>
                  );
                })}
              </Flex>
              <Divider borderColor="border.primary" />
              <Box my="0.25rem">
                <Text fontSize="0.85rem" color="text.primary" textAlign="center">
                  <Box as="span" fontWeight="bold">
                    {selectedWords.length}
                  </Box>{' '}
                  selected out of{' '}
                  <Box as="span" fontWeight="bold">
                    {reccomendationWordState.length}
                  </Box>
                </Text>
              </Box>
              <Flex flexWrap="wrap" my="0.5rem">
                {selectedWords.map(({ id, word }) => {
                  return (
                    <Box
                      onClick={() => deselectWord({ id, word })}
                      _hover={{ borderColor: 'border.primary' }}
                      border="1px solid transparent"
                      px="0.6rem"
                      py="0.35rem"
                      cursor="pointer"
                      borderRadius={20}
                      key={id}
                      mx="0.5rem"
                    >
                      <Tooltip label="Remove">
                        <Text fontSize="0.85rem">{word}</Text>
                      </Tooltip>
                    </Box>
                  );
                })}
              </Flex>
              <Divider borderColor="border.primary" />

              <Flex my="1.5rem">
                <FormControl>
                  <FormLabel htmlFor="recommendation" fontWeight="bold">
                    Tell us why you recommend {teacherName}
                  </FormLabel>
                  <Textarea
                    _hover={{ borderColor: 'border.primary' }}
                    placeholder="Enter a recommendation..."
                    value={recommendation}
                    minH="100px"
                    borderColor="border.primary"
                    resize="none"
                    onChange={(e) => setRecommendation(e.target.value)}
                    id="recommendation"
                    name="recommendation"
                  />
                </FormControl>
              </Flex>
            </Box>

            {errors.length > 0 && (
              <Flex flexDir="column" align="center">
                {errors.map((error) => {
                  return (
                    <Text
                      key={nanoid()}
                      my="0.25rem"
                      color="error.primary"
                      fontSize="0.8rem"
                    >
                      {error}
                    </Text>
                  );
                })}
              </Flex>
            )}
          </ModalBody>
          <ModalFooter>
            <ButtonGroup>
              <Button onClick={createRecommendation} mr={3} colorScheme="blackAlpha">
                Submit
              </Button>
              <Button colorScheme="gray" mr={3} onClick={onClose}>
                Cancel
              </Button>
            </ButtonGroup>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default WriteRecomendation;
