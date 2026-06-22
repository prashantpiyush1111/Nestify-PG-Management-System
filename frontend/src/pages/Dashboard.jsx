import { useEffect, useState } from "react";
import api from "../api";

function StatCard({ icon, label, value, color }) {
  return (
    <div className={`bg-white rounded-2xl p-5 shadow-sm border-l-4 ${color}`}>
      <div className="text-2xl mb-2">{icon}</div>
      <p className="text-2xl font-bold text-gray-800">{value}</p>
      <p className="text-sm text-gray-500 mt-1">{label}</p>
    </div>
  );
}

export default function Dashboard() {
  const [stats, setStats] = useState({ rooms: 0, tenants: 0, pending: 0, complaints: 0 });

  useEffect(() => {
    Promise.all([
      api.get("/rooms"),
      api.get("/tenants"),
      api.get("/payments/pending"),
      api.get("/complaints"),
    ]).then(([rooms, tenants, pending, complaints]) => {
      setStats({
        rooms:      rooms.data.length,
        tenants:    tenants.data.length,
        pending:    pending.data.length,
        complaints: complaints.data.length,
      });
    }).catch(() => {});
  }, []);

  return (
    <div className="fade-in">
      <h2 className="text-2xl font-bold text-gray-800 mb-1">Dashboard</h2>
      <p className="text-sm text-gray-400 mb-6">Overview of your PG</p>

      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard icon="🛏️" label="Total Rooms"       value={stats.rooms}      color="border-orange-400" />
        <StatCard icon="👤" label="Total Tenants"     value={stats.tenants}    color="border-blue-400"   />
        <StatCard icon="⏳" label="Pending Payments"  value={stats.pending}    color="border-yellow-400" />
        <StatCard icon="📋" label="Open Complaints"   value={stats.complaints} color="border-red-400"    />
      </div>

      <div className="mt-8 bg-white rounded-2xl p-6 shadow-sm">
        <h3 className="font-semibold text-gray-700 mb-2">Quick Tips</h3>
        <ul className="text-sm text-gray-500 space-y-1 list-disc list-inside">
          <li>Use Rooms page to add/manage rooms and assign tenants</li>
          <li>Mark payments as paid from the Payments page</li>
          <li>Track and resolve complaints from the Complaints section</li>
        </ul>
      </div>
    </div>
  );
}