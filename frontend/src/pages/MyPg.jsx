import { useEffect, useState } from "react";
import api from "../api";
import { useAuth } from "../AuthContext";

const empty = {
  pgName: "", address: "", city: "", pricePerMonth: "",
  imageUrl: "", rules: "", description: "", contactNumber: ""
};

export default function MyPg() {
  const { user } = useAuth();
  const [form, setForm]       = useState(empty);
  const [pgId, setPgId]       = useState(null);
  const [loading, setLoading] = useState(false);
  const [saved, setSaved]     = useState(false);

  useEffect(() => {
    api.get(`/pg-listings/owner/${user.username}`)
      .then(r => { setForm(r.data); setPgId(r.data.id); })
      .catch(() => {});
  }, []);

  const save = async () => {
    setLoading(true);
    try {
      const payload = { ...form, ownerUsername: user.username, pricePerMonth: parseFloat(form.pricePerMonth) };
      if (pgId) await api.put(`/pg-listings/${pgId}`, payload);
      else      await api.post("/pg-listings", payload);
      setSaved(true);
      setTimeout(() => setSaved(false), 3000);
    } catch { alert("Error saving!"); }
    finally { setLoading(false); }
  };

  const handleChange = (field, value) => {
    setForm(prev => ({ ...prev, [field]: value }));
  };

  return (
    <div className="fade-in max-w-2xl mx-auto">
      <h2 className="text-2xl font-bold text-gray-800 mb-1">My PG Listing</h2>
      <p className="text-sm text-gray-400 mb-6">Apna PG register/update karo — tenants isko dekh sakte hain</p>

      {form.imageUrl && (
        <div className="mb-6 rounded-2xl overflow-hidden h-48 bg-gray-100">
          <img src={form.imageUrl} alt="PG" className="w-full h-full object-cover" />
        </div>
      )}

      <div className="bg-white rounded-2xl p-6 shadow-sm space-y-4">
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-xs font-medium text-gray-500 mb-1">PG Name</label>
            <input value={form.pgName} onChange={e => handleChange("pgName", e.target.value)}
              className="w-full border border-gray-200 rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
          </div>
          <div>
            <label className="block text-xs font-medium text-gray-500 mb-1">City</label>
            <input value={form.city} onChange={e => handleChange("city", e.target.value)}
              className="w-full border border-gray-200 rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
          </div>
          <div>
            <label className="block text-xs font-medium text-gray-500 mb-1">Contact Number</label>
            <input value={form.contactNumber} onChange={e => handleChange("contactNumber", e.target.value)}
              className="w-full border border-gray-200 rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
          </div>
          <div>
            <label className="block text-xs font-medium text-gray-500 mb-1">Price per Month (₹)</label>
            <input type="number" value={form.pricePerMonth} onChange={e => handleChange("pricePerMonth", e.target.value)}
              className="w-full border border-gray-200 rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
          </div>
        </div>

        <div>
          <label className="block text-xs font-medium text-gray-500 mb-1">Full Address</label>
          <input value={form.address} onChange={e => handleChange("address", e.target.value)}
            className="w-full border border-gray-200 rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
        </div>

        <div>
          <label className="block text-xs font-medium text-gray-500 mb-1">Image URL</label>
          <input value={form.imageUrl} onChange={e => handleChange("imageUrl", e.target.value)}
            className="w-full border border-gray-200 rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
        </div>

        <div>
          <label className="block text-xs font-medium text-gray-500 mb-1">Description</label>
          <textarea rows={3} value={form.description} onChange={e => handleChange("description", e.target.value)}
            className="w-full border border-gray-200 rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400 resize-none" />
        </div>

        <div>
          <label className="block text-xs font-medium text-gray-500 mb-1">Rules & Regulations</label>
          <textarea rows={4} value={form.rules} onChange={e => handleChange("rules", e.target.value)}
            className="w-full border border-gray-200 rounded-xl px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400 resize-none" />
        </div>

        {saved && (
          <div className="bg-green-50 text-green-600 text-sm px-4 py-2 rounded-xl">
            ✅ PG listing saved successfully!
          </div>
        )}

        <button onClick={save} disabled={loading}
          className="w-full bg-orange-500 hover:bg-orange-600 text-white font-semibold py-3 rounded-xl transition-all disabled:opacity-50">
          {loading ? "Saving..." : pgId ? "Update PG Listing" : "Register PG Listing"}
        </button>
      </div>
    </div>
  );
}