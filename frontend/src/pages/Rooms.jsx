import { useEffect, useState } from "react";
import api from "../api";

const empty = { roomNumber: "", roomType: "Single", rent: "", available: true };

export default function Rooms() {
  const [rooms, setRooms]   = useState([]);
  const [modal, setModal]   = useState(false);
  const [form, setForm]     = useState(empty);
  const [editId, setEditId] = useState(null);
  const [loading, setLoading] = useState(false);

  const load = () => api.get("/rooms").then(r => setRooms(r.data)).catch(() => {});
  useEffect(() => { load(); }, []);

  const openAdd  = () => { setForm(empty); setEditId(null); setModal(true); };
  const openEdit = (r) => { setForm({ roomNumber: r.roomNumber, roomType: r.roomType, rent: r.rent, available: r.available }); setEditId(r.id); setModal(true); };

  const save = async () => {
    setLoading(true);
    try {
      const payload = { ...form, rent: parseFloat(form.rent) };
      if (editId) await api.put(`/rooms/${editId}`, payload);
      else        await api.post("/rooms", payload);
      setModal(false); load();
    } catch { alert("Error saving room"); }
    finally { setLoading(false); }
  };

  const del = async (id) => {
    if (!confirm("Delete this room?")) return;
    await api.delete(`/rooms/${id}`);
    load();
  };

  const assign = async (id) => {
    await api.put(`/rooms/${id}/assign`);
    load();
  };

  return (
    <div className="fade-in">
      <div className="flex items-center justify-between mb-6">
        <div>
          <h2 className="text-2xl font-bold text-gray-800">Rooms</h2>
          <p className="text-sm text-gray-400">{rooms.length} rooms total</p>
        </div>
        <button onClick={openAdd}
          className="bg-orange-500 hover:bg-orange-600 text-white px-4 py-2 rounded-xl text-sm font-medium">
          + Add Room
        </button>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {rooms.map(r => (
          <div key={r.id} className="bg-white rounded-2xl p-5 shadow-sm">
            <div className="flex justify-between items-start mb-3">
              <div>
                <p className="font-bold text-gray-800 text-lg">Room {r.roomNumber}</p>
                <p className="text-sm text-gray-400">{r.roomType}</p>
              </div>
              <span className={`text-xs px-2 py-1 rounded-full font-medium ${r.available ? "bg-green-100 text-green-600" : "bg-red-100 text-red-500"}`}>
                {r.available ? "Available" : "Occupied"}
              </span>
            </div>
            <p className="text-orange-600 font-bold text-xl mb-4">₹{r.rent}<span className="text-sm text-gray-400 font-normal">/mo</span></p>
            <div className="flex gap-2">
              <button onClick={() => openEdit(r)} className="flex-1 text-xs border border-gray-200 hover:bg-gray-50 py-1.5 rounded-lg">Edit</button>
              {r.available && <button onClick={() => assign(r.id)} className="flex-1 text-xs bg-blue-50 hover:bg-blue-100 text-blue-600 py-1.5 rounded-lg">Assign</button>}
              <button onClick={() => del(r.id)} className="flex-1 text-xs bg-red-50 hover:bg-red-100 text-red-500 py-1.5 rounded-lg">Delete</button>
            </div>
          </div>
        ))}
      </div>

      {modal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl p-6 w-full max-w-sm fade-in shadow-xl">
            <h3 className="font-bold text-lg mb-4">{editId ? "Edit Room" : "Add Room"}</h3>
            <div className="space-y-3">
              <input placeholder="Room Number" value={form.roomNumber}
                onChange={e => setForm({ ...form, roomNumber: e.target.value })}
                className="w-full border rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
              <select value={form.roomType} onChange={e => setForm({ ...form, roomType: e.target.value })}
                className="w-full border rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400">
                {["Single","Double","Triple","Dormitory"].map(t => <option key={t}>{t}</option>)}
              </select>
              <input placeholder="Rent (₹)" type="number" value={form.rent}
                onChange={e => setForm({ ...form, rent: e.target.value })}
                className="w-full border rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
              <label className="flex items-center gap-2 text-sm text-gray-600">
                <input type="checkbox" checked={form.available}
                  onChange={e => setForm({ ...form, available: e.target.checked })} />
                Available
              </label>
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