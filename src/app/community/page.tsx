import PostList from "@/components/features/community/PostList";

export default function CommunityPage() {
  const storeId = 3;

  return (
    <div style={{
      maxWidth: '1000px',
      margin: '0 auto'
    }}>
      <PostList storeId={storeId} />
    </div>
  );
}