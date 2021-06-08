export class CartItemCreateDto {
  goodsId: number;
  addingTime: number;
  quantity: number;

  constructor(goodsId: number, addingTime: number, quantity: number) {
    this.goodsId = goodsId;
    this.addingTime = addingTime;
    this.quantity = quantity;
  }
}
