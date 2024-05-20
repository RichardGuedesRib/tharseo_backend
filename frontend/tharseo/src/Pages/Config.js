import "../assets/css/style.css";
import React, { useState, useEffect } from "react";
import Menubar from "../Components/Nav/menubar.jsx";
import Header from "../Components/Nav/Header.jsx"

function Config() {
  const [menuhidden, setMenuhidden] = useState(false);
  const btnIsVisible = document.getElementById("icon-visible");
  let visibleBalance = false;


  useEffect(() => {}, []);

 
  return (
    <main className="app-dashboard">
      <section
        className="menu-hidden"
        onClick={() => {
          setMenuhidden(!menuhidden);
        }}
      >
        <span class="material-symbols-outlined" style={{ fontSize: 30 }}>
          menu
        </span>
      </section>
      <section className="container-dashboard">
        <Menubar menuhidden={menuhidden} />

        <aside className="container-dashboard-trades">
         
        <Header />

          <aside className="container-trades container-config">
            <section className="container-dashboard-right-bottom-top">
              <span className="title-active-trades">Configurações</span>
            </section>

            <section className="container-dashboard-right-bottom-middle container-table-assets-trade container-item-config">
              <aside className="conteiner-line-config">
                <span className="config-line">
                  Sincronizar ativos com corretora
                </span>
                <span className="btn-config update">Sincronizar Agora</span>
              </aside>
            </section>
          </aside>
        </aside>
      </section>
    </main>
  );
}

export default Config;
