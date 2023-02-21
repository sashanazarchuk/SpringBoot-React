import './App.css';
import Navbar from './components/tailwind/Navbars';
import Pagination from './components/tailwind/Pagination';
import Home from './components/home';
import { Route, Routes } from 'react-router-dom';
import TeamPage from './components/Team';
import Yourprofile from './components/profile/yourprofile';
import Settings from './components/profile/settings';
import Projects from './components/Projects';
import Calendar from './components/Calendar';
import NoMatchPage from './components/noMatch';
import DefaultLayout from './components/tailwind/Navbars/Layout';

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<DefaultLayout />}>
          <Route path="home" element={<Home />} />
          <Route path="team" element={<TeamPage />} />
          <Route path="yourprofile" element={<Yourprofile />} />
          <Route path="settings" element={<Settings />} />
          <Route path="projects" element={<Projects />} />
          <Route path="calendar" element={<Calendar />} />
          <Route path="*" element={<NoMatchPage />} />
        </Route>
      </Routes>

    </>
  );
}

export default App;
