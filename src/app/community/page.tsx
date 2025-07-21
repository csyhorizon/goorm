import PostList from "@/components/features/community/PostList";
import FloatingWriteButton from "@/components/features/community/FloatingWriteButton";

export default function CommunityPage() {
  return (
    <div style={{
      maxWidth: '1000px',
      margin: '0 auto'
    }}>
      <PostList />
      <FloatingWriteButton />
    </div>
  );
}