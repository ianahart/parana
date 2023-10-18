import { Box, Flex, FormLabel, Text, Tooltip } from '@chakra-ui/react';
import { nanoid } from 'nanoid';
import { AiOutlineCheck, AiOutlineClose } from 'react-icons/ai';

interface IFormListProps {
  options: string[];
  label: string;
  name: string;
  selectedOptions: string[];
  addOptionToList: (name: string, option: string) => void;
  removeOptionFromList: (name: string, option: string) => void;
}

const FormList = ({
  name,
  options,
  label,
  selectedOptions,
  addOptionToList,
  removeOptionFromList,
}: IFormListProps) => {
  const selectOption = (option: string) => {
    addOptionToList(name, option);
  };

  const deselectOption = (option: string) => {
    removeOptionFromList(name, option);
  };

  return (
    <Box color="text.secondary">
      <FormLabel>{label}</FormLabel>
      <Text color="text.primary" fontSize="0.85rem" my="0.5rem">
        Select all that apply to you
      </Text>
      <Flex flexWrap="wrap">
        {options.map((option) => {
          return (
            <Box
              onClick={() => selectOption(option)}
              cursor="pointer"
              p="0.35rem"
              borderRadius={20}
              border="1px solid"
              borderColor="border.primary"
              fontSize="0.9rem"
              mx="1rem"
              my="0.5rem"
              key={nanoid()}
            >
              <Text>{option}</Text>
            </Box>
          );
        })}
      </Flex>
      <Box my="1rem">
        {selectedOptions.length === 0 && (
          <Box textAlign="center">
            <Text fontSize="0.85rem" color="text.primary">
              You currently no selected terrains
            </Text>
          </Box>
        )}
        {selectedOptions.map((selectedOption) => {
          return (
            <Flex my="0.5rem" justify="space-between" align="center" key={nanoid()}>
              <Flex align="center">
                <Box mr="0.25rem">
                  <AiOutlineCheck />
                </Box>
                <Text fontSize="0.9rem">{selectedOption}</Text>
              </Flex>
              <Tooltip label="Remove terrain" placement="top-start">
                <Box onClick={() => deselectOption(selectedOption)} cursor="pointer">
                  <AiOutlineClose />
                </Box>
              </Tooltip>
            </Flex>
          );
        })}
      </Box>
    </Box>
  );
};

export default FormList;
