import { Box, Button, Flex, Text } from '@chakra-ui/react';
import ConnectionContainer from './ConnectionContainer';
import { ISuggestion, IUserContext } from '../../interfaces';
import { BsChat } from 'react-icons/bs';
import { useContext } from 'react';
import { UserContext } from '../../context/user';
import { Client } from '../../util/client';
import { nanoid } from 'nanoid';

interface ISuggestionProps {
  suggestion: ISuggestion;
  removeSuggestion: (teacherId: number) => void;
}

const Suggestion = ({ suggestion, removeSuggestion }: ISuggestionProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  const handleOnClick = () => {
    createConnectionRequest(user.id, suggestion.teacherId);
  };

  const createConnectionRequest = (senderId: number, receiverId: number) => {
    Client.createConnectionRequest(senderId, receiverId)
      .then(() => {
        removeSuggestion(suggestion.teacherId);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  return (
    <ConnectionContainer data={suggestion}>
      <Box m="0.5rem">
        <Text textAlign="center" fontSize="0.8rem">
          Terrain in common with you
        </Text>
        <Flex flexWrap="wrap" justify="center">
          {suggestion.terrainInCommon.map((t) => {
            return (
              <Box mx="0.25rem" key={nanoid()}>
                <Text fontSize="0.7rem" color="text.primary">
                  {t}
                </Text>
              </Box>
            );
          })}
        </Flex>
        <Flex justify="center">
          <Button onClick={handleOnClick} size="sm" colorScheme="blackAlpha">
            <Box mr="0.25rem">
              <BsChat />
            </Box>
            Request Lesson
          </Button>
        </Flex>
      </Box>
    </ConnectionContainer>
  );
};

export default Suggestion;
