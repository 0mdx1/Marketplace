import {CartItem} from "../../_models/cart-item.model";

export interface Cart{
  addItem(item: CartItem): void;
  updateItem(item: CartItem): void;
  removeItem(item: CartItem): void;
  getItem(productId: number): CartItem | null;
  empty(): void;
  setItems(items: CartItem[]): void;
  getItems(): CartItem[];
}
