import { Box, Card, CardBody, CardFooter } from '@chakra-ui/react';
import { ITeacher } from '../../interfaces';
import { useNavigate } from 'react-router-dom';
import HeroImage from './HeroImage';
import TeacherInformation from './TeacherInformation';
import ReviewStat from './ReviewStat';
import Bio from './Bio';
import Attributes from './Attributes';

interface ITeacherProps {
  data: ITeacher;
}

const Teacher = ({ data }: ITeacherProps) => {
  const navigate = useNavigate();

  const goToProfile = () => {
    navigate(`/profiles/${data.profileId}`);
  };

  return (
    <Card onClick={goToProfile} bg="blackAlpha.500" cursor="pointer">
      <CardBody>
        <Box position="relative">
          <HeroImage firstName={data.firstName} avatarUrl={data.avatarUrl} />
          <TeacherInformation
            firstName={data.firstName}
            city={data.city}
            state={data.state}
          />
        </Box>
      </CardBody>
      <CardFooter display="block">
        <ReviewStat />
        <Bio bio={data.bio} />
        <Attributes
          isNewTeacher={data.isNewTeacher}
          firstLessonFree={data.firstLessonFree}
          perHour={data.perHour}
        />
      </CardFooter>
    </Card>
  );
};

export default Teacher;
