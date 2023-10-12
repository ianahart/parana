import { Box, Text } from '@chakra-ui/react';

const Footer = () => {
  const year = new Date().getFullYear();

  return (
    <Box color="light.primary" className="footer" textAlign="center" p="0.5rem">
      <Text>Pirana &copy; {year}</Text>
    </Box>
  );
};

export default Footer;
