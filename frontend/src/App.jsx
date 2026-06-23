import { BrowserRouter, Routes, Route, Navigate, NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "./AuthContext";
import Login      from "./pages/Login";
import Dashboard  from "./pages/Dashboard";
import Rooms      from "./pages/Rooms";
import Tenants    from "./pages/Tenants";
import Payments   from "./pages/Payments";
import Complaints from "./pages/Complaints";
import MyPg       from "./pages/MyPg";
import FindPg     from "./pages/FindPg";

const ADMIN_NAV = [
  { to: "/",           label: "Dashboard",  icon: "🏠" },
  { to: "/rooms",      label: "Rooms",      icon: "🛏️" },
  { to: "/tenants",    label: "Tenants",    icon: "👤" },
  { to: "/payments",   label: "Payments",   icon: "💰" },
  { to: "/complaints", label: "Complaints", icon: "📋" },
  { to: "/my-pg",      label: "My PG",      icon: "🏡" },
];

const TENANT_NAV = [
  { to: "/",           label: "Dashboard",    icon: "🏠" },
  { to: "/find-pg",    label: "Find PG",      icon: "🔍" },
  { to: "/payments",   label: "My Payments",  icon: "💰" },
  { to: "/complaints", label: "My Complaints",icon: "📋" },
];

function Layout({ children }) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const handleLogout = () => { logout(); navigate("/login"); };
  const NAV = user?.role === "ADMIN" ? ADMIN_NAV : TENANT_NAV;

  return (
    <div className="flex h-screen bg-gray-50">
      {/* Sidebar */}
      <aside className="hidden md:flex flex-col w-56 bg-white border-r border-gray-200 py-6 px-4">
        <div className="mb-8">
          <h1 className="text-xl font-bold text-orange-600">🏡 Nestify</h1>
          <p className="text-xs text-gray-400 mt-1">PG Management</p>
        </div>
        <nav className="flex-1 space-y-1">
          {NAV.map(n => (
            <NavLink key={n.to} to={n.to} end={n.to === "/"}
              className={({ isActive }) =>
                `flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium transition-all
                ${isActive ? "bg-orange-50 text-orange-600" : "text-gray-600 hover:bg-gray-50"}`
              }>
              <span>{n.icon}</span>{n.label}
            </NavLink>
          ))}
        </nav>
        <div className="border-t pt-4 mt-4">
          <p className="text-xs text-gray-400 mb-1">Logged in as</p>
          <p className="text-sm font-semibold text-gray-700">{user?.username}</p>
          <p className="text-xs text-orange-500 mb-3">{user?.role}</p>
          <button onClick={handleLogout}
            className="w-full text-sm bg-gray-100 hover:bg-red-50 hover:text-red-600 text-gray-600 py-2 rounded-lg transition-all">
            Logout
          </button>
        </div>
      </aside>

      {/* Main */}
      <div className="flex-1 flex flex-col overflow-hidden">
        {/* Mobile topbar */}
        <header className="md:hidden flex items-center justify-between px-4 py-3 bg-white border-b">
          <h1 className="text-lg font-bold text-orange-600">🏡 Nestify</h1>
          <button onClick={handleLogout} className="text-xs text-red-500">Logout</button>
        </header>

        <main className="flex-1 overflow-y-auto p-4 md:p-8 pb-20 md:pb-8">
          {children}
        </main>

        {/* Mobile bottom nav */}
        <nav className="md:hidden fixed bottom-0 left-0 right-0 bg-white border-t flex justify-around py-2">
          {NAV.map(n => (
            <NavLink key={n.to} to={n.to} end={n.to === "/"}
              className={({ isActive }) =>
                `flex flex-col items-center text-xs gap-0.5 ${isActive ? "text-orange-600" : "text-gray-400"}`
              }>
              <span className="text-lg">{n.icon}</span>
              <span>{n.label}</span>
            </NavLink>
          ))}
        </nav>
      </div>
    </div>
  );
}

function ProtectedRoute({ children }) {
  const { user } = useAuth();
  return user ? <Layout>{children}</Layout> : <Navigate to="/login" />;
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/"           element={<ProtectedRoute><Dashboard  /></ProtectedRoute>} />
        <Route path="/rooms"      element={<ProtectedRoute><Rooms      /></ProtectedRoute>} />
        <Route path="/tenants"    element={<ProtectedRoute><Tenants    /></ProtectedRoute>} />
        <Route path="/payments"   element={<ProtectedRoute><Payments   /></ProtectedRoute>} />
        <Route path="/complaints" element={<ProtectedRoute><Complaints /></ProtectedRoute>} />
        <Route path="/my-pg"      element={<ProtectedRoute><MyPg      /></ProtectedRoute>} />
        <Route path="/find-pg"    element={<ProtectedRoute><FindPg    /></ProtectedRoute>} />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  );
}