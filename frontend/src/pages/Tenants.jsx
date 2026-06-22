import { useEffect, useState } from "react";
import api from "../api";

const empty = { name: "", phoneNumber: "", idProof: "", assignedRoomNumber: "" };

export default function Tenants() {
  const [tenants, setTenants] = useState([]);
  const [modal, setModal]     = useState(false);
  const [form, setForm]       = useState(empty);
  const [editId, setEditId]   = useState(null);
  const [loading, setLoading] = useState(false);

  const load = () => api.get("/tenants").then(r => setTenants(r.data)).catch(() => {});
  useEffect(() => { load(); }, []);

  const openAdd  = () => { setForm(empty); setEditId(null); setModal(true); };
  const openEdit = (t) => { setForm({ name: t.name, phoneNumber: t.phoneNumber, idProof: t.idProof, assignedRoomNumber: t.assignedRoomNumber }); setEditId(t.id); setModal(true); };

  const save = async () => {
    setLoading(true);
    try {
      if (editId) await api.put(`/tenants/${editId}`, form);
      else        await api.post("/tenants", form);
      setModal(false); load();
    } catch { alert("Error saving tenant"); }
    finally { setLoading(false); }
  };

  const del = async (id) => {
    if (!confirm("Delete this tenant?")) return;
    await api.delete(`/tenants/${id}`); load();
  };

  const F = ({ label, field, placeholder }) => (
    <div>
      <label className="block text-xs text-gray-500 mb-1">{label}</label>
      <input placeholder={placeholder || label} value={form[field]}
        onChange={e => setForm({ ...form, [field]: e.target.value })}
        className="w-full border rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
    </div>
  );

  return (
    <div className="fade-in">
      <div className="flex items-center justify-between mb-6">
        <div>
          <h2 className="text-2xl font-bold text-gray-800">Tenants</h2>
          <p className="text-sm text-gray-400">{tenants.length} tenants registered</p>
        </div>
        <button onClick={openAdd}
          className="bg-orange-500 hover:bg-orange-600 text-white px-4 py-2 rounded-xl text-sm font-medium">
          + Add Tenant
        </button>
      </div>

      <div className="bg-white rounded-2xl shadow-sm overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 border-b">
            <tr>{["Name","Phone","ID Proof","Room","Actions"].map(h => (
              <th key={h} className="text-left px-4 py-3 text-xs font-semibold text-gray-500 uppercase tracking-wide">{h}</th>
            ))}</tr>
          </thead>
          <tbody>
            {tenants.length === 0 && (
              <tr><td colSpan={5} className="text-center text-gray-400 py-10">No tenants yet</td></tr>
            )}
            {tenants.map(t => (
              <tr key={t.id} className="border-b last:border-0 hover:bg-gray-50">
                <td className="px-4 py-3 font-medium text-gray-800">{t.name}</td>
                <td className="px-4 py-3 text-gray-500">{t.phoneNumber}</td>
                <td className="px-4 py-3 text-gray-500">{t.idProof}</td>
                <td className="px-4 py-3">
                  <span className="bg-blue-50 text-blue-600 px-2 py-0.5 rounded-full text-xs font-medium">
                    {t.assignedRoomNumber || "—"}
                  </span>
                </td>
                <td className="px-4 py-3">
                  <div className="flex gap-2">
                    <button onClick={() => openEdit(t)} className="text-xs border px-3 py-1 rounded-lg hover:bg-gray-50">Edit</button>
                    <button onClick={() => del(t.id)} className="text-xs bg-red-50 text-red-500 px-3 py-1 rounded-lg hover:bg-red-100">Delete</button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {modal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl p-6 w-full max-w-sm fade-in shadow-xl">
            <h3 className="font-bold text-lg mb-4">{editId ? "Edit Tenant" : "Add Tenant"}</h3>
            <div className="space-y-3">
              <F label="Name" field="name" />
              <F label="Phone Number" field="phoneNumber" />
              <F label="ID Proof" field="idProof" placeholder="Aadhar/PAN/etc" />
              <F label="Assigned Room Number" field="assignedRoomNumber" />
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