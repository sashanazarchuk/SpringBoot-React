import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import http from "../../http_common";
import { ICategoryItem, ICategoryResponse } from "./types";
 import { CategoryActionTypes, GetCategoryAction, ICategoryState } from "./types";


const Home = () => {
  const [categories, setCategories] = useState<Array<ICategoryItem>>([]);
  // const { category_list } = useSelector((state: any) => state.product as ICategoryState);
  // const dispatch = useDispatch();

  useEffect(() => {

    http.get<Array<ICategoryItem>>("/api/categories").then((res) => {
      console.log("List categories", res);
      // const { data } = res;
      setCategories(res.data);
      // const payload: ICategoryState = {
      //   category_list: data.data
      // };
      // const action: GetCategoryAction = {
      //   type: CategoryActionTypes.GET_CATEGORIES,
      //   payload: payload
      // }
      // dispatch(action);
    });
  }, []);




  return (
    <>
      <div className="bg-gray-100">
        <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
          <div className="mx-auto max-w-2xl py-16 sm:py-24 lg:max-w-none lg:py-32">
            <h2 className="text-2xl font-bold text-gray-900">Колекції</h2>

            <div className="mt-6 space-y-12 lg:grid lg:grid-cols-3 lg:gap-x-6 lg:space-y-0">
              {categories.map((c) => (
                <div key={c.name} className="group relative">
                  <div className="relative h-80 w-full overflow-hidden rounded-lg bg-white group-hover:opacity-75 sm:aspect-w-2 sm:aspect-h-1 sm:h-64 lg:aspect-w-1 lg:aspect-h-1">
                    <img
                      src={`http://localhost:8080/files/${c.image}`}
                      className="h-full w-full object-cover object-center"
                    />
                  </div>
                  <h3 className="mt-6 text-sm text-gray-500">
                    <a>
                      <span className="absolute inset-0" />
                      {c.name}
                    </a>
                  </h3>
                  <p className="text-base font-semibold text-gray-900">{c.description}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>

    </>
  )
}

export default Home;