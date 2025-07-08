
import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { useRoutes } from "react-router-dom";
import routes from "virtual:generated-pages";
import AuthWrapper from "./components/AuthWrapper";

const queryClient = new QueryClient();

const App = () => {
  const element = useRoutes(routes);

  return (
    <QueryClientProvider client={queryClient}>
      <TooltipProvider>
        <Toaster />
        <Sonner />
        <AuthWrapper>{element}</AuthWrapper>
      </TooltipProvider>
    </QueryClientProvider>
  );
};

export default App;
