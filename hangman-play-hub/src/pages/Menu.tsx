import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { User, Play, Trophy, LogOut } from "lucide-react";
import doodleBg from "@/assets/doodle-bg.png";
import { useToast } from "@/hooks/use-toast";
import { useEffect, useState } from "react";
import { Leaderboard, LeaderBoardUse } from "@/type/Leaderboard";
import { convertLeaderboard } from "@/type/Leaderboard";

const Menu = () => {
  const navigate = useNavigate();
  const { toast } = useToast();

  const [user, setUser] = useState<string | null>(null);
  const [leaderboardData, setLeaderBoard] = useState<LeaderBoardUse[]>([]);

  const getLeaderboard = async () => {
    try {
      const res = await fetch(`http://localhost:8080/api/leaderboard`, {
        method: "GET",
        credentials: "include",
      });
      if (res.status === 200) {
        const ldbRaw: Leaderboard[] = await res.json();
        const ldb = convertLeaderboard(ldbRaw);
        if (ldb != null) {
          setLeaderBoard(ldb);
        }
      }
    } catch (err) {
      console.error("Login error: ", err);
      //or do some fancy show error
    }
  };
  const getUsername = async () => {
    try {
      const res = await fetch(`http://localhost:8080/api/user/username`, {
        method: "GET",
        credentials: "include",
      });
      if (res.status === 200) {
        const usernamejson: string = await res.text();
        setUser(usernamejson);
      }
    } catch (err) {
      console.error("Login error: ", err);
      //or do some fancy show error
    }
  };

  useEffect(() => {
    getLeaderboard();
    getUsername();
  }, []);

  const handleLogout = async () => {
    try {
      const res = await fetch(`http://localhost:8080/auth/logout`, {
        method: "POST",
        credentials: "include",
      });
      if (res.status === 200) {
        toast({
          title: "LogOut Successful",
          description: "We are redirecting you shortly!",
          variant: "default",
        });
        setTimeout(() => {
          navigate("/", { replace: true });
        }, 1000);
      }
    } catch (err) {
      console.error("Login error: ", err);
      //or do some fancy show error
      toast({
        title: "Error",
        description: "Logout failed",
        variant: "destructive",
      });
    }
  };

  return (
    <div
      className="min-h-screen relative overflow-hidden"
      style={{
        backgroundImage: `url(${doodleBg})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
      }}
    >
      <div className="absolute inset-0 bg-background/85 backdrop-blur-sm" />

      <div className="relative z-10">
        {/* Top Bar */}
        <div className="border-b border-border/50 bg-card/50 backdrop-blur-md">
          <div className="max-w-7xl mx-auto px-4 py-4">
            <h1 className="text-2xl font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent text-center">
              HANGMAN – Survive else get Hanged
            </h1>
          </div>
        </div>

        <div className="max-w-7xl mx-auto p-4 md:p-6">
          <div className="grid md:grid-cols-[300px_1fr] gap-6">
            {/* Left Sidebar */}
            <div className="game-card p-6 space-y-6 h-fit animate-fade-in">
              {/* Player Info */}
              <div className="text-center space-y-3">
                <Avatar className="w-24 h-24 mx-auto border-4 border-primary">
                  <AvatarFallback className="bg-primary/10 text-2xl">
                    <User className="w-12 h-12 text-primary" />
                  </AvatarFallback>
                </Avatar>
                <div>
                  <h2 className="text-xl font-semibold">{user}</h2>
                  <p className="text-sm text-muted-foreground">
                    Ready to play!
                  </p>
                </div>
              </div>

              {/* Menu Options */}
              <nav className="space-y-2">
                <Button
                  className="w-full justify-start gap-3 h-12 font-semibold gradient-playful hover:opacity-90 transition-opacity"
                  onClick={() => navigate("/game")}
                >
                  <Play className="w-5 h-5" />
                  Start Game
                </Button>

                <Button
                  variant="outline"
                  className="w-full justify-start gap-3 h-12 font-semibold bg-primary/5 border-primary/20 hover:bg-primary/10 hover:border-primary"
                >
                  <Trophy className="w-5 h-5" />
                  Leaderboard
                </Button>

                <Button
                  variant="outline"
                  className="w-full justify-start gap-3 h-12 font-semibold hover:bg-destructive/10 hover:border-destructive hover:text-destructive"
                  onClick={handleLogout}
                >
                  <LogOut className="w-5 h-5" />
                  Logout
                </Button>
              </nav>
            </div>

            {/* Leaderboard Section */}
            <div
              className="game-card p-8 animate-fade-in"
              style={{ animationDelay: "0.1s" }}
            >
              <div className="flex items-center justify-between mb-6">
                <h2 className="text-3xl font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
                  Leaderboard
                </h2>
                <Trophy className="w-8 h-8 text-primary animate-bounce-subtle" />
              </div>

              <div className="space-y-3">
                {leaderboardData.map((player, index) => (
                  <div
                    key={player.rank}
                    className={`flex items-center justify-between p-4 rounded-xl transition-all hover:scale-[1.02] ${
                      index === 0
                        ? "bg-gradient-to-r from-primary/20 to-secondary/20 border-2 border-primary/30"
                        : "bg-muted/50 hover:bg-muted/80"
                    }`}
                    style={{ animationDelay: `${index * 0.1}s` }}
                  >
                    <div className="flex items-center gap-4">
                      <div
                        className={`w-10 h-10 rounded-full flex items-center justify-center font-bold text-lg ${
                          index === 0
                            ? "bg-primary text-primary-foreground"
                            : index === 1
                            ? "bg-secondary text-secondary-foreground"
                            : index === 2
                            ? "bg-accent text-accent-foreground"
                            : "bg-muted-foreground/20 text-foreground"
                        }`}
                      >
                        #{player.rank}
                      </div>
                      <div>
                        <p className="font-semibold text-lg">{player.name}</p>
                        {index === 0 && (
                          <p className="text-xs text-primary font-medium">
                            🏆 Champion
                          </p>
                        )}
                      </div>
                    </div>
                    <div className="text-2xl font-bold text-primary">
                      {player.score}
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Menu;
