import { Box, Flex, Text } from '@chakra-ui/react';
import { useContext, useEffect, useRef, useState } from 'react';
import { BsImage } from 'react-icons/bs';
import { IoTextOutline } from 'react-icons/io5';
import PhotoForm from './PhotoForm';
import TextForm from './TextForm';
import { IStoryForm, IUserContext } from '../../interfaces';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import { UserContext } from '../../context/user';
import { Client } from '../../util/client';

let stompClient: any = null;

const CreateStory = () => {
  const shouldRun = useRef(true);
  const { user } = useContext(UserContext) as IUserContext;
  const [storyType, setStoryType] = useState('');

  useEffect(() => {
    if (shouldRun.current) {
      connect();
      shouldRun.current = false;
    }
  }, [shouldRun.current]);

  useEffect(() => {
    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, []);

  const connect = () => {
    let Sock = new SockJS('https://parana-hart-6c0dd51d52f9.herokuapp.com/wss');
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  const sendStory = (form: IStoryForm) => {
    const data = {
      text: form.text.value,
      fontSize: form.fontSize.value,
      duration: form.duration.value,
      color: form.color.value,
      background: form.background.value,
      alignment: form.alignment.value,
      userId: user.id,
      type: 'text',
    };

    if (stompClient) {
      stompClient.send('/api/v1/private-stories', {}, JSON.stringify(data));
    }
  };

  const createStory = (form: IStoryForm) => {
    const formData = new FormData();
    formData.append('duration', form.duration.value);
    formData.append('userId', user.id.toString());
    formData.append('type', 'photo');
    if (form.file.value !== null) {
      formData.append('file', form.file.value);
    }
    Client.createStory(formData)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const onConnected = () => {
    console.log('connected');
  };
  const onError = () => {};

  const resetStoryType = () => {
    setStoryType('');
  };

  const handleCreateStory = (form: IStoryForm) => {
    if (form.file.value !== null) {
      createStory(form);
    } else {
      sendStory(form);
    }
  };

  return (
    <Box m="1rem" color="text.secondary" maxW="1480px" width="100%">
      {storyType.length === 0 && (
        <Flex
          flexDir={['column', 'column', 'row']}
          justify={['unset', 'center', 'center']}
        >
          <Flex
            cursor="pointer"
            alignSelf={['center', 'center', 'unset']}
            onClick={() => setStoryType('photo')}
            dir="column"
            align="center"
            justify="center"
            m="1rem"
            bg="radial-gradient(circle, rgba(34,193,195,1) 0%, rgba(253,187,45,1) 100%)"
            minH="600px"
            width={['95%', '300px', '300px']}
            borderRadius={8}
          >
            <Box textAlign="center">
              <Flex
                mx="auto"
                borderRadius={50}
                height="40px"
                width="40px"
                bg="black.tertiary"
                dir="column"
                align="center"
                fontSize="1.3rem"
                justify="center"
              >
                <BsImage />
              </Flex>
              <Text fontSize="1.2rem" fontWeight="bold" color="black.tertiary">
                Photo
              </Text>
            </Box>
          </Flex>
          <Flex
            cursor="pointer"
            alignSelf={['center', 'center', 'unset']}
            onClick={() => setStoryType('text')}
            dir="column"
            align="center"
            justify="center"
            m="1rem"
            bg="radial-gradient(circle, rgba(128,90,213,1) 29%, rgba(213,63,140,1) 100%)"
            minH="600px"
            width={['95%', '300px', '300px']}
            borderRadius={8}
          >
            <Box>
              <Flex
                borderRadius={50}
                height="40px"
                width="40px"
                bg="black.tertiary"
                mx="auto"
                dir="column"
                fontSize="1.3rem"
                align="center"
                justify="center"
              >
                <IoTextOutline />
              </Flex>

              <Text fontSize="1.2rem" fontWeight="bold" color="black.tertiary">
                Text
              </Text>
            </Box>
          </Flex>
        </Flex>
      )}

      {storyType === 'photo' && (
        <PhotoForm
          handleCreateStory={handleCreateStory}
          resetStoryType={resetStoryType}
        />
      )}
      {storyType === 'text' && (
        <TextForm handleCreateStory={handleCreateStory} resetStoryType={resetStoryType} />
      )}
    </Box>
  );
};

export default CreateStory;
