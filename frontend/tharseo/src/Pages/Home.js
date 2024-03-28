import "../assets/css/style.css";
import React from "react";

import Menubar from "../Components/menubar.jsx";
import Chart from "../Components/Chart.jsx";

function Home({ chart }) {
  return (
    <main className="app-dashboard">
      <section className="container-dashboard">
        <Menubar />
        <aside className="container-dashboard-right">
          <aside className="container-dashboard-right-top"></aside>
          <aside className="container-dashboard-right-middle">
            <Chart data={chart} />
          </aside>
          <aside className="container-dashboard-right-bottom"></aside>
        </aside>
      </section>
    </main>
  );
}

export default Home;
