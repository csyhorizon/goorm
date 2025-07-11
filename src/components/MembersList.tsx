import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Users, Crown, Shield } from "lucide-react";

interface User {
    id: string;
    name: string;
    email: string;
    avatar?: string;
    role: 'admin' | 'moderator' | 'user';
    joinedAt: string;
}

interface MembersListProps {
    users: User[];
}

export function MembersList({ users }: MembersListProps) {
    const getRoleIcon = (role: string) => {
        switch (role) {
            case 'admin':
                return <Crown className="h-3 w-3" />;
            case 'moderator':
                return <Shield className="h-3 w-3" />;
            default:
                return <Users className="h-3 w-3" />;
        }
    };

    const getRoleBadgeVariant = (role: string) => {
        switch (role) {
            case 'admin':
                return 'default';
            case 'moderator':
                return 'secondary';
            default:
                return 'outline';
        }
    };

    return (
        <Card className="shadow-lg border-0">
            <CardHeader>
                <CardTitle className="text-lg font-semibold text-foreground flex items-center gap-2">
                    <Users className="h-5 w-5 text-primary" />
                    All Members ({users.length})
                </CardTitle>
            </CardHeader>
            <CardContent>
                <div className="space-y-4 max-h-96 overflow-y-auto">
                    {users.map((user) => (
                        <div key={user.id} className="flex items-center justify-between p-3 rounded-lg bg-muted/30 hover:bg-muted/50 transition-colors">
                            <div className="flex items-center space-x-3">
                                <Avatar className="h-10 w-10">
                                    <AvatarImage src={user.avatar} alt={user.name} />
                                    <AvatarFallback className="bg-primary/10 text-primary font-medium">
                                        {user.name.split(' ').map(n => n[0]).join('').toUpperCase()}
                                    </AvatarFallback>
                                </Avatar>
                                <div>
                                    <p className="text-sm font-medium text-foreground">{user.name}</p>
                                    <p className="text-xs text-muted-foreground">{user.email}</p>
                                </div>
                            </div>
                            <div className="flex items-center space-x-2">
                                <Badge variant={getRoleBadgeVariant(user.role)} className="text-xs">
                                    {getRoleIcon(user.role)}
                                    <span className="ml-1 capitalize">{user.role}</span>
                                </Badge>
                            </div>
                        </div>
                    ))}
                </div>
            </CardContent>
        </Card>
    );
}