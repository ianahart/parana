import { Modal, ModalBody, ModalContent, ModalOverlay } from '@chakra-ui/react';

interface IFilterContainerProps {
  children: React.ReactNode;
  isOpen: boolean;
  onClose: () => void;
}

const FilterContainer = ({ children, isOpen, onClose }: IFilterContainerProps) => {
  return (
    <Modal isCentered onClose={onClose} isOpen={isOpen}>
      <ModalOverlay />
      <ModalContent p="1rem" bg="primary.dark">
        <ModalBody>{children}</ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default FilterContainer;
