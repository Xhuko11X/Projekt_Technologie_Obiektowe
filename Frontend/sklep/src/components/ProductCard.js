import { Link } from 'react-router-dom';
import './ProductCard.css';

function ProductCard({ product }) {
  return (
    <div className="product-card">
      <h3>{product.name}</h3>
      <p>{product.description}</p>
      <p><strong>${product.price}</strong></p>
      <Link to={`/product/${product.id}`} className="details-btn">View Details</Link>
    </div>
  );
}

export default ProductCard;
