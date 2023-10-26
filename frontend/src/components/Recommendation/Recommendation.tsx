import {
  Box,
  Flex,
  ScaleFade,
  Text,
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalCloseButton,
} from '@chakra-ui/react';
import { IRecommendation, IUserContext } from '../../interfaces';
import UserAvatar from '../Shared/UserAvatar';
import { AiOutlineClose } from 'react-icons/ai';
import { BsChevronRight, BsThreeDots, BsTrash } from 'react-icons/bs';
import { useContext, useState } from 'react';
import { UserContext } from '../../context/user';
import { nanoid } from 'nanoid';

interface IReviewProps {
  recommendation: IRecommendation;
  removeRecommendation: (recommendationId: number) => void;
}

const Recommendation = ({ recommendation, removeRecommendation }: IReviewProps) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const { user } = useContext(UserContext) as IUserContext;
  const [isOptionsOpen, setIsOptionsOpen] = useState(false);

  const wordsPreview = () => {
    return recommendation.words.split(',');
  };

  const renderWords = (data: string[]) => {
    return data.map((word) => {
      return (
        <Box
          my="0.5rem"
          color="text.secondary"
          py="0.35rem"
          px="0.6rem"
          fontSize="0.85rem"
          mx="0.5rem"
          bg="primary.light"
          borderRadius={20}
          border="1px solid"
          borderColor="border.primary"
          key={nanoid()}
        >
          <Text>{word}</Text>
        </Box>
      );
    });
  };

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
        {user.id === recommendation.authorId && isOptionsOpen && (
          <ScaleFade initialScale={0.8} in={isOptionsOpen}>
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
                onClick={() => removeRecommendation(recommendation.id)}
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
              ></Flex>
            </Box>
          </ScaleFade>
        )}
      </Flex>
      <Flex align="center" justify="space-between">
        <Flex align="center" justify="flex-start">
          <UserAvatar
            width="45px"
            height="45px"
            fullName={recommendation.fullName}
            avatarUrl={recommendation.avatarUrl}
          />
          <Box mx="1rem">
            <Text fontWeight="bold">{recommendation.firstName}</Text>
          </Box>
        </Flex>
      </Flex>
      <Box my="0.5rem">
        <Text color="text.primary" fontSize="0.85rem">
          {recommendation.date}
        </Text>
      </Box>
      {recommendation.words.length > 0 && (
        <Flex my="0.5rem" flexWrap="wrap">
          {renderWords(wordsPreview().slice(0, 5))}
        </Flex>
      )}
      {wordsPreview().length > 5 && (
        <Flex
          onClick={onOpen}
          cursor="pointer"
          align="center"
          my="0.5rem"
          fontSize="0.85rem"
        >
          <Text>View more</Text>
          <Box ml="0.25rem">
            <BsChevronRight />
          </Box>
        </Flex>
      )}

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent bg="primary.dark">
          <ModalHeader color="text.secondary">Special words</ModalHeader>
          <ModalCloseButton color="text.secondary" />
          <ModalBody>
            <Flex flexWrap="wrap" p="1rem">
              {renderWords(wordsPreview())}
            </Flex>
          </ModalBody>
        </ModalContent>
      </Modal>

      <Box minH="100px" my="0.5rem">
        <Text>{recommendation.recommendation}</Text>
      </Box>
    </Box>
  );
};

export default Recommendation;
