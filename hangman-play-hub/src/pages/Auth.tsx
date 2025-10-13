import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { User, ChevronLeft, ChevronRight } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { supabase } from "@/integrations/supabase/client";
import hangmanIcon from "@/assets/hangman-icon.png";
import doodleBg from "@/assets/doodle-bg.png";

const howToPlaySteps = [
  {
    title: "Step 1: Guess the Word",
    description:
      "A random word is selected. Your job is to guess it letter by letter!",
  },
  {
    title: "Step 2: Choose Letters",
    description:
      "Click on letters to make your guess. Correct letters appear in the word.",
  },
  {
    title: "Step 3: Avoid the Hangman",
    description:
      "Each wrong guess adds a part to the hangman. Get 6 wrong and you lose!",
  },
];

const Auth = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [currentStep, setCurrentStep] = useState(0);
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    // Check if user is already logged in
    supabase.auth.getSession().then(({ data: { session } }) => {
      if (session) {
        navigate("/menu");
      }
    });

    // Listen for auth changes
    const {
      data: { subscription },
    } = supabase.auth.onAuthStateChange((event, session) => {
      if (session) {
        navigate("/menu");
      }
    });

    return () => subscription.unsubscribe();
  }, [navigate]);

  const handleGoogleSignIn = async () => {
    setIsLoading(true);
    const redirectUrl = `${window.location.origin}/menu`;

    const { error } = await supabase.auth.signInWithOAuth({
      provider: "google",
      options: {
        redirectTo: redirectUrl,
      },
    });

    if (error) {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    }
    setIsLoading(false);
  };

  // const handleSubmit = async (e: React.FormEvent) => {
  //   e.preventDefault();
  //   setIsLoading(true);

  //   const { data, error } = await supabase.auth.signInWithPassword({
  //     email: formData.email,
  //     password: formData.password,
  //   });

  //   setIsLoading(false);

  //   if (error) {
  //     toast({
  //       title: "Sign In Failed",
  //       description: error.message,
  //       variant: "destructive",
  //     });
  //     return;
  //   }

  //   if (data.session) {
  //     toast({
  //       title: "Welcome Back!",
  //       description: "Successfully signed in.",
  //     });
  //     navigate("/menu");
  //   }
  // };

  const handleRegister = () => {
    navigate("/register", { replace: true });
  };

  const nextStep = () => {
    setCurrentStep((prev) => (prev + 1) % howToPlaySteps.length);
  };

  const prevStep = () => {
    setCurrentStep(
      (prev) => (prev - 1 + howToPlaySteps.length) % howToPlaySteps.length
    );
  };

  async function signIn(e) {
    e.preventDefault();

    setIsLoading(true);
    try {
      const res = await fetch(`http://localhost:8080/auth/login`, {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          email: formData.email,
          password: formData.password,
        }),
      });
      if (res.status === 200) {
        toast({
          title: "Successful",
          description: "Login Success",
          variant: "default",
        });
        setIsLoading(false);
        setTimeout(() => {
          navigate("/menu", { replace: true });
        }, 1000);
      }
    } catch (err) {
      console.error("Login error: ", err);
      //or do some fancy show error
      toast({
        title: "Error",
        description: "Login failed",
        variant: "destructive",
      });
    }
  }

  return (
    <div
      className="min-h-screen p-4 relative overflow-hidden"
      style={{
        backgroundImage: `url(${doodleBg})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
      }}
    >
      <div className="absolute inset-0 bg-background/85 backdrop-blur-sm" />

      <div className="relative z-10 max-w-6xl mx-auto pt-8">
        {/* Title */}
        <div className="text-center mb-8">
          <div className="flex items-center justify-center gap-3 mb-2">
            <img
              src={hangmanIcon}
              alt="Hangman"
              className="w-12 h-12 animate-bounce-subtle"
            />
            <h1 className="text-5xl font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
              HANGMAN
            </h1>
          </div>
        </div>

        <div className="grid md:grid-cols-2 gap-8 animate-fade-in">
          {/* Authentication Panel */}
          <div className="game-card p-8 space-y-6">
            <div className="text-center">
              <Avatar className="w-24 h-24 mx-auto mb-4 border-4 border-primary">
                <AvatarFallback className="bg-primary/10">
                  <User className="w-12 h-12 text-primary" />
                </AvatarFallback>
              </Avatar>
              <h2 className="text-2xl font-semibold mb-2">Welcome Back!</h2>
              <p className="text-sm text-muted-foreground">
                Sign in to continue playing
              </p>
            </div>

            <form onSubmit={signIn} className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="your.email@example.com"
                  value={formData.email}
                  onChange={(e) =>
                    setFormData({ ...formData, email: e.target.value })
                  }
                  required
                  disabled={isLoading}
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="password">Password</Label>
                <Input
                  id="password"
                  type="password"
                  placeholder="Enter your password"
                  value={formData.password}
                  onChange={(e) =>
                    setFormData({ ...formData, password: e.target.value })
                  }
                  required
                  disabled={isLoading}
                />
              </div>

              <div className="flex gap-3">
                <Button
                  type="button"
                  variant="outline"
                  onClick={handleRegister}
                  className="flex-1 h-11 font-semibold hover:bg-primary/10 hover:text-primary hover:border-primary"
                  disabled={isLoading}
                >
                  Register
                </Button>
                <Button
                  type="submit"
                  className="flex-1 h-11 font-semibold gradient-playful hover:opacity-90 transition-opacity"
                  disabled={isLoading}
                >
                  {isLoading ? "Signing In..." : "Start"}
                </Button>
              </div>
            </form>

            {/* <div className="relative">
              <div className="absolute inset-0 flex items-center">
                <span className="w-full border-t" />
              </div>
              <div className="relative flex justify-center text-xs uppercase">
                <span className="bg-card px-2 text-muted-foreground">
                  Or continue with
                </span>
              </div>
            </div> */}

            {/* <Button
              type="button"
              variant="outline"
              className="w-full h-12 font-semibold"
              onClick={handleGoogleSignIn}
              disabled={isLoading}
            >
              <svg className="w-5 h-5 mr-2" viewBox="0 0 24 24">
                <path
                  fill="currentColor"
                  d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"
                />
                <path
                  fill="currentColor"
                  d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"
                />
                <path
                  fill="currentColor"
                  d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"
                />
                <path
                  fill="currentColor"
                  d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"
                />
              </svg>
              Sign in with Google
            </Button> */}
          </div>

          {/* How to Play Panel */}
          <div className="game-card p-8 space-y-6">
            <h2 className="text-2xl font-semibold text-center mb-6">
              How To Play
            </h2>

            <div className="min-h-[200px] flex flex-col justify-center space-y-4">
              <div className="text-center space-y-3">
                <h3 className="text-xl font-semibold text-primary">
                  {howToPlaySteps[currentStep].title}
                </h3>
                <p className="text-muted-foreground leading-relaxed">
                  {howToPlaySteps[currentStep].description}
                </p>
              </div>
            </div>

            <div className="flex items-center justify-between pt-4">
              <Button
                variant="ghost"
                size="icon"
                onClick={prevStep}
                className="hover:bg-primary/10 hover:text-primary"
              >
                <ChevronLeft className="w-5 h-5" />
              </Button>

              <div className="flex gap-2">
                {howToPlaySteps.map((_, index) => (
                  <button
                    key={index}
                    onClick={() => setCurrentStep(index)}
                    className={`w-2 h-2 rounded-full transition-all ${
                      index === currentStep
                        ? "bg-primary w-8"
                        : "bg-muted-foreground/30 hover:bg-muted-foreground/50"
                    }`}
                  />
                ))}
              </div>

              <Button
                variant="ghost"
                size="icon"
                onClick={nextStep}
                className="hover:bg-primary/10 hover:text-primary"
              >
                <ChevronRight className="w-5 h-5" />
              </Button>
            </div>
          </div>
        </div>

        {/* Footer */}
        <div className="text-center mt-8">
          <p className="text-sm text-muted-foreground">
            Created by{" "}
            <span className="font-semibold text-foreground">
              Teng Rithrathanak, Lim Ankim, and Leng Monirith
            </span>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Auth;
