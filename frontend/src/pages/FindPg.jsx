import { useEffect, useState } from "react";
import api from "../api";

export default function FindPg() {
  const [pgs, setPgs]       = useState([]);
  const [search, setSearch] = useState("");
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    api.get("/pg-listings").then(r => setPgs(r.data)).catch(() => {});
  }, []);

  const filtered = pgs.filter(p =>
    p.pgName?.toLowerCase().includes(search.toLowerCase()) ||
    p.city?.toLowerCase().includes(search.toLowerCase()) ||
    p.address?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="fade-in">
      <h2 className="text-2xl font-bold text-gray-800 mb-1">Find PG</h2>
      <p className="text-sm text-gray-400 mb-6">Available PGs browse karo</p>

      {/* Search */}
      <input placeholder="🔍 City ya PG naam se search karo..."
        value={search} onChange={e => setSearch(e.target.value)}
        className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm mb-6 focus:outline-none focus:ring-2 focus:ring-orange-400" />

      {filtered.length === 0 && (
        <div className="bg-white rounded-2xl p-10 text-center text-gray-400 shadow-sm">
          Koi PG nahi mila 😔
        </div>
      )}

      {/* PG Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {filtered.map(pg => (
          <div key={pg.id} onClick={() => setSelected(pg)}
            className="bg-white rounded-2xl shadow-sm overflow-hidden cursor-pointer hover:shadow-md transition-all">
            <div className="h-40 bg-gray-100">
              {pg.imageUrl
                ? <img src={pg.imageUrl} alt={pg.pgName} className="w-full h-full object-cover" />
                : <div className="w-full h-full flex items-center justify-center text-4xl">🏠</div>
              }
            </div>
            <div className="p-4">
              <h3 className="font-bold text-gray-800">{pg.pgName}</h3>
              <p className="text-xs text-gray-400 mt-0.5">📍 {pg.city}</p>
              <p className="text-orange-600 font-bold text-lg mt-2">
                ₹{pg.pricePerMonth}<span className="text-xs text-gray-400 font-normal">/mo</span>
              </p>
              <button className="mt-3 w-full bg-orange-50 hover:bg-orange-100 text-orange-600 text-sm font-medium py-1.5 rounded-lg transition-all">
                View Details
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* Detail Modal */}
      {selected && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4"
          onClick={() => setSelected(null)}>
          <div className="bg-white rounded-2xl w-full max-w-lg max-h-[90vh] overflow-y-auto fade-in shadow-xl"
            onClick={e => e.stopPropagation()}>
            {selected.imageUrl && (
              <img src={selected.imageUrl} alt={selected.pgName} className="w-full h-52 object-cover rounded-t-2xl" />
            )}
            <div className="p-6">
              <h2 className="text-xl font-bold text-gray-800 mb-1">{selected.pgName}</h2>
              <p className="text-sm text-gray-400 mb-4">📍 {selected.address}, {selected.city}</p>

              <div className="bg-orange-50 rounded-xl px-4 py-3 mb-4">
                <p className="text-orange-600 text-2xl font-bold">₹{selected.pricePerMonth}<span className="text-sm font-normal text-gray-400">/month</span></p>
              </div>

              {selected.description && (
                <div className="mb-4">
                  <p className="text-xs font-semibold text-gray-500 uppercase mb-1">About</p>
                  <p className="text-sm text-gray-600">{selected.description}</p>
                </div>
              )}

              {selected.rules && (
                <div className="mb-4">
                  <p className="text-xs font-semibold text-gray-500 uppercase mb-2">Rules & Regulations</p>
                  <ul className="space-y-1">
                    {selected.rules.split("\n").map((r, i) => (
                      <li key={i} className="text-sm text-gray-600 flex gap-2">
                        <span className="text-orange-400">•</span>{r}
                      </li>
                    ))}
                  </ul>
                </div>
              )}

              {selected.contactNumber && (
                <div className="bg-gray-50 rounded-xl px-4 py-3 mb-4">
                  <p className="text-xs text-gray-400 mb-1">Contact</p>
                  <p className="text-sm font-semibold text-gray-700">📞 {selected.contactNumber}</p>
                </div>
              )}

              <button onClick={() => setSelected(null)}
                className="w-full border border-gray-200 rounded-xl py-2 text-sm text-gray-500 hover:bg-gray-50">
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}