import { applyMiddleware, combineReducers, createStore } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import thunk from "redux-thunk";
import { categoryReducer } from "../components/home/categoryReducer";

export const rootReducer=combineReducers({
    category: categoryReducer

});

export const store =createStore(rootReducer,
    composeWithDevTools(applyMiddleware(thunk)));