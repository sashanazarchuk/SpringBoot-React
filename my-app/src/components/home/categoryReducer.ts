import { ICategoryItem, CategoryActionTypes } from './types';

//оголошую змінну яка буде містити массив категорій
const initState: Array<ICategoryItem> = [
    // {
    //     id: 1,
    //     name: "Dogi",
    //     description: "Dogi dogi",
    //     image: "dogi.jpg"
    // }

];


//експортую функцію редюсера який приймає state і action в якості параметрів 
export const CategoryReducer = (state = initState, action: any) => {
    switch (action.type) {
        //описую запит на отримання списку категорій
        case CategoryActionTypes.GET_CATEGORIES: {
            //отримую список категорій з сервера і конвертую в масив
            const payload: Array<ICategoryItem> = action.payload as [];
            //повертаю дані отримані з сервера
            return payload;
        }
        case CategoryActionTypes.CREATE_CATEGORY: {
            return [
                ...state, action.payload
            ];
        }
        case CategoryActionTypes.DELETE_CATEGORY: {
            return state.filter(category => category.id !== action.payload);
        }
        default:
            return state;
    }
    //повертаю початковий стан якщо тип дії не відповідає жодному з визначених
    return state;
}