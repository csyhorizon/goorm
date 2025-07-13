import React, { useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { updatePost, PostUpdateRequest } from '@/lib/postApi';
import { Button } from '@/components/ui/button';

interface PostEditModalProps {
  postId: number;
  initialContent: string;
  initialLocation: string;
  initialImages: string[];
  onClose: () => void;
}

const PostEditModal: React.FC<PostEditModalProps> = ({
  postId,
  initialContent,
  initialLocation,
  initialImages,
  onClose,
}) => {
  const queryClient = useQueryClient();

  const [content, setContent] = useState(initialContent);
  const [location, setLocation] = useState(initialLocation);
  const [remainImageUrls, setRemainImageUrls] = useState(initialImages);

  const S3_BASE_URL = 'https://uniqrambucket.s3.ap-northeast-2.amazonaws.com/';

  const mutation = useMutation({
    mutationFn: (data: PostUpdateRequest) => updatePost(postId, data),
    onSuccess: () => {
      alert('게시물이 수정되었습니다!');
      queryClient.invalidateQueries({ queryKey: ['postDetail', postId] });
      queryClient.invalidateQueries({ queryKey: ['userPosts'] });
      onClose();
    },
    onError: (error: any) => {
      alert(`수정 실패: ${error.message}`);
    },
  });

  const handleImageToggle = (url: string) => {
    if (initialImages.length <= 2) {
      alert('이미지가 2개 이하일 때는 삭제할 수 없습니다.');
      return;
    }

    setRemainImageUrls((prev) =>
      prev.includes(url)
        ? prev.filter((item) => item !== url)
        : [...prev, url]
    );
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    const imagesChanged = remainImageUrls.length !== initialImages.length;

    if (initialImages.length >= 3 && imagesChanged && remainImageUrls.length < 2) {
      alert('이미지는 최소 2장 이상 남겨야 합니다.');
      return;
    }

    mutation.mutate({
      content,
      location,
      remainImageUrls,
    });
  };

  return (
    <div className="fixed inset-0 bg-black/70 flex items-center justify-center z-50">
      <div className="bg-gray-800 p-6 rounded-lg w-full max-w-lg relative">
        <button onClick={onClose} className="absolute top-2 right-2 text-gray-500">
          ✕
        </button>
        <h2 className="text-xl font-semibold mb-4 text-white">게시물 수정</h2>

        <form onSubmit={handleSubmit} className="space-y-4">
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="w-full border p-2 rounded text-black bg-white"
            rows={3}
            placeholder="내용을 수정하세요"
          />
          <input
            type="text"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
            className="w-full border p-2 rounded text-black bg-white"
            placeholder="위치"
          />

          <div className="grid grid-cols-3 gap-2">
            {initialImages.map((url) => {
              const fullUrl = url.startsWith('http') ? url : `${S3_BASE_URL}${url}`;
              const isActive = remainImageUrls.includes(url);
              return (
                <div
                  key={url}
                  className={`relative border cursor-pointer ${
                    isActive ? 'border-green-500' : 'border-red-500 opacity-50'
                  }`}
                  onClick={() => handleImageToggle(url)}
                >
                  <img src={fullUrl} alt="media" className="object-cover w-full h-24 rounded" />
                  {!isActive && (
                    <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center text-white text-sm">
                      삭제됨
                    </div>
                  )}
                </div>
              );
            })}
          </div>

          <Button type="submit" disabled={mutation.isPending} className="w-full">
            {mutation.isPending ? '수정 중...' : '수정 완료'}
          </Button>
        </form>
      </div>
    </div>
  );
};

export default PostEditModal;
