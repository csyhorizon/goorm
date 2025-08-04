'use client';

import { useState, useEffect, FormEvent, ChangeEvent } from 'react';
import { useRouter } from 'next/navigation';
import { createPost } from '@/lib/apis/post.api';

export default function CreatePostPage() {
    const router = useRouter();
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [location, setLocation] = useState('');
    const [storeId, setStoreId] = useState('');
    const [images, setImages] = useState<File[]>([]);
    const [imagePreviews, setImagePreviews] = useState<string[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleImageChange = (e: ChangeEvent<HTMLInputElement>) => {
        const files = e.target.files;
        if (files) {
            const fileArray = Array.from(files);
            setImages(fileArray);

            const previewArray = fileArray.map(file => URL.createObjectURL(file));
            // 이전 미리보기가 있다면 메모리 누수 방지를 위해 해제
            imagePreviews.forEach(preview => URL.revokeObjectURL(preview));
            setImagePreviews(previewArray);
        }
    };

    // 컴포넌트가 언마운트될 때 생성된 Object URL을 해제하여 메모리 누수를 방지합니다.
    useEffect(() => {
        return () => {
            imagePreviews.forEach(preview => URL.revokeObjectURL(preview));
        };
    }, [imagePreviews]);

    // 폼 제출 핸들러
    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!title || !content || !storeId || images.length === 0) {
            setError('제목, 내용, 가게 ID, 이미지는 필수 항목입니다.');
            return;
        }

        setIsLoading(true);
        setError(null);

        try {
            const postCreateRequest = {
                title,
                content,
                location,
                storeId: Number(storeId),
            };

            const response = await createPost(postCreateRequest, images);
            alert('게시글이 성공적으로 등록되었습니다.');
            // 성공 시 생성된 게시글 상세 페이지나 목록 페이지로 이동합니다.
            router.push(`/community/${response.id}`);
        } catch (err) {
            setError('게시글 등록에 실패했습니다. 다시 시도해주세요.');
            console.error(err);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div style={{ maxWidth: '768px', margin: '40px auto', padding: '20px' }}>
            <h1>게시글 작성</h1>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                <div>
                    <label htmlFor="title">제목</label>
                    <input
                        id="title"
                        type="text"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                        style={inputStyle}
                    />
                </div>
                <div>
                    <label htmlFor="content">내용</label>
                    <textarea
                        id="content"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        required
                        rows={10}
                        style={inputStyle}
                    />
                </div>
                <div>
                    <label htmlFor="location">위치 (선택)</label>
                    <input
                        id="location"
                        type="text"
                        value={location}
                        onChange={(e) => setLocation(e.target.value)}
                        style={inputStyle}
                    />
                </div>
                <div>
                    <label htmlFor="storeId">가게 ID</label>
                    {/* 실제로는 검색이나 다른 방식으로 storeId를 가져오는 것이 좋습니다. */}
                    <input
                        id="storeId"
                        type="number"
                        value={storeId}
                        onChange={(e) => setStoreId(e.target.value)}
                        required
                        style={inputStyle}
                    />
                </div>
                <div>
                    <label htmlFor="images">이미지 (최대 5개)</label>
                    <input
                        id="images"
                        type="file"
                        multiple
                        accept="image/*"
                        onChange={handleImageChange}
                        required
                        style={inputStyle}
                    />
                </div>
                {/* 이미지 미리보기 */}
                <div style={{ display: 'flex', gap: '10px', flexWrap: 'wrap' }}>
                    {imagePreviews.map((preview, index) => (
                        <img key={index} src={preview} alt="preview" style={{ width: '100px', height: '100px', objectFit: 'cover' }} />
                    ))}
                </div>

                {error && <p style={{ color: 'red' }}>{error}</p>}

                <button type="submit" disabled={isLoading} style={buttonStyle}>
                    {isLoading ? '등록 중...' : '게시글 등록'}
                </button>
            </form>
        </div>
    );
}

// 간단한 스타일 객체
const inputStyle = {
    width: '100%',
    padding: '8px',
    marginTop: '4px',
    boxSizing: 'border-box' as 'border-box',
    border: '1px solid #ccc',
    borderRadius: '4px',
};

const buttonStyle = {
    padding: '12px 20px',
    backgroundColor: '#ff6f0f',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '1rem',
};