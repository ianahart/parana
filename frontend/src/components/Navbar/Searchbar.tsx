import { Box, Input } from '@chakra-ui/react';
import { useState } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';

const Searchbar = () => {
  const [searchValue, setSearchValue] = useState('');

  return (
    <Box display={['none', 'none', 'block']} position="relative">
      <Input
        value={searchValue}
        onChange={(e) => setSearchValue(e.target.value)}
        borderRadius={20}
        color="text.secondary"
        pl="2rem"
        placeholder="Search Teachers..."
        _placeholder={{ color: 'text.secondary' }}
        borderColor="border.primary"
        mx="1rem"
        width={['100%', '100%', '400px']}
      />
      {!searchValue.length && (
        <Box
          position="absolute"
          top="9px"
          left="20px"
          color="border.primary"
          fontSize="1.7rem"
        >
          <AiOutlineSearch />
        </Box>
      )}
    </Box>
  );
};

export default Searchbar;
