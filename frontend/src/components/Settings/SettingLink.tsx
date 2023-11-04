import { Box, ListItem, Flex, Text } from '@chakra-ui/react';
import { BsChevronRight } from 'react-icons/bs';
import { Link as RouterLink } from 'react-router-dom';

interface IConnectionLinkProps {
  path: string;
  value: string;
  text: string;
  selected: string;
  icon: React.ReactNode;
  setSelected: (selected: string) => void;
}

const SettingLink = ({
  path,
  value,
  text,
  selected,
  icon,
  setSelected,
}: IConnectionLinkProps) => {
  return (
    <ListItem
      borderRadius={8}
      p="0.25rem"
      _hover={{ background: 'blackAlpha.900' }}
      onClick={() => setSelected(value)}
      my="1.5rem"
    >
      <RouterLink to={path}>
        <Flex align="center" justify="space-between">
          <Flex align="center">
            <Flex
              height="40px"
              width="40px"
              borderRadius={50}
              bg={selected === value ? 'primary.light' : 'blackAlpha.900'}
              flexDir="column"
              align="center"
              justify="center"
              fontSize="1.4rem"
              mr="0.35rem"
            >
              {icon}
            </Flex>
            <Text fontSize="1.1rem">{text}</Text>
          </Flex>
          <Box fontSize="1.2rem">
            <BsChevronRight />
          </Box>
        </Flex>
      </RouterLink>
    </ListItem>
  );
};

export default SettingLink;
