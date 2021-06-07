export class Filter {
  category: string;
  minPrice: number;
  maxPrice: number;
  sort: string;
  direction: string;

  constructor(
    category: string,
    minPrice: number,
    maxPrice: number,
    sort: string,
    direction: string
  ) {
    this.category = category;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.sort = sort;
    this.direction = direction;
  }
}
