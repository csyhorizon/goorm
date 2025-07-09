import { Outlet } from 'react-router-dom';
import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import AuthWrapper from "@/components/AuthWrapper";

export default function Layout() {
  return (
    <TooltipProvider>
      <AuthWrapper>
        <Outlet />
      </AuthWrapper>
      <Toaster />
      <Sonner />
    </TooltipProvider>
  );
} 