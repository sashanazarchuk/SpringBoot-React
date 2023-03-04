export interface ICategoryItem {
    id: number;
    name: string;
    image: string;
    description: string;
}
export interface ICategoryResponse {
    data: Array<ICategoryItem>,

}

export interface ICategoryState {
    category_list: Array<ICategoryItem>,
}

export enum CategoryActionTypes {
    GET_CATEGORIES = "GET_CATEGORIES_ACTION"
}

export interface GetCategoryAction {
    type: CategoryActionTypes.GET_CATEGORIES,
    payload: ICategoryState
}

export type CategoryAction = | GetCategoryAction
