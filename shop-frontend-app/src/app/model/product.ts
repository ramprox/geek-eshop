import {Category} from "./category";

export class Product {

  constructor(public id: number,
              public name: string,
              public description: string,
              public shortDescription: string,
              public cost: number,
              public category: Category,
              public pictureIds: number[],
              public mainPictureId: number) {
  }
}
