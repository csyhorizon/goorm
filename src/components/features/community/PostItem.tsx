'use client';

import Link from "next/link";
import Image from "next/image";
import { PostResponse } from "@/lib/apis/post.api";// API 타입 경로

const CommentIcon = () => <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z" stroke="#868e96" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"></path></svg>;
const HeartIcon = () => <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z" stroke="#868e96" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"></path></svg>;

export default function PostItem({ post }: { post: PostResponse }) {
    const thumbnailUrl = post.mediaUrls && post.mediaUrls.length > 0
        ? post.mediaUrls[0]
        : '/file.svg';

    return (
        <Link href={`/community/${post.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
            <article style={{ display: 'flex', padding: '16px', borderBottom: '1px solid #eaeaea', cursor: 'pointer' }}>
                {/* 썸네일 이미지 */}
                <div style={{ width: '100px', height: '100px', marginRight: '16px', flexShrink: 0 }}>
                    <Image
                        src={thumbnailUrl}
                        alt={post.title}
                        width={100}
                        height={100}
                        style={{ width: '100%', height: '100%', objectFit: 'cover', borderRadius: '8px', backgroundColor: '#f0f0f0' }}
                        onError={(e) => { e.currentTarget.src = '/default-thumbnail.png'; }} // 이미지 로드 실패 시
                    />
                </div>

                {/* 게시글 정보 */}
                <div style={{ flex: 1, display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
                    <div>
                        <h3 style={{ margin: 0, fontSize: '1rem', color: '#212529', fontWeight: 'normal' }}>
                            {post.title}
                        </h3>
                        <p style={{ margin: '4px 0 0', fontSize: '0.8rem', color: '#868e96' }}>
                            {post.location}
                        </p>
                    </div>

                    {/* 작성자 정보 (가격, 댓글/좋아요 수 대신 표시) */}
                    <div style={{ marginTop: 'auto', display: 'flex', justifyContent: 'flex-end' }}>
                        <span style={{ fontSize: '0.8rem', color: '#495057' }}>
                            {post.memberName}
                        </span>
                    </div>
                </div>
            </article>
        </Link>
    );
}