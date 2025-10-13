export type Leaderboard = {
    id: number,
    totalScore:number,
    lastUpdated:string,
    user: User
}
export type User = {
    id:number,
    username:string,
    email:string
}
export type LeaderBoardUse = {
    rank:number,
    score:number,
    name:string
}

export function convertLeaderboard(data: Leaderboard[]): LeaderBoardUse[] {
  // Sort descending by totalScore
  const sorted = [...data].sort((a, b) => b.totalScore - a.totalScore);

  // Map to new format with rank
  return sorted.map((item, index) => ({
    rank: index + 1,
    score: item.totalScore,
    name: item.user.username,
  }));
}