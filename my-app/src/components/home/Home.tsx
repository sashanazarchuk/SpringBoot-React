import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { APP_ENV } from "../../env";
import http from "../../http_common";
import { CategoryActionTypes, ICategoryItem } from "./types";


const Home = () => {
  const dispatch = useDispatch();
  const list = useSelector((store: any) => store.categories as Array<ICategoryItem>);
  useEffect(() => {
    //роблю запит до сервера за списком категорій
    http.get<Array<ICategoryItem>>("/api/categories").then((res) => {
      console.log("List categories", res);
      //з сервера отримую список категорій
      const { data } = res;
      //викликаю dispatch для відправки action до store для оновлення стану додатку
      dispatch({ type: CategoryActionTypes.GET_CATEGORIES, payload: data });
    });
  }, []);




  return (
    <>
      <div className="bg-gray-100">
        <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
          <div className="mx-auto max-w-2xl py-16 sm:py-24 lg:max-w-none lg:py-32">
            <h2 className="text-2xl font-bold text-gray-900">Колекції</h2>

            <div className="mt-6 space-y-12 lg:grid lg:grid-cols-3 lg:gap-x-6 lg:space-y-0">
              {list.map((c) => (
                <div key={c.name} className="group relative">
                  <div className="relative h-80 w-full overflow-hidden rounded-lg bg-white group-hover:opacity-75 sm:aspect-w-2 sm:aspect-h-1 sm:h-64 lg:aspect-w-1 lg:aspect-h-1">
                    <img
                      src={APP_ENV.REMOTE_HOST_NAME + "files/" + c.image}
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