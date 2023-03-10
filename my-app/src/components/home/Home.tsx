import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import http from "../../http_common";
import { CategoryActionTypes, ICategoryItem } from "./types";


const Home = () => {
  const dispatch = useDispatch();
  const list = useSelector((store: any) => store.categories as Array<ICategoryItem>);
  const fetchCategories = async () => {
    http.get<Array<ICategoryItem>>("/api/categories").then((res) => {
      console.log("List categories", res);
      const { data } = res;
      dispatch({ type: CategoryActionTypes.GET_CATEGORIES, payload: data });
    });
  }
  const handleDeleteCategory = async (id: number) => {
    console.log("Deleting category with id:", id);
    try {
      await http.delete(`/api/categories/${id}`);
      dispatch({ type: CategoryActionTypes.DELETE_CATEGORY, payload: id });
      fetchCategories();
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  return (
    <>
      <div className="bg-gray-100">
        <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
          <div className="mx-auto max-w-2xl py-16 sm:py-24 lg:max-w-none lg:py-32">
            <h2 className="text-2xl font-bold text-gray-900">Колекції</h2>

            <div className="mt-6 space-y-12 lg:grid lg:grid-cols-3 lg:gap-x-6 lg:space-y-0">
              {list.map((c) => (
                <div key={c.id} className="group relative">
                  <div className="relative h-80 w-full overflow-hidden rounded-lg bg-white group-hover:opacity-75 sm:aspect-w-2 sm:aspect-h-1 sm:h-64 lg:aspect-w-1 lg:aspect-h-1">
                    <img
                      src={"http://localhost:8080/files/600_" + c.image}
                      alt={c.name}
                      className="h-full w-full object-cover object-center"
                    />
                  </div>
                  <h3 className="text-sm text-gray-500">
                    <span>
                      {c.name}
                    </span>
                  </h3>
                  <p className="text-base font-semibold text-gray-900">
                    {c.description}
                  </p>
                  <button type="button" onClick={() => handleDeleteCategory(c.id)} className="bg-red-500 hover:bg-red-600 text-white font-medium py-2 px-4 rounded-md mb-5">Delete</button>
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