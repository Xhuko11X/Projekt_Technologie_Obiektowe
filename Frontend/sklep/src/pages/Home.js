import { useEffect, useState } from 'react';
import api from '../services/api';
import ProductCard from '../components/ProductCard';
import './Home.css';

function Home() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    api.get('/products')
      .then(res => setProducts(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div className="products-container">
      {products.map(p => <ProductCard key={p.id} product={p} />)}
    </div>
  );
}

export default Home;
