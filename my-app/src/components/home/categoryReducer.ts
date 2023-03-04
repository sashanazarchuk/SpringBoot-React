import { CategoryAction, CategoryActionTypes, ICategoryState } from "./types";

const initialState: ICategoryState = {
    category_list: []
};

export const categoryReducer = (state = initialState, action: CategoryAction): ICategoryState => {

    switch (action.type) {
        case CategoryActionTypes.GET_CATEGORIES: {
            return {
                ...state,
                ...action.payload
            }
        }
    }
    return state;
}
export default categoryReducer