import { Box, Flex, Text, Switch } from '@chakra-ui/react';
import { useContext, useEffect, useRef, useState } from 'react';
import { IoMdNotificationsOutline } from 'react-icons/io';
import { Client } from '../../util/client';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
import Header from './Header';

const Notifications = () => {
  const shouldRun = useRef(true);
  const { user } = useContext(UserContext) as IUserContext;
  const [notificationsOn, setNotificationsOn] = useState(true);

  const getSettings = (settingId: number) => {
    Client.getSettings(settingId)
      .then((res) => {
        const { notifications } = res.data.data;
        setNotificationsOn(notifications);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && user.settingId !== 0) {
      shouldRun.current = false;
      getSettings(user.settingId);
    }
  }, [shouldRun.current, user.settingId]);

  const updateNotificationSetting = (isChecked: boolean, settingId: number) => {
    Client.updateNotificationSetting(isChecked, settingId)
      .then((res) => {
        console.log(res);
        setNotificationsOn(isChecked);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { checked } = e.target;
    updateNotificationSetting(checked, user.settingId);
  };

  return (
    <Box color="text.secondary">
      <Header heading="Notifications" icon={<IoMdNotificationsOutline />} />
      <Box my="3rem">
        <Flex align="center" justify="space-between">
          <Box>
            <Text fontSize="1.2rem" fontWeight="bold">
              Notification Settings
            </Text>
            <Text fontSize="0.9rem" color="text.primary">
              Toggle on and off notifications for posts and comments.
            </Text>
          </Box>
          <Box>
            <Switch
              onChange={handleOnChange}
              isChecked={notificationsOn}
              colorScheme="purple"
              size="lg"
            />
          </Box>
        </Flex>
      </Box>
    </Box>
  );
};

export default Notifications;
