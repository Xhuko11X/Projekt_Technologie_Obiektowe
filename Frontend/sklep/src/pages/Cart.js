import { useCart } from '../contexts/CartContext';
import { Link } from 'react-router-dom';
import './Cart.css'; // Add your CSS styles here

function Cart() {
  const { cart, updateQuantity, removeFromCart } = useCart();

  const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

  if (cart.length === 0) return <p>Your cart is empty</p>;

  return (
    <div className="cart-container">
      {cart.map(item => (
        <div key={item.id} className="cart-item">
          <h3>{item.name}</h3>
          <p>Price: ${item.price}</p>
          <input
            type="number"
            value={item.quantity}
            onChange={(e) => updateQuantity(item.id, parseInt(e.target.value))}
            min="1"
          />
          <button onClick={() => removeFromCart(item.id)}>Remove</button>
        </div>
      ))}
      <h2>Total: ${total.toFixed(2)}</h2>
      <Link to="/checkout" className="checkout-btn">Go to Checkout</Link>
    </div>
  );
}

export default Cart;
