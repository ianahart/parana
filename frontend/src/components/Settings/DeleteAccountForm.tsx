import {
  Box,
  Button,
  Flex,
  Input,
  Text,
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  Fade,
} from '@chakra-ui/react';
import { useContext, useState } from 'react';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
import { Client } from '../../util/client';
import { useNavigate } from 'react-router-dom';
import BasicSpinner from '../Shared/BasicSpinner';

const DeleteAccountForm = () => {
  const navigate = useNavigate();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const { user, logout } = useContext(UserContext) as IUserContext;
  const [messageTest, setMessageTest] = useState('');
  const [testCompleted, setTestCompleted] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const test = `permanently delete ${user.fullName.toLowerCase()}`;

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>, test: string) => {
    const { value } = e.target;
    setMessageTest(value);

    if (value === test) {
      setTestCompleted(true);
    }

    if (value !== test && testCompleted) {
      setTestCompleted(false);
    }
  };

  const deleteAccount = (userId: number) => {
    setIsLoading(true);
    Client.deleteAccount(userId)
      .then(() => {
        setIsLoading(false);
        logout();
        handleOnClose();
        navigate('/login');
      })
      .catch((err) => {
        setIsLoading(false);
        throw new Error(err);
      });
  };

  const handleOnClose = () => {
    onClose();
    setTestCompleted(false);
    setMessageTest('');
  };

  return (
    <Box mt="3rem">
      <Box>
        <Button
          cursor="unset"
          disabled
          _hover={{ bg: 'transparent' }}
          colorScheme="yellow"
          variant="outline"
        >
          Danger Zone
        </Button>
      </Box>
      <Flex
        textAlign={['center', 'left', 'left']}
        justify="space-between"
        my="2rem"
        flexDir={['column', 'column', 'row']}
      >
        <Box>
          <Text fontWeight="bold">Delete Account</Text>
        </Box>
        <Box>
          <Text color="text.primary" fontSize="0.85rem" width={['95%', '95%', '450px']}>
            All of your most senstive data will be removed for your privacy. Only a subset
            of information will reside as it is needed to make sure this application runs
            smoothly. e.g. messages between you and a contact of yours.
          </Text>
        </Box>
      </Flex>
      <Flex flexDir="column" my="1rem" align="center">
        <Text color="text.primary" fontSize="0.85rem">
          Please type this into the box below:
        </Text>
        <Text
          fontSize="0.8rem"
          fontWeight="bold"
          color={testCompleted ? 'error.primary' : 'text.primary'}
        >
          {test}
        </Text>
      </Flex>
      <Box>
        <Input
          value={messageTest}
          onChange={(e) => handleOnChange(e, test)}
          fontSize="0.9rem"
          _placeholder={{ fontSize: '0.9rem' }}
          placeholder="Type here..."
          borderColor="border.primary"
        />
      </Box>
      <Fade in={testCompleted}>
        <Flex my="1rem" justify={['center', 'flex-end', 'flex-end']}>
          <Button
            onClick={onOpen}
            width={['100%', '200px', '200px']}
            colorScheme="blackAlpha"
          >
            Delete
          </Button>
        </Flex>
      </Fade>

      <Modal isOpen={isOpen} onClose={handleOnClose}>
        <ModalOverlay />
        <ModalContent bg="primary.dark" color="text.secondary">
          <ModalHeader>Confirm Delete Account</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text mb="0.25rem">Are you sure you want to delete your account?</Text>
            <Text fontSize="0.85rem" color="text.primary">
              There is no going back after you proceed with this action.
            </Text>
          </ModalBody>
          {isLoading && (
            <Flex justify="center" my="2rem">
              <BasicSpinner
                color="text.secondary"
                message="Deleting account... Please wait."
              />
            </Flex>
          )}
          {!isLoading && (
            <ModalFooter>
              <Button
                colorScheme="blackAlpha"
                mr={3}
                onClick={() => deleteAccount(user.id)}
              >
                Proceed
              </Button>
              <Button onClick={handleOnClose} colorScheme="gray">
                Cancel
              </Button>
            </ModalFooter>
          )}
        </ModalContent>
      </Modal>
    </Box>
  );
};

export default DeleteAccountForm;
