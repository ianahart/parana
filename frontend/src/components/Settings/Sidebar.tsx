import { Box, Flex, Heading, UnorderedList } from '@chakra-ui/react';
import { IoSettingsOutline } from 'react-icons/io5';
import { AiOutlineLock } from 'react-icons/ai';
import { BsShieldLock } from 'react-icons/bs';
import { IoMdNotificationsOutline } from 'react-icons/io';
import SettingLink from './SettingLink';
import { useEffect, useRef, useState } from 'react';
import { useLocation } from 'react-router-dom';

const Sidebar = () => {
  const shouldRun = useRef(true);
  const [selected, setSelected] = useState('/settings/notifications');
  const location = useLocation();

  useEffect(() => {
    if (shouldRun.current) {
      shouldRun.current = false;
      setSelected(location.pathname);
    }
  }, [shouldRun.current, location.pathname]);

  return (
    <Box m="1rem">
      <Flex mb="2rem">
        <Heading fontSize="1.4rem" color="text.secondary">
          Settings
        </Heading>
      </Flex>
      <Box as="nav" color="text.secondary">
        <UnorderedList listStyleType="none">
          <SettingLink
            setSelected={setSelected}
            path="general"
            text="General"
            value="/settings/general"
            selected={selected}
            icon={<IoSettingsOutline />}
          />
          <SettingLink
            setSelected={setSelected}
            path="security"
            text="Security & Login"
            value="/settings/security"
            selected={selected}
            icon={<AiOutlineLock />}
          />
          <SettingLink
            setSelected={setSelected}
            path="privacy"
            text="User Privacy"
            value="/settings/privacy"
            selected={selected}
            icon={<BsShieldLock />}
          />
          <SettingLink
            setSelected={setSelected}
            path="notifications"
            text="Notifications"
            value="/settings/notifications"
            selected={selected}
            icon={<IoMdNotificationsOutline />}
          />
        </UnorderedList>
      </Box>
    </Box>
  );
};

export default Sidebar;
