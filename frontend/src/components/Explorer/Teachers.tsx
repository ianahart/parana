import { Box, Grid, GridItem } from '@chakra-ui/react';
import { ITeacher } from '../../interfaces';
import Teacher from './Teacher';

interface ITeachersProps {
  teachers: ITeacher[];
}

const Teachers = ({ teachers }: ITeachersProps) => {
  return (
    <Grid className="teachers-grid" gap={6}>
      {teachers.map((teacher) => {
        return (
          <GridItem key={teacher.userId}>
            <Teacher data={teacher} />
          </GridItem>
        );
      })}
    </Grid>
  );
};

export default Teachers;
