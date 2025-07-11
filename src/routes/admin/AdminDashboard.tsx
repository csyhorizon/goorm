import { StatsCard } from "@/components/StatsCard";
import { UserGrowthChart } from "@/components/UserGrowthChart";
import { MembersList } from "@/components/MembersList";
import { OnlineUsers } from "@/components/OnlineUsers";

// Mock data - replace with actual API calls
const mockStats = {
    totalUsers: 12457,
    totalPosts: 8932,
    totalComments: 24563,
    totalLikes: 67891
};

const mockGrowthData = [
    { name: 'Jan', users: 400 },
    { name: 'Feb', users: 1200 },
    { name: 'Mar', users: 2800 },
    { name: 'Apr', users: 4200 },
    { name: 'May', users: 6800 },
    { name: 'Jun', users: 9200 },
    { name: 'Jul', users: 12457 }
];

const mockMembers = [
    { id: '1', name: 'John Doe', email: 'john@example.com', role: 'admin' as const, joinedAt: '2023-01-15', avatar: undefined },
    { id: '2', name: 'Jane Smith', email: 'jane@example.com', role: 'moderator' as const, joinedAt: '2023-02-20', avatar: undefined },
    { id: '3', name: 'Mike Johnson', email: 'mike@example.com', role: 'user' as const, joinedAt: '2023-03-10', avatar: undefined },
    { id: '4', name: 'Sarah Wilson', email: 'sarah@example.com', role: 'user' as const, joinedAt: '2023-04-05', avatar: undefined },
    { id: '5', name: 'Alex Brown', email: 'alex@example.com', role: 'moderator' as const, joinedAt: '2023-05-12', avatar: undefined }
];

const mockOnlineUsers = [
    { id: '1', name: 'John Doe', lastActive: 'Just now', status: 'online' as const, avatar: undefined },
    { id: '2', name: 'Jane Smith', lastActive: '2 min ago', status: 'online' as const, avatar: undefined },
    { id: '3', name: 'Mike Johnson', lastActive: '5 min ago', status: 'away' as const, avatar: undefined },
    { id: '4', name: 'Sarah Wilson', lastActive: '1 min ago', status: 'busy' as const, avatar: undefined }
];

export function AdminDashboard() {
    return (
        <div className="min-h-screen bg-background p-6">
            <div className="max-w-7xl mx-auto space-y-8">
                {/* Header */}
                <div className="mb-8">
                    <h1 className="text-3xl font-bold text-foreground mb-2">Admin Dashboard</h1>
                    <p className="text-muted-foreground">Monitor your platform's performance and user activity</p>
                </div>

                {/* Statistics Cards */}
                <StatsCard stats={mockStats} />

                {/* User Growth Chart */}
                <UserGrowthChart data={mockGrowthData} />

                {/* Bottom Section - Members List and Online Users */}
                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <MembersList users={mockMembers} />
                    <OnlineUsers users={mockOnlineUsers} />
                </div>
            </div>
        </div>
    );
}