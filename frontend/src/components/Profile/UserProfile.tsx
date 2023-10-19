import { Box, Flex, Heading, Text } from '@chakra-ui/react';
import { IProfile } from '../../interfaces';
import Terrain from './Terrain';
import Biography from './Biography';
import UserAvatar from '../Shared/UserAvatar';
import Travel from './Travel';

interface IUserProfileProps {
  profile: IProfile;
}

const UserProfile = ({ profile }: IUserProfileProps) => {
  console.log(profile);
  return (
    <Flex className="teacher-profile-container" color="text.secondary">
      <Flex p="0.5rem" flexDir="column" flexGrow={2} minH="100vh">
        <Terrain terrain={profile.terrain} heading="Terrain" />
        <Box my="1rem">
          <Biography firstName={profile.firstName} bio={profile.bio} />
        </Box>
        <Box my="1rem">
          <Travel
            homeMountain={profile.homeMountain}
            state={profile.state}
            city={profile.city}
            travelUpTo={profile.travelUpTo}
          />
        </Box>
        <Box my="1rem">
          <Heading as="h3" fontSize="1.5rem">
            Stance
          </Heading>
          <Text mt="0.25rem" fontWeight="bold">
            {profile.stance}
          </Text>
        </Box>
        <Box my="1rem">
          <Heading as="h3" fontSize="1.5rem">
            Years Snowboarding
          </Heading>
          <Text mt="0.25rem" fontWeight="bold">
            {profile.yearsSnowboarding} year(s)
          </Text>
        </Box>
      </Flex>
      <Box
        bg="primary.bg"
        borderRadius={8}
        boxShadow="primary.boxShadow"
        minW="350px"
        p="0.5rem"
        height="100%"
      >
        <Flex mx="auto" align="center" flexDir="column">
          <UserAvatar
            width="160px"
            height="160px"
            fullName={profile.fullName}
            avatarUrl={profile.avatarUrl}
          />
          <Flex justify="center" my="0.5rem">
            <Text fontWeight="bold" fontSize="1.3rem">
              {profile.firstName}
            </Text>
          </Flex>
        </Flex>
      </Box>
    </Flex>
  );
};

export default UserProfile;
