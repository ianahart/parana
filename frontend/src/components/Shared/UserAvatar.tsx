import { Flex } from '@chakra-ui/react';
import { abbreviate } from '../../util';

interface IUserAvatarProps {
  avatarUrl: string | null;
  fullName: string;
  width: string;
  height: string;
}

const UserAvatar = ({ avatarUrl, fullName, width, height }: IUserAvatarProps) => {
  return (
    <Flex
      justifyContent="center"
      alignItems="center"
      height={height}
      width={width}
      color="light.primary"
      borderRadius="50%"
      bg="primary.light"
      backgroundImage={avatarUrl ? `url(${avatarUrl})` : 'none'}
      backgroundPosition="center"
      backgroundSize="cover"
    >
      {!avatarUrl ? abbreviate(fullName) : ''}
    </Flex>
  );
};

export default UserAvatar;
