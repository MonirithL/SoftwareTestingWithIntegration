import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Home, RotateCcw } from "lucide-react";
import doodleBg from "@/assets/doodle-bg.png";
import { WordFull } from "@/type/WordFull";

const MAX_WRONG_GUESSES = 6;

const Game = () => {
  const navigate = useNavigate();
  const [dbWord, setdbWord] = useState<WordFull | null>(null);
  const [word, setWord] = useState("");
  const [hint, setHint] = useState("");
  const [guessedLetters, setGuessedLetters] = useState<Set<string>>(new Set());
  const [wrongGuesses, setWrongGuesses] = useState(0);
  const [gameStatus, setGameStatus] = useState<"playing" | "won" | "lost">(
    "playing"
  );

  useEffect(() => {
    startNewGame();
  }, []);

  async function getWord() {
    try {
      const res = await fetch(`http://localhost:8080/api/word`, {
        method: "GET",
        credentials: "include",
      });
      if (res.status === 200) {
        const data: WordFull = await res.json();
        return data;
      } else {
        return null;
      }
    } catch (err) {
      console.error("get word: ", err);
      return null;
    }
  }
  async function addGame() {
    const score = 6 - wrongGuesses;
    try {
      const res = await fetch(`http://localhost:8080/api/game/save`, {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          word: dbWord.word,
          score: score,
          dateString: new Date().toISOString(),
        }),
      });
      if (res.status === 200) {
        return 1;
      } else {
        return null;
      }
    } catch (err) {
      console.error("get word: ", err);
      return 0;
    }
  }
  function createHint(): string {
    return "";
  }

  const startNewGame = async () => {
    //set word
    const nword: WordFull = await getWord();
    if (nword != null) {
      setdbWord(nword);
    }

    // const randomItem = WORDS[Math.floor(Math.random() * WORDS.length)];
    // setWord(randomItem.word);
    // setHint(randomItem.hint);
  };

  useEffect(() => {
    if (dbWord != null) {
      const nhint: string = `${dbWord.partOfSpeech} - (${dbWord.category}) ${dbWord.definition}`;
      setHint(nhint);
      setWord(dbWord.word.toUpperCase());
      setGuessedLetters(new Set());
      setWrongGuesses(0);
      setGameStatus("playing");
    }
  }, [dbWord]);

  const handleLetterClick = async (letter: string) => {
    if (gameStatus !== "playing" || guessedLetters.has(letter)) return;

    const newGuessed = new Set(guessedLetters);
    newGuessed.add(letter);
    setGuessedLetters(newGuessed);

    if (!word.includes(letter)) {
      console.log(`${word} not have ${letter}`);
      const newWrongGuesses = wrongGuesses + 1;
      setWrongGuesses(newWrongGuesses);
      if (newWrongGuesses >= MAX_WRONG_GUESSES) {
        setGameStatus("lost");
      }
    } else {
      // Check if word is complete
      const allLettersGuessed = word.split("").every((l) => newGuessed.has(l));

      if (allLettersGuessed) {
        const statusSaveGame = await addGame();
        if (statusSaveGame === 1) {
          setGameStatus("won");
        }
        //call win function
      }
    }
  };

  const renderWord = () => {
    return word.split("").map((letter, index) => (
      <div
        key={index}
        className="w-12 h-16 border-b-4 border-primary flex items-center justify-center text-3xl font-bold text-primary"
      >
        {guessedLetters.has(letter) ? letter : ""}
      </div>
    ));
  };

  const renderHangman = () => {
    const parts = [
      // Head
      <circle
        key="head"
        cx="140"
        cy="60"
        r="20"
        stroke="currentColor"
        strokeWidth="3"
        fill="none"
      />,
      // Body
      <line
        key="body"
        x1="140"
        y1="80"
        x2="140"
        y2="130"
        stroke="currentColor"
        strokeWidth="3"
      />,
      // Left arm
      <line
        key="leftarm"
        x1="140"
        y1="95"
        x2="115"
        y2="110"
        stroke="currentColor"
        strokeWidth="3"
      />,
      // Right arm
      <line
        key="rightarm"
        x1="140"
        y1="95"
        x2="165"
        y2="110"
        stroke="currentColor"
        strokeWidth="3"
      />,
      // Left leg
      <line
        key="leftleg"
        x1="140"
        y1="130"
        x2="120"
        y2="160"
        stroke="currentColor"
        strokeWidth="3"
      />,
      // Right leg
      <line
        key="rightleg"
        x1="140"
        y1="130"
        x2="160"
        y2="160"
        stroke="currentColor"
        strokeWidth="3"
      />,
    ];

    return (
      <svg className="w-64 h-64 text-destructive" viewBox="0 0 200 200">
        {/* Gallows */}
        <line
          x1="20"
          y1="180"
          x2="100"
          y2="180"
          stroke="currentColor"
          strokeWidth="3"
        />
        <line
          x1="40"
          y1="180"
          x2="40"
          y2="20"
          stroke="currentColor"
          strokeWidth="3"
        />
        <line
          x1="40"
          y1="20"
          x2="140"
          y2="20"
          stroke="currentColor"
          strokeWidth="3"
        />
        <line
          x1="140"
          y1="20"
          x2="140"
          y2="40"
          stroke="currentColor"
          strokeWidth="3"
        />

        {/* Body parts based on wrong guesses */}
        {parts.slice(0, wrongGuesses)}
      </svg>
    );
  };

  const alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

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
          <div className="max-w-7xl mx-auto px-4 py-4 flex items-center justify-between">
            <Button
              variant="outline"
              onClick={() => navigate("/menu")}
              className="gap-2"
            >
              <Home className="w-4 h-4" />
              Back to Menu
            </Button>
            <h1 className="text-2xl font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
              HANGMAN GAME
            </h1>
            <div className="w-32" /> {/* Spacer for centering */}
          </div>
        </div>

        <div className="max-w-4xl mx-auto p-6 space-y-8 animate-fade-in">
          {/* Game Status */}
          {gameStatus !== "playing" && (
            <div
              className={`game-card p-6 text-center ${
                gameStatus === "won"
                  ? "border-2 border-primary"
                  : "border-2 border-destructive"
              }`}
            >
              <h2
                className={`text-3xl font-bold mb-2 ${
                  gameStatus === "won" ? "text-primary" : "text-destructive"
                }`}
              >
                {gameStatus === "won" ? "🎉 You Won!" : "😢 Game Over!"}
              </h2>
              <p className="text-muted-foreground mb-4">
                {gameStatus === "lost" && `The word was: ${word}`}
              </p>
              <Button
                onClick={startNewGame}
                className="gradient-playful hover:opacity-90 transition-opacity gap-2"
              >
                <RotateCcw className="w-4 h-4" />
                Play Again
              </Button>
            </div>
          )}

          {/* Game Display - Hangman & Word on Same Row */}
          <div className="game-card p-8">
            <div className="flex flex-col lg:flex-row items-center justify-center gap-8">
              {/* Hangman Drawing */}
              <div className="flex justify-center">{renderHangman()}</div>

              {/* Word Display */}
              <div className="flex-1 max-w-2xl">
                <div className="mb-4 p-4 bg-muted/30 rounded-lg border border-border/50">
                  <p className="text-sm text-muted-foreground mb-1">💡 Hint:</p>
                  <p className="text-lg font-medium">{hint}</p>
                </div>
                <div className="flex justify-center gap-2 flex-wrap mb-4">
                  {renderWord()}
                </div>
                <div className="text-center text-muted-foreground">
                  Wrong guesses: {wrongGuesses} / {MAX_WRONG_GUESSES}
                </div>
              </div>
            </div>
          </div>

          {/* Letter Buttons */}
          <div className="game-card p-8">
            <h3 className="text-xl font-semibold mb-4 text-center">
              Select a Letter
            </h3>
            <div className="grid grid-cols-7 gap-2 max-w-2xl mx-auto">
              {alphabet.map((letter) => {
                const isGuessed = guessedLetters.has(letter);
                const isCorrect = isGuessed && word.includes(letter);
                const isWrong = isGuessed && !word.includes(letter);

                return (
                  <Button
                    key={letter}
                    onClick={() => handleLetterClick(letter)}
                    disabled={isGuessed || gameStatus !== "playing"}
                    className={`h-12 text-lg font-semibold ${
                      isCorrect
                        ? "bg-primary text-primary-foreground"
                        : isWrong
                        ? "bg-destructive/20 text-destructive"
                        : "hover:scale-105"
                    }`}
                    variant={isGuessed ? "secondary" : "outline"}
                  >
                    {letter}
                  </Button>
                );
              })}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Game;
