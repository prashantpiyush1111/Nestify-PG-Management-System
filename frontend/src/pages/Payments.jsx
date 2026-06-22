import { useEffect, useState } from "react";
import api from "../api";

const empty = { tenantName: "", roomNumber: "", amount: "", paymentDate: "", dueDate: "", status: "PENDING" };

export default function Payments() {
  const [payments, setPayments] = useState([]);
  const [filter, setFilter]     = useState("ALL");
  const [modal, setModal]       = useState(false);
  const [form, setForm]         = useState(empty);
  const [loading, setLoading]   = useState(false);

  const load = () => api.get("/payments").then(r => setPayments(r.data)).catch(() => {});
  useEffect(() => { load(); }, []);

  const displayed = filter === "PENDING"
    ? payments.filter(p => p.status === "PENDING")
    : payments;

  const markPaid = async (id) => {
    await api.put(`/payments/${id}/pay`); load();
  };

  const save = async () => {
    setLoading(true);
    try {
      await api.post("/payments", { ...form, amount: parseFloat(form.amount) });
      setModal(false); load();
    } catch { alert("Error saving payment"); }
    finally { setLoading(false); }
  };

  const statusBadge = (s) => s === "PAID"
    ? "bg-green-100 text-green-600"
    : "bg-yellow-100 text-yellow-600";

  return (
    <div className="fade-in">
      <div className="flex items-center justify-between mb-6">
        <div>
          <h2 className="text-2xl font-bold text-gray-800">Payments</h2>
          <p className="text-sm text-gray-400">{payments.length} records</p>
        </div>
        <button onClick={() => { setForm(empty); setModal(true); }}
          className="bg-orange-500 hover:bg-orange-600 text-white px-4 py-2 rounded-xl text-sm font-medium">
          + Add Payment
        </button>
      </div>

      <div className="flex gap-2 mb-4">
        {["ALL","PENDING"].map(f => (
          <button key={f} onClick={() => setFilter(f)}
            className={`px-4 py-1.5 rounded-xl text-sm font-medium transition-all
              ${filter === f ? "bg-orange-500 text-white" : "bg-white border text-gray-500 hover:bg-gray-50"}`}>
            {f}
          </button>
        ))}
      </div>

      <div className="bg-white rounded-2xl shadow-sm overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 border-b">
            <tr>{["Tenant","Room","Amount","Due Date","Status","Action"].map(h => (
              <th key={h} className="text-left px-4 py-3 text-xs font-semibold text-gray-500 uppercase tracking-wide">{h}</th>
            ))}</tr>
          </thead>
          <tbody>
            {displayed.length === 0 && (
              <tr><td colSpan={6} className="text-center text-gray-400 py-10">No payments found</td></tr>
            )}
            {displayed.map(p => (
              <tr key={p.id} className="border-b last:border-0 hover:bg-gray-50">
                <td className="px-4 py-3 font-medium">{p.tenantName}</td>
                <td className="px-4 py-3 text-gray-500">Room {p.roomNumber}</td>
                <td className="px-4 py-3 text-orange-600 font-semibold">₹{p.amount}</td>
                <td className="px-4 py-3 text-gray-500">{p.dueDate || "—"}</td>
                <td className="px-4 py-3">
                  <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${statusBadge(p.status)}`}>
                    {p.status}
                  </span>
                </td>
                <td className="px-4 py-3">
                  {p.status === "PENDING" && (
                    <button onClick={() => markPaid(p.id)}
                      className="text-xs bg-green-50 text-green-600 px-3 py-1 rounded-lg hover:bg-green-100">
                      Mark Paid
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {modal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl p-6 w-full max-w-sm fade-in shadow-xl">
            <h3 className="font-bold text-lg mb-4">Add Payment</h3>
            <div className="space-y-3">
              {[
                ["Tenant Name","tenantName","text"],
                ["Room Number","roomNumber","text"],
                ["Amount (₹)","amount","number"],
                ["Payment Date","paymentDate","date"],
                ["Due Date","dueDate","date"],
              ].map(([label, field, type]) => (
                <div key={field}>
                  <label className="block text-xs text-gray-500 mb-1">{label}</label>
                  <input type={type} value={form[field]}
                    onChange={e => setForm({ ...form, [field]: e.target.value })}
                    className="w-full border rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
                </div>
              ))}
              <div>
                <label className="block text-xs text-gray-500 mb-1">Status</label>
                <select value={form.status} onChange={e => setForm({ ...form, status: e.target.value })}
                  className="w-full border rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400">
                  <option>PENDING</option>
                  <option>PAID</option>
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