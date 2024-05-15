import "../assets/css/style.css";
import React, { useState, useEffect, useContext } from "react";
import Menubar from "../Components/Nav/menubar.jsx";
import Tabletrade from "../Components/Trade/Tabletrade.jsx";
import Containergrid from "../Components/Trade/Containergrid.jsx";
import { UserContext } from "../Services/UserDataProvider";
import serverConfig from "../Services/ServerConfig.js";
import Header from "../Components/Nav/Header.jsx";

function Trade() {
  const [walletFilter, setWalletFilter] = useState([]);
  const [limitAsset, setLimitAsset] = useState(14);
  const [containerInputGrid, setContainerInputGrid] = useState(false);
  const [gridData, setGridData] = useState(null);
  const [menuhidden, setMenuhidden] = useState(false);
   const btnIsVisible = document.getElementById("icon-visible");
  const { userProfile, wallet, transactions, updateUserData, setIDUser } =
  useContext(UserContext);
  

 
  useEffect(() => {
    if (wallet) {
      const walletFilter = Array.isArray(wallet)
        ? wallet.slice(0, limitAsset)
        : [];
      setWalletFilter(walletFilter);
    }
  }, [limitAsset, gridData]);

  const getGridData = (data) => {
    setGridData(data);
  };


  function showMoreAssets() {
    setLimitAsset((prevLimit) => prevLimit + 5);
    document
      .getElementsByClassName("table-trades")[0]
      ?.classList.add("show-more");
  }

  function show() {
    setContainerInputGrid(true);
    console.log("Chamou Visible");
    console.log(containerInputGrid);
  }

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

        <Containergrid
          containerInputGrid={containerInputGrid}
          setContainerInputGrid={setContainerInputGrid}
          gridData={gridData}
          addressServer={serverConfig.addressServerTharseo}
          user={userProfile}
        />

        <aside className="container-dashboard-trades">

        <Header />

          <aside className="container-trades">
            <section className="container-dashboard-right-bottom-top">
              <span className="title-active-trades">
                Configurar Automatização
              </span>
              <span className="title-active-trades" onClick={() => show()}>
                Separado para Buttons Filter
              </span>
            </section>

            <section className="container-dashboard-right-bottom-middle container-table-assets-trade">
              <Tabletrade
                table={walletFilter}
                setContainerInputGrid={setContainerInputGrid}
                getGridData={getGridData}
                setGridConfig={userProfile.grids}
                addressServer={serverConfig.addressServerTharseo}
                className="show-more"
                user={userProfile}
              />

              <aside className="container-btn-showmore-trade">
                <span
                  className="btn-showmore-trade"
                  id="btn-showmore-trade"
                  onClick={showMoreAssets}
                >
                  Ver Mais
                </span>
              </aside>
            </section>
          </aside>
        </aside>
      </section>
    </main>
  );
}

export default Trade;
