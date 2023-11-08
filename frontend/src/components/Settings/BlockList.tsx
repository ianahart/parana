import {
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  useDisclosure,
  Button,
  Flex,
} from '@chakra-ui/react';
import { useContext, useEffect, useRef, useState } from 'react';
import { UserContext } from '../../context/user';
import { IPrivacy, IUserContext } from '../../interfaces';
import { blockPaginationState } from '../../state/initialState';
import { Client } from '../../util/client';
import BlockedUser from './BlockedUser';

const BlockList = () => {
  const shouldRun = useRef(true);
  const [pagination, setPagination] = useState(blockPaginationState);
  const [privacies, setPrivacies] = useState<IPrivacy[]>([]);
  const { user } = useContext(UserContext) as IUserContext;
  const { isOpen, onOpen, onClose } = useDisclosure();

  const getBlockedUsers = (paginate: boolean, userId: number) => {
    const pageNum = paginate ? pagination.page : -1;
    Client.getBlockedUsers(userId, pageNum, pagination.pageSize, pagination.direction)
      .then((res) => {
        const { direction, page, pageSize, privacies, totalElements, totalPages } =
          res.data.data;

        setPagination((prevState) => ({
          ...prevState,
          page,
          pageSize,
          direction,
          totalPages,
          totalElements,
        }));

        setPrivacies((prevState) => [...prevState, ...privacies]);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (isOpen) {
      setPrivacies([]);
      setPagination(blockPaginationState);
      getBlockedUsers(false, user.id);
    }
  }, [isOpen]);

  useEffect(() => {
    if (shouldRun.current && user.id !== 0) {
      shouldRun.current = false;
      getBlockedUsers(false, user.id);
    }
  }, [shouldRun.current, user.id]);

  const updatePrivacy = (name: string, checked: boolean, id: number) => {
    const updatedPrivacies = privacies.map((p) =>
      p.id === id ? { ...p, [name]: checked } : p
    );
    const privacyIndex = updatedPrivacies.findIndex((p) => p.id === id);
    const { messages, posts, comments } = updatedPrivacies[privacyIndex];
    const allUnChecked = [messages, posts, comments].every((p) => !p);

    if (allUnChecked) {
      setPrivacies((prevState) =>
        prevState.filter((p) => p.id !== updatedPrivacies[privacyIndex].id)
      );
    } else {
      setPrivacies(updatedPrivacies);
    }
  };

  const handleUpdatePrivacy = (name: string, checked: boolean, privacyId: number) => {
    Client.updateBlockedUser(name, checked, privacyId)
      .then(() => {
        updatePrivacy(name, checked, privacyId);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  return (
    <>
      <Button colorScheme="blackAlpha" onClick={onOpen}>
        Edit list...
      </Button>
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent
          minWidth={['90%', '90%', '600px']}
          color="text.secondary"
          bg="primary.dark"
        >
          <ModalHeader>Blocked Users</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            {privacies.map((privacy) => {
              return (
                <BlockedUser
                  handleUpdatePrivacy={handleUpdatePrivacy}
                  key={privacy.id}
                  privacy={privacy}
                />
              );
            })}
          </ModalBody>
          <ModalFooter>
            {pagination.page < pagination.totalPages - 1 && (
              <Flex my="1rem" justify="flex-start">
                <Button
                  _hover={{ bg: 'transparent', opacity: 0.8 }}
                  bg="transparent"
                  fontSize="0.9rem"
                  color="text.secondary"
                  onClick={() => getBlockedUsers(true, user.id)}
                >
                  See more
                </Button>
              </Flex>
            )}

            <Button colorScheme="blackAlpha" mr={3} onClick={onClose}>
              Close
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default BlockList;
