import { useEffect, useState } from "react";
import api from "../api";

const empty = { tenantName: "", roomNumber: "", issue: "", status: "OPEN" };

export default function Complaints() {
  const [complaints, setComplaints] = useState([]);
  const [modal, setModal]           = useState(false);
  const [form, setForm]             = useState(empty);
  const [loading, setLoading]       = useState(false);

  const load = () => api.get("/complaints").then(r => setComplaints(r.data)).catch(() => {});
  useEffect(() => { load(); }, []);

  const save = async () => {
    setLoading(true);
    try {
      await api.post("/complaints", form);
      setModal(false); load();
    } catch { alert("Error saving complaint"); }
    finally { setLoading(false); }
  };

  const updateStatus = async (id, status) => {
    await api.put(`/complaints/${id}/status`, { status }); load();
  };

  const del = async (id) => {
    if (!confirm("Delete complaint?")) return;
    await api.delete(`/complaints/${id}`); load();
  };

  const statusColor = (s) => ({
    OPEN:        "bg-red-100 text-red-500",
    IN_PROGRESS: "bg-yellow-100 text-yellow-600",
    RESOLVED:    "bg-green-100 text-green-600",
  }[s] || "bg-gray-100 text-gray-500");

  return (
    <div className="fade-in">
      <div className="flex items-center justify-between mb-6">
        <div>
          <h2 className="text-2xl font-bold text-gray-800">Complaints</h2>
          <p className="text-sm text-gray-400">{complaints.length} total</p>
        </div>
        <button onClick={() => { setForm(empty); setModal(true); }}
          className="bg-orange-500 hover:bg-orange-600 text-white px-4 py-2 rounded-xl text-sm font-medium">
          + Add Complaint
        </button>
      </div>

      <div className="space-y-3">
        {complaints.length === 0 && (
          <div className="bg-white rounded-2xl p-10 text-center text-gray-400 shadow-sm">No complaints yet 🎉</div>
        )}
        {complaints.map(c => (
          <div key={c.id} className="bg-white rounded-2xl p-5 shadow-sm flex flex-col sm:flex-row sm:items-center gap-4">
            <div className="flex-1">
              <div className="flex items-center gap-2 mb-1">
                <p className="font-semibold text-gray-800">{c.tenantName}</p>
                <span className="text-gray-400 text-xs">· Room {c.roomNumber}</span>
              </div>
              <p className="text-sm text-gray-600">{c.issue}</p>
            </div>
            <div className="flex items-center gap-2 flex-wrap">
              <span className={`text-xs px-2 py-1 rounded-full font-medium ${statusColor(c.status)}`}>
                {c.status}
              </span>
              {c.status === "OPEN" && (
                <button onClick={() => updateStatus(c.id, "IN_PROGRESS")}
                  className="text-xs bg-yellow-50 text-yellow-600 px-3 py-1 rounded-lg hover:bg-yellow-100">
                  Start
                </button>
              )}
              {c.status === "IN_PROGRESS" && (
                <button onClick={() => updateStatus(c.id, "RESOLVED")}
                  className="text-xs bg-green-50 text-green-600 px-3 py-1 rounded-lg hover:bg-green-100">
                  Resolve
                </button>
              )}
              <button onClick={() => del(c.id)} className="text-xs bg-red-50 text-red-400 px-3 py-1 rounded-lg hover:bg-red-100">
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>

      {modal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl p-6 w-full max-w-sm fade-in shadow-xl">
            <h3 className="font-bold text-lg mb-4">Add Complaint</h3>
            <div className="space-y-3">
              {[["Tenant Name","tenantName"],["Room Number","roomNumber"]].map(([label, field]) => (
                <div key={field}>
                  <label className="block text-xs text-gray-500 mb-1">{label}</label>
                  <input value={form[field]} onChange={e => setForm({ ...form, [field]: e.target.value })}
                    className="w-full border rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
                </div>
              ))}
              <div>
                <label className="block text-xs text-gray-500 mb-1">Issue</label>
                <textarea rows={3} value={form.issue} onChange={e => setForm({ ...form, issue: e.target.value })}
                  className="w-full border rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400 resize-none" />
              </div>
              <div>
                <label className="block text-xs text-gray-500 mb-1">Status</label>
                <select value={form.status} onChange={e => setForm({ ...form, status: e.target.value })}
                  className="w-full border rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400">
                  <option>OPEN</option>
                  <option>IN_PROGRESS</option>
                  <option>RESOLVED</option>
                </select>
              </div>
            </div>
            <div className="flex gap-2 mt-5">
              <button onClick={() => setModal(false)} className="flex-1 border rounded-xl py-2 text-sm">Cancel</button>
              <button onClick={save} disabled={loading}
                className="flex-1 bg-orange-500 text-white rounded-xl py-2 text-sm font-medium disabled:opacity-50">
                {loading ? "Saving..." : "Save"}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}