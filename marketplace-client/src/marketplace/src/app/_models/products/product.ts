export class Product {
  id: number;
  goodName: string;
  firmName: string;
  quantity: number;
  price: number;
  unit: string;
  discount: number;
  inStock: boolean;
  status: boolean;
  shippingDate: Date;
  image: string;
  description: string;
  categoryName: string;

  constructor(
    id: number,
    goodName: string,
    firmName: string,
    quantity: number,
    price: number,
    unit: string,
    discount: number,
    inStock: boolean,
    status: boolean,
    shippingDate: Date,
    image: string,
    description: string,
    categoryName: string
  ) {
    this.id = id;
    this.goodName = goodName;
    this.firmName = firmName;
    this.quantity = quantity;
    this.price = price;
    this.unit = unit;
    this.discount = discount;
    this.inStock = inStock;
    this.status = status;
    this.shippingDate = shippingDate;
    this.image = image;
    this.description = description;
    this.categoryName = categoryName;
  }
}
