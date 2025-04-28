import { useEffect, useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import api from '../services/api';
import './Orders.css'; // Import your CSS file for styling

function Orders() {
  const { user } = useAuth();
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    api.get(`/users/${user.id}/orders`)
      .then(res => setOrders(res.data))
      .catch(err => console.error(err));
  }, [user]);

  return (
    <div className="orders-page">
      <h2>Your Orders</h2>
      {orders.map(order => (
        <div key={order.id} className="order-card">
          <p>Status: {order.status}</p>
          <p>Total: ${order.totalAmount}</p>
        </div>
      ))}
    </div>
  );
}

export default Orders;
