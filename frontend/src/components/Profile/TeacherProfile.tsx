import { Box, Flex } from '@chakra-ui/react';
import { IProfile } from '../../interfaces';
import Terrain from './Terrain';
import Biography from './Biography';
import LessonLocation from './LessonLocation';
import AboutLesson from './AboutLesson';
import Tags from './Tags';
import TeacherHero from './TeacherHero';

interface ITeacherProfileProps {
  profile: IProfile;
}

const TeacherProfile = ({ profile }: ITeacherProfileProps) => {
  return (
    <Flex className="teacher-profile-container" color="text.secondary">
      <Flex p="0.5rem" flexDir="column" flexGrow={2} minH="100vh">
        <Terrain terrain={profile.terrain} heading="Terrain" />
        <LessonLocation
          city={profile.city}
          state={profile.state}
          travelUpTo={profile.travelUpTo}
        />
        <Biography firstName={profile.firstName} bio={profile.bio} />
        <AboutLesson level="All Levels" lang="English" about={profile.aboutLesson} />
        <Tags tags={profile.tags} heading="Hashtags" />
      </Flex>
      <Box
        bg="primary.bg"
        borderRadius={8}
        boxShadow="primary.boxShadow"
        minW="350px"
        p="0.5rem"
        height="100%"
      >
        <TeacherHero profile={profile} />
      </Box>
    </Flex>
  );
};

export default TeacherProfile;
