import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Settings, Camera, Plus, Grid3x3, Bookmark, User } from "lucide-react";

const MyProfilePage = () => {
  const [activeTab, setActiveTab] = useState("posts");

  const user = {
    username: "user01",
    realName: "홍길동",
    profileImageUrl: "https://via.placeholder.com/150x150.png?text=Profile",
    stats: {
      posts: 0,
      followers: 100,
      following: 100
    }
  };

  return (
      <div className="w-full max-w-[935px] mx-auto bg-background min-h-screen px-4 pt-10">
        {/* Profile Section */}
        <div className="flex items-center gap-16 mb-12">
          {/* Profile Image */}
          <Avatar className="w-40 h-40">
            <AvatarImage src={user.profileImageUrl} alt="Profile" />
            <AvatarFallback>Image</AvatarFallback>
          </Avatar>

          {/* Right Side */}
          <div className="flex-1 space-y-4">
            {/* Username & Buttons */}
            <div className="flex items-center gap-2 flex-wrap">
              <h2 className="text-2xl font-light text-profile-text">{user.username}</h2>
              <Button variant="secondary" size="sm" className="text-sm px-4 py-1.5">
                프로필 편집
              </Button>
              <Button variant="secondary" size="sm" className="text-sm px-4 py-1.5">
                보관된 스토리 보기
              </Button>
              <Button variant="ghost" size="icon">
                <Settings className="w-5 h-5 text-profile-text" />
              </Button>
            </div>

            {/* Stats */}
            <div className="flex gap-10 text-sm">
              <div className="flex gap-1">
                <span className="text-profile-secondary">게시물</span>
                <span className="font-semibold text-profile-text">{user.stats.posts}</span>
              </div>
              <div className="flex gap-1">
                <span className="text-profile-secondary">팔로워</span>
                <span className="font-semibold text-profile-text">{user.stats.followers}</span>
              </div>
              <div className="flex gap-1">
                <span className="text-profile-secondary">팔로우</span>
                <span className="font-semibold text-profile-text">{user.stats.following}</span>
              </div>
            </div>

            {/* Bio */}
            <div className="font-semibold text-profile-text text-sm">{user.realName}</div>
          </div>
        </div>

        {/* Story Highlights */}
        <div className="mb-8">
          <div className="flex gap-4 overflow-x-auto pb-2">
            <div className="flex flex-col items-center gap-2 min-w-[70px]">
              <div className="w-20 h-20 rounded-full border-2 border-muted flex items-center justify-center bg-muted/50">
                <Plus className="h-9 w-9 text-muted-foreground" />
              </div>
              <span className="text-xs text-profile-secondary">신규</span>
            </div>
          </div>
        </div>

        {/* Tabs */}
        <Tabs value={activeTab} onValueChange={setActiveTab} className="w-full">
          <TabsList className="grid w-full grid-cols-3 bg-transparent h-auto p-0 border-t border-border">
            {["posts", "saved", "tagged"].map((tab) => (
                <TabsTrigger
                    key={tab}
                    value={tab}
                    className="flex items-center gap-2 py-3 px-4 data-[state=active]:border-t-2 data-[state=active]:border-profile-text data-[state=active]:bg-transparent rounded-none border-t-2 border-transparent"
                >
                  {tab === "posts" && <Grid3x3 className="h-4 w-4" />}
                  {tab === "saved" && <Bookmark className="h-4 w-4" />}
                  {tab === "tagged" && <User className="h-4 w-4" />}
                  <span className="hidden sm:inline">{tab.toUpperCase()}</span>
                </TabsTrigger>
            ))}
          </TabsList>

          <TabsContent value="posts" className="mt-8">
            <EmptyState
                icon={Camera}
                title="사진 공유"
                subtitle="사진을 공유하면 회원님의 프로필에 표시됩니다."
                linkText="첫 사진 공유하기"
            />
          </TabsContent>

          <TabsContent value="saved" className="mt-8">
            <EmptyState
                icon={Bookmark}
                title="저장"
                subtitle="다시 보고 싶은 사진과 동영상을 저장하세요. 콘텐츠를 저장해도 다른 사람에게 알림이 전송되지 않으며, 저장된 콘텐츠는 회원님만 볼 수 있습니다."
                linkText="저장된 콘텐츠 보기"
            />
          </TabsContent>

          <TabsContent value="tagged" className="mt-8">
            <EmptyState
                icon={User}
                title="내가 나온 사진"
                subtitle="사람들이 회원님을 사진에 태그하면 태그된 사진이 여기에 표시됩니다."
                linkText="친구 찾아보기"
            />
          </TabsContent>
        </Tabs>
      </div>
  );
};

interface EmptyStateProps {
  icon: React.ComponentType<{ className?: string }>;
  title: string;
  subtitle: string;
  linkText: string;
}

const EmptyState = ({ icon: Icon, title, subtitle, linkText }: EmptyStateProps) => {
  return (
      <div className="flex flex-col items-center justify-center py-16 text-center">
        <div className="w-20 h-20 rounded-full border-2 border-profile-text flex items-center justify-center mb-6">
          <Icon className="h-10 w-10 text-profile-text" />
        </div>
        <h3 className="text-2xl font-light text-profile-text mb-2">{title}</h3>
        <p className="text-profile-secondary mb-4 max-w-md">{subtitle}</p>
        <Button variant="link" className="text-instagram-blue font-semibold p-0 h-auto">
          {linkText}
        </Button>
      </div>
  );
};

export default MyProfilePage;
