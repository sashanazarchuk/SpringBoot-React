import { Link } from "react-router-dom";

const NoMatchPage = () => {
  
    return (
        <>
            <h1>404</h1>
            <Link to ="/">Перейти на головну сторінку</Link> {}
        </>
    );

};
export default NoMatchPage;