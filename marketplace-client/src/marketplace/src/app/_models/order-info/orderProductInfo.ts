import {Unit} from "../unit";

export class OrderProductInfo {
  id: number = 0;
  name: string = '';
  category: string = '';
  firm: string = '';
  quantity: number = 0;
  price: number = 0;
  unit: Unit | null = null;

  // constructor(id: number, name: string,
  //             category: string, firm: string,
  //             quantity: number, price: number, unit: Unit) {
  //   this.id = id;
  //   this.name = name;
  //   this.category = category;
  //   this.firm = firm;
  //   this.quantity = quantity;
  //   this.price = price;
  //   this.unit = unit;
  // }
}
