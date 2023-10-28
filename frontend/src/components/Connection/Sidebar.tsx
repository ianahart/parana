import { Box, Flex, Heading, UnorderedList } from '@chakra-ui/react';
import ConnectionLink from './ConnectionLink';
import { PiUsersThree } from 'react-icons/pi';
import { useState } from 'react';
import { TbUserUp } from 'react-icons/tb';
import { AiOutlineHeart } from 'react-icons/ai';

const Sidebar = () => {
  const [selected, setSelected] = useState('all');

  return (
    <Box m="1rem">
      <Flex mb="2rem">
        <Heading fontSize="1.4rem" color="text.secondary">
          Connections
        </Heading>
      </Flex>
      <Box as="nav" color="text.secondary">
        <UnorderedList listStyleType="none">
          <ConnectionLink
            setSelected={setSelected}
            path="requests"
            text="Requests"
            value="requests"
            selected={selected}
            icon={<TbUserUp />}
          />
          <ConnectionLink
            setSelected={setSelected}
            path="all"
            text="All Connections"
            value="all"
            selected={selected}
            icon={<PiUsersThree />}
          />
          <ConnectionLink
            setSelected={setSelected}
            path="favorites"
            text="Favorites"
            value="favorites"
            selected={selected}
            icon={<AiOutlineHeart />}
          />
        </UnorderedList>
      </Box>
    </Box>
  );
};

export default Sidebar;
