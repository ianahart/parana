import {
  Box,
  Button,
  Flex,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalCloseButton,
  useDisclosure,
  ButtonGroup,
  Input,
  FormControl,
  FormLabel,
  Text,
} from '@chakra-ui/react';
import { useState } from 'react';
import { AiOutlinePlus } from 'react-icons/ai';

interface IHashTagFormProps {
  hashtags: string[];
  addHashTag: (name: string, hashtag: string) => void;
  name: string;
}

const HashTagForm = ({ hashtags, addHashTag, name }: IHashTagFormProps) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [hashtag, setHashtag] = useState('');
  const [error, setError] = useState('');

  const validateForm = (hashtag: string) => {
    const validLength = hashtag.trim().length === 0 || hashtag.length > 40;
    const maxLimit = hashtags.length >= 5;
    const exists = hashtags.includes(hashtag.toLowerCase());
    if (validLength || maxLimit || exists) {
      setError(
        'Hash tag must be between 1 and 40 characters, and must not exist already'
      );
      return false;
    }
    return true;
  };

  const handleOnSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.stopPropagation();
    e.preventDefault();
    setError('');
    if (!validateForm(hashtag)) return;
    addHashTag(name, hashtag.toLowerCase());
    setHashtag('');
  };

  return (
    <Box>
      <Flex>
        <Button onClick={onOpen} colorScheme="blackAlpha">
          <Box mr="0.25rem">
            <AiOutlinePlus />
          </Box>
          Hashtag
        </Button>
        <Modal isOpen={isOpen} onClose={onClose}>
          <ModalOverlay />
          <ModalContent bg="primary.dark">
            <ModalHeader color="text.secondary">Add a hashtag</ModalHeader>
            <ModalCloseButton color="text.secondary" />
            <ModalBody>
              <form onSubmit={handleOnSubmit}>
                {error.length > 0 && (
                  <Flex my="1rem" justify="center">
                    <Text fontSize="0.85rem" color="error.primary">
                      {error}
                    </Text>
                  </Flex>
                )}
                <FormControl color="text.secondary">
                  <FormLabel htmlFor={name}>Add Hashtag</FormLabel>
                  <Input
                    color="text.secondary"
                    value={hashtag}
                    borderColor="border.primary"
                    onChange={(e) => setHashtag(e.target.value)}
                    placeholder="Enter a hashtag"
                    _placeholder={{ color: 'text.secondary' }}
                    id={name}
                    name={name}
                  />
                </FormControl>
                <ButtonGroup display="flex" justifyContent="flex-end" my="1.5rem">
                  <Button type="submit" colorScheme="blackAlpha" size="sm">
                    Add
                  </Button>
                  <Button onClick={onClose} colorScheme="blackAlpha" size="sm">
                    Cancel
                  </Button>
                </ButtonGroup>
              </form>
            </ModalBody>
          </ModalContent>
        </Modal>
      </Flex>
    </Box>
  );
};

export default HashTagForm;
