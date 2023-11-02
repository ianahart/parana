import { Box, Divider, Flex, Image, Text } from '@chakra-ui/react';
import { IComment, IPost, IUserContext } from '../../../interfaces';
import UserAvatar from '../../Shared/UserAvatar';
import { BsHandThumbsUpFill, BsThreeDots, BsTrash } from 'react-icons/bs';
import { useContext, useState } from 'react';
import { UserContext } from '../../../context/user';
import WritePost from './WritePost';
import { AiOutlineClose } from 'react-icons/ai';
import Actions from './Actions';
import { Client } from '../../../util/client';
import Comment from './Comment';
import { commentPaginationState } from '../../../state/initialState';

interface IPostProps {
  post: IPost;
  ownerProfileId: number;
  removePost: (postId: number) => void;
  ownerId: number;
  ownerFirstName: string;
  updatePost: (postId: number, postText: string, file: File | null, gif: string) => void;
  handleLikePost: (userId: number, postId: number, action: string) => void;
  updateLatestCommentLike: (postId: number, isLiked: boolean) => void;
}

const Post = ({
  post,
  ownerProfileId,
  removePost,
  ownerId,
  ownerFirstName,
  updatePost,
  handleLikePost,
  updateLatestCommentLike,
}: IPostProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  const [optionsOpen, setOptionsOpen] = useState(false);
  const [comments, setComments] = useState<IComment[]>([]);
  const [pagination, setPagination] = useState(commentPaginationState);

  const handleOptions = () => {
    if (user.id === ownerProfileId || user.id === post.authorId) {
      setOptionsOpen(true);
    }
  };

  const handleRemovePost = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    setOptionsOpen(false);
    removePost(post.id);
  };

  const createComment = (userId: number, postId: number, text: string) => {
    return Client.createComment(userId, postId, text, ownerId);
  };

  const getComments = (paginate: boolean) => {
    const pageNum = paginate ? pagination.page : -1;
    Client.getComments(post.id, pageNum, pagination.pageSize, pagination.direction)
      .then((res) => {
        const { comments, direction, page, pageSize, totalElements, totalPages } =
          res.data.data;
        setPagination((prevState) => ({
          ...prevState,
          direction,
          page,
          pageSize,
          totalElements,
          totalPages,
        }));
        setComments((prevState) => [...prevState, ...comments]);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const refreshComments = () => {
    setPagination(commentPaginationState);
    setComments([]);
    getComments(false);
  };

  const deleteComment = (commentId: number, ownerId: number) => {
    Client.removeComment(commentId, ownerId)
      .then(() => {
        setComments((prevState) =>
          prevState.filter((comment) => comment.id !== commentId)
        );
        refreshComments();
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const likeComment = (commentId: number, userId: number) => {
    if (post.comment !== null) {
      updateLatestCommentLike(post.id, true);
    }

    Client.likeComment(commentId, userId)
      .then(() => {
        updatedCommentLike(commentId, true);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const unlikeComment = (commentId: number, userId: number) => {
    if (post.comment !== null) {
      updateLatestCommentLike(post.id, false);
    }

    Client.removeLikeComment(commentId, userId)
      .then(() => {
        updatedCommentLike(commentId, false);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const updatedCommentLike = (commentId: number, isLiked: boolean) => {
    setComments(
      comments.map((comment) => {
        if (comment.id === commentId) {
          comment.currentUserHasLikedComment = isLiked;
          comment.likesCount = isLiked ? comment.likesCount + 1 : comment.likesCount - 1;
        }
        return comment;
      })
    );
  };

  return (
    <Box
      my="1rem"
      borderRadius={8}
      border="1px solid"
      borderColor="border.primary"
      p="1rem"
    >
      <Flex justify="space-between" align="center">
        <Flex>
          <UserAvatar
            height="45px"
            width="45px"
            avatarUrl={post.authorAvatarUrl}
            fullName={post.authorFullName}
          />
          <Box ml="0.5rem">
            <Text fontWeight="bold"> {post.authorFullName}</Text>
            <Text fontSize="0.75rem" color="text.primary">
              {post.readableDate}
            </Text>
          </Box>
        </Flex>
        <Box cursor="pointer" position="relative" onClick={handleOptions}>
          <BsThreeDots />
          {optionsOpen && (
            <Box
              p="0.5rem"
              className="fade-in"
              zIndex={5}
              borderRadius={8}
              top="15px"
              right="0"
              pos="absolute"
              bg="blackAlpha.900"
              width="180px"
              minH="240px"
            >
              <Flex
                m="0.25rem"
                justify="flex-end"
                onClick={(e) => {
                  e.stopPropagation();
                  setOptionsOpen(false);
                }}
              >
                <Box>
                  <AiOutlineClose />
                </Box>
              </Flex>
              <Flex
                onClick={handleRemovePost}
                _hover={{ bg: 'black.tertiary' }}
                borderRadius={8}
                p="0.25rem"
                align="center"
              >
                <Box mr="0.25rem">
                  <BsTrash />
                </Box>
                <Box>
                  <Text fontSize="0.85rem">Delete</Text>
                </Box>
              </Flex>
              <Divider my="0.25rem" borderColor="border.primary" />
              {user.id === post.authorId && (
                <WritePost
                  postId={post.id}
                  method="update"
                  ownerId={ownerId}
                  ownerFirstName={ownerFirstName}
                  ownerProfileId={ownerProfileId}
                  updatePost={updatePost}
                  isLoading={false}
                  postError=""
                />
              )}
            </Box>
          )}
        </Box>
      </Flex>
      <Box>
        <Text lineHeight="1.8" m="0.5rem">
          {post.text}
        </Text>
        {post.fileUrl && !post.fileUrl.endsWith('.mp4') && (
          <Flex>
            <Image borderRadius={8} width="100%" src={post.fileUrl} alt="post image" />
          </Flex>
        )}
        {post.fileUrl && post.fileUrl.endsWith('.mp4') && (
          <video style={{ borderRadius: '8px' }} controls width="100%">
            <source src={post.fileUrl}></source>
          </video>
        )}
        {post.gif && (
          <Flex justify="center">
            <Image borderRadius={8} src={post.gif} alt="a gif" />
          </Flex>
        )}
      </Box>
      <Flex my="1rem" align="center" justify="space-between">
        <Box>
          <Flex align="center">
            <Flex
              mr="0.25rem"
              height="20px"
              width="20px"
              flexDir="column"
              align="center"
              justify="center"
              borderRadius={50}
              bg="blue.500"
              fontSize="0.9rem"
            >
              <BsHandThumbsUpFill />
            </Flex>
            <Text fontSize="0.9rem" color="text.secondary">
              {post.likesCount > 0 ? post.likesCount : ''}
            </Text>
          </Flex>
        </Box>
        <Box>
          <Text>{post.commentCount > 0 ? post.commentCount : ''} comments</Text>
        </Box>
      </Flex>
      <Divider my="1rem" borderColor="border.primary" />
      <Actions
        refreshComments={refreshComments}
        createComment={createComment}
        currentUserHasLikedPost={post.currentUserHasLikedPost}
        handleLikePost={handleLikePost}
        postId={post.id}
      />
      {post.comment !== null && comments.length <= 0 && (
        <>
          <Box
            onClick={() => getComments(false)}
            cursor="pointer"
            _hover={{ opacity: 0.8 }}
          >
            <Text fontWeight="bold" fontSize="0.9rem" role="button">
              View more comments
            </Text>
          </Box>
          <Box my="0.5rem">
            <Comment
              unlikeComment={unlikeComment}
              likeComment={likeComment}
              deleteComment={deleteComment}
              ownerId={ownerId}
              comment={post.comment}
            />
          </Box>
        </>
      )}
      <Box my="0.5rem">
        {comments.map((comment) => {
          return (
            <Comment
              unlikeComment={unlikeComment}
              likeComment={likeComment}
              deleteComment={deleteComment}
              ownerId={ownerId}
              comment={comment}
              key={comment.id}
            />
          );
        })}
      </Box>

      {pagination.page < pagination.totalPages - 1 && (
        <Box my="0.5rem">
          <Box
            onClick={() => getComments(true)}
            cursor="pointer"
            _hover={{ opacity: 0.8 }}
          >
            <Text fontWeight="bold" fontSize="0.9rem" role="button">
              View more comments
            </Text>
          </Box>
        </Box>
      )}
    </Box>
  );
};

export default Post;
