import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";
import Register from "./pages/Register";
import Auth from "./pages/Auth";
import Menu from "./pages/Menu";
import Game from "./pages/Game";
import NotFound from "./pages/NotFound";
import NoAuthLayout from "./layout/NoAuthLayout";
import AuthLayout from "./layout/AuthLayout";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <Routes>
          <Route element={<NoAuthLayout />}>
            <Route path="/register" element={<Register />} />
            <Route path="/" element={<Auth />} />
          </Route>

          <Route element={<AuthLayout />}>
            <Route path="/menu" element={<Menu />} />
            <Route path="/game" element={<Game />} />
          </Route>
          {/* ADD ALL CUSTOM ROUTES ABOVE THE CATCH-ALL "*" ROUTE */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
