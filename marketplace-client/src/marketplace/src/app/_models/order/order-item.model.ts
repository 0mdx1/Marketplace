import {CartItem} from '../cart-item.model';

export class OrderItemModel {
  categoryName: string;
  description: string;
  discount: number;
  firmName: string;
  goodName: string;
  goodsId: number;
  inStock: boolean;
  price: number;
  unit: string;
  quantity: number;
  addingTime: number;

  constructor(item: CartItem) {
    this.categoryName = item.goods.categoryName;
    this.description = item.goods.description;
    this.discount = item.goods.discount;
    this.firmName = item.goods.goodName;
    this.goodsId = item.goods.id;
    this.goodName = item.goods.goodName;
    this.inStock = item.goods.inStock;
    this.price = item.goods.price;
    this.unit = item.goods.unit;
    this.quantity = item.goods.quantity;
    this.addingTime = item.addingTime;
  }
}
