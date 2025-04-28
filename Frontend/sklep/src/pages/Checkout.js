import { useCart } from '../contexts/CartContext';
import { useAuth } from '../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import './Checkout.css'; // Import your CSS file for styling

function Checkout() {
  const { cart, clearCart } = useCart();
  const { user } = useAuth();
  const navigate = useNavigate();

  const handleCheckout = () => {
    const order = {
      userId: user.id,
      items: cart.map(item => ({ productId: item.id, quantity: item.quantity })),
      totalAmount: cart.reduce((sum, item) => sum + item.price * item.quantity, 0),
    };
    api.post('/orders', order)
      .then(() => {
        clearCart();
        navigate('/orders');
      })
      .catch(err => console.error(err));
  };

  return (
    <div className="checkout-page">
      <h2>Confirm your order</h2>
      <button onClick={handleCheckout}>Place Order</button>
    </div>
  );
}

export default Checkout;
