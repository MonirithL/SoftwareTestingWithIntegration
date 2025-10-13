import { useNavigate, Outlet } from "react-router";
import { useEffect, useState } from "react";
import Loading from "@/pages/Loading";

export default function NoAuthLayout() {
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  async function checkAuth() {
    setLoading(true);
    try {
      const res = await fetch(`http://localhost:8080/api/authCheck`, {
        method: "GET",
        credentials: "include",
      });
      if (res.status === 200) {
        console.log("THERE IS AUTH, redirecting");
        navigate("/menu", { replace: true });
      }
    } catch (err) {
      console.error("NOAuthLayout error: ", err);
      //   navigate("/menu", { replace: true });
    } finally {
      setLoading(false);
    }
  }
  useEffect(() => {
    checkAuth();
  }, [navigate]);

  if (loading) return <Loading />;
  return <Outlet />;
}
