import { useNavigate, Outlet } from "react-router";
import { useEffect, useState } from "react";
import Loading from "@/pages/Loading";

export default function AuthLayout() {
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  async function checkAuth() {
    setLoading(true);
    try {
      const res = await fetch(`http://localhost:8080/api/authCheck`, {
        method: "GET",
        credentials: "include",
      });
      if (res.status !== 200) {
        const data = await res.json();
        console.log("AUTH LAYOUT data: ", data);
        navigate("/", { replace: true });
      }
    } catch (err) {
      console.error("AuthLayout error: ", err);
      navigate("/", { replace: true });
    } finally {
      setLoading(false);
    }
  }
  useEffect(() => {
    checkAuth();
  }, [navigate]);

  if (loading) return <Loading></Loading>;
  return <Outlet />;
}
