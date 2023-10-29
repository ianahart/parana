import { Box, Button, ButtonGroup } from '@chakra-ui/react';
import { IConnection } from '../../interfaces';
import { BiUserMinus } from 'react-icons/bi';
import { ImProfile } from 'react-icons/im';
import { useNavigate } from 'react-router-dom';
import ConnectionContainer from './ConnectionContainer';

interface IConnectionProps {
  connection: IConnection;
  removeConnection: (connectionId: number) => void;
}

const Connection = ({ connection, removeConnection }: IConnectionProps) => {
  const navigate = useNavigate();

  const navigateToProfile = () => {
    navigate(`/profiles/${connection.profileId}`);
  };

  return (
    <ConnectionContainer data={connection}>
      <Box>
        <ButtonGroup display="flex" justifyContent="space-between" p="0.5rem">
          <Button
            size="sm"
            onClick={() => removeConnection(connection.id)}
            colorScheme="gray"
          >
            <Box>
              <BiUserMinus />
            </Box>
            Remove
          </Button>
          <Button onClick={navigateToProfile} size="sm" colorScheme="telegram">
            <Box>
              <ImProfile />
            </Box>
            Profile
          </Button>
        </ButtonGroup>
      </Box>
    </ConnectionContainer>
  );
};

export default Connection;
