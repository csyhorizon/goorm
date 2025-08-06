'use client';

import Link from "next/link";
import Image from "next/image";
import { PostResponse } from "@/lib/apis/post.api";

export default function PostItem({ post }: { post: PostResponse }) {
    const thumbnailUrl = post.mediaUrls && post.mediaUrls.length > 0
        ? post.mediaUrls[0]
        : '/file.svg';

    return (
        <Link href={`/community/${post.storeId}/${post.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
            <article style={{ display: 'flex', padding: '16px', borderBottom: '1px solid #eaeaea', cursor: 'pointer' }}>
                <div style={{ width: '100px', height: '100px', marginRight: '16px', flexShrink: 0 }}>
                    <Image
                        src={thumbnailUrl}
                        alt={post.title}
                        width={100}
                        height={100}
                        style={{ width: '100%', height: '100%', objectFit: 'cover', borderRadius: '8px', backgroundColor: '#f0f0f0' }}
                        onError={(e) => { e.currentTarget.src = '/default-thumbnail.png'; }}
                    />
                </div>
                <div style={{ flex: 1, display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
                    <div>
                        <h3 style={{ margin: 0, fontSize: '1rem', color: '#212529', fontWeight: 'normal' }}>
                            {post.title}
                        </h3>
                        <p style={{ margin: '4px 0 0', fontSize: '0.8rem', color: '#868e96' }}>
                            {post.location}
                        </p>
                    </div>
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