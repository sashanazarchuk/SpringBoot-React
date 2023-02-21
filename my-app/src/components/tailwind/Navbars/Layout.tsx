import { Outlet } from "react-router-dom";
import Navbar from ".";
import Pagination from "../Pagination";


const DefaultLayout = () => {
    return (
        <>
            <Navbar />
            <div className="container">
                <Outlet />
            </div>
            <Pagination />
        </>
    );
}

export default DefaultLayout;