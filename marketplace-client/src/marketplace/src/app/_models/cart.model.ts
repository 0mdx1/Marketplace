import {Product} from "./product.model";

export interface CartModelServer {
  total: number;
  data: [{
    product: Product,
    numInCart: number
  }];
}

export interface CartModelPublic {
  total: number;
  prodData: [{
    id: number,
    incart: number
  }]
}
