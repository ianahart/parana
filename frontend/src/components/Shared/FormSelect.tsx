import { Box, Flex, FormLabel, Input, Text, useOutsideClick } from '@chakra-ui/react';
import { BsChevronDown } from 'react-icons/bs';
import { nanoid } from 'nanoid';
import { useRef, useState } from 'react';
import { AiOutlineCheck } from 'react-icons/ai';

interface IFormSelectProps {
  updateField: (name: string, value: string, attribute: string) => void;
  options: string[];
  name: string;
  value: string;
  height: string;
  minWidth: string[];
  label: string;
}

const FormSelect = ({
  updateField,
  options,
  name,
  value,
  height,
  minWidth,
  label,
}: IFormSelectProps) => {
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [filteredOptions, setFilteredOptions] = useState<string[]>([...options]);
  const [searchValue, setSearchValue] = useState('');

  const ref = useRef<HTMLDivElement>(null);
  useOutsideClick({
    ref,
    handler: () => setDropdownOpen(false),
  });

  const handleOnClick = (option: string) => {
    updateField(name, option, 'value');
    setDropdownOpen(false);
  };

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    setSearchValue(value);
    const filtered = options.filter((option) => {
      if (option.toLowerCase().includes(searchValue.toLowerCase())) {
        return option;
      }
    });
    setFilteredOptions(filtered);
  };

  return (
    <Box position="relative" my="1.5rem">
      <FormLabel>{label}</FormLabel>
      <Flex
        minW={minWidth}
        onClick={() => setDropdownOpen(true)}
        p="0.25rem"
        pr="1rem"
        pl="1.5rem"
        align="center"
        height="40px"
        borderRadius={6}
        border="1px solid"
        borderColor="border.primary"
        justify="space-between"
      >
        <Text>{value}</Text>
        <Box>
          <BsChevronDown />
        </Box>
      </Flex>
      {dropdownOpen && (
        <Box
          ref={ref}
          zIndex={8}
          className="overflow-scroll"
          overflowY="auto"
          bg="blackAlpha.900"
          height={height}
          borderRadius={6}
          p="0.5rem"
          pos="absolute"
          top="70px"
          width="100%"
        >
          <Input
            value={searchValue}
            onChange={handleOnChange}
            placeholder="Search..."
            border="none"
            borderBottom="1px solid"
            borderRadius={0}
            borderColor="border.primary"
          />
          {filteredOptions.map((option) => {
            return (
              <Flex
                bg={option === value ? 'primary.light' : 'transparent'}
                align="center"
                key={nanoid()}
                onClick={() => handleOnClick(option)}
                cursor="pointer"
                p="0.5rem"
                _hover={{ background: 'primary.light' }}
                my="0.5rem"
              >
                {value === option && (
                  <Box mr="0.25rem">
                    <AiOutlineCheck />
                  </Box>
                )}
                <Text>{option}</Text>
              </Flex>
            );
          })}
        </Box>
      )}
    </Box>
  );
};

export default FormSelect;
