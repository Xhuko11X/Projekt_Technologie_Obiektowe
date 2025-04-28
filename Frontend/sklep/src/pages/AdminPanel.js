import { useAuth } from '../contexts/AuthContext';
import './AdminPanel.css'; // Dodajemy CSS do stylizacji

function AdminPanel() {
  const { user } = useAuth();

  if (!user || user.role !== 'ADMIN') {
    return <p>Access denied</p>;
  }

  return (
    <div className="admin-panel">
      <h2>Admin Dashboard</h2>
      <p>Manage products, users and orders</p>
      {/* Możesz tu dodać formularze do zarządzania */}
    </div>
  );
}

export default AdminPanel;
