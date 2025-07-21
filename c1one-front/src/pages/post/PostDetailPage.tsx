// import React from 'react';
// import { useParams, useNavigate } from 'react-router-dom';
// import { useGetPostQuery, useGetCommentsQuery } from '@/lib/api';
// import { PostDetailView } from '@/components/PostDetailView';

// const PostDetailPage: React.FC = () => {
//   const { id } = useParams<{ id: string }>();
//   const navigate = useNavigate(); 
//   const postId = Number(id);

//   const { data: post, isLoading: postLoading, error: postError } = useGetPostQuery(postId);
//   const { data: comments } = useGetCommentsQuery(postId);

//   if (postLoading) {
//     return (
//       <div className="flex items-center justify-center min-h-screen">
//         <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-gray-900"></div>
//       </div>
//     );
//   }

//   if (postError || !post) {
//     return (
//       <div className="flex items-center justify-center min-h-screen">
//         <div className="text-red-500">게시물을 불러오는 중 오류가 발생했습니다.</div>
//       </div>
//     );
//   }

//   return (
//     <div className="min-h-screen bg-gray-50">
//       <PostDetailView
//         post={post}
//         comments={comments || []}
//         onClose={() => navigate(-1)} // ← 뒤로가기 (onClose 실행 시)
//       />
//     </div>
//   );
// };

// export default PostDetailPage;
