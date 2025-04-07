import { Author } from "../../author/model/Autor";
import { Category } from "../../category/model/Category";

export class Game {
    id: number;
    title: string;
    age: number;
    category: Category;
    author: Author;
}