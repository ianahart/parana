import { Box, Flex, Heading } from '@chakra-ui/react';

interface IHeaderProps {
  heading: string;
  icon: React.ReactNode;
}

const Header = ({ heading, icon }: IHeaderProps) => {
  return (
    <Box as="header">
      <Flex align="center">
        <Flex mr="0.25rem" fontSize="1.2rem">
          {icon}
        </Flex>
        <Heading fontSize="1.2rem">{heading}</Heading>
      </Flex>
    </Box>
  );
};

export default Header;
