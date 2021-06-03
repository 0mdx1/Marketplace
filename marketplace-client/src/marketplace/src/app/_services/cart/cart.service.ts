import {Product} from "../../_models/product.model";
import {CartItem} from "../../_models/cart-item.model";

export interface CartService{
  addProduct(product: Product): void
  setProductQuantity(product: Product, quantity: number): void;
  removeProduct(product: Product): void
  deleteProduct(product: Product): void

  getCartItems(): CartItem[];
  setCartItems(cartItems: CartItem[]): void;
}
