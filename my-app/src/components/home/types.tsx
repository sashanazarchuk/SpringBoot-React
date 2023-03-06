export interface ICategoryItem {
    id: number;
    name: string;
    description: string;
    image: string;
}


export enum CategoryActionTypes {
    GET_CATEGORIES = "GET_CATEGORIES_ACTION"
}
