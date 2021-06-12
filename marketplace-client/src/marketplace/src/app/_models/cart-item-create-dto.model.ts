import {CartItem} from "./cart-item.model";

export class CartItemCreateDto {
  goodsId: number;
  addingTime: number;
  quantity: number;

  constructor(goodsId: number, addingTime: number, quantity: number) {
    this.goodsId = goodsId;
    this.addingTime = addingTime;
    this.quantity = quantity;
  }

  public static mapToDto(item: CartItem): CartItemCreateDto {
    return new CartItemCreateDto(item.goods.id,item.addingTime,item.quantity);
  }
}
