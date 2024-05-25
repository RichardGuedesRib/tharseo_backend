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
  const [limitActiveTrade, setLimiteActiveTrade] = useState(14);
  const [containerInputGrid, setContainerInputGrid] = useState(false);
  const [gridData, setGridData] = useState(null);

  const { userProfile, wallet, updateUserData } = useContext(UserContext);

  useEffect(() => {
    updateUserData();
  }, [limitActiveTrade, gridData]);

  const getGridData = (data) => {
    setGridData(data);
  };

  function showMoreAssets() {
    setLimiteActiveTrade((prevLimit) => prevLimit + 5);
    document
      .getElementsByClassName("table-trades")[0]
      ?.classList.add("show-more");
  }

  return (
    <main className="app-dashboard">
      <section className="container-dashboard">
        <Menubar />

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
              <span className="title-active-trades">
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
                limitActiveTrade={limitActiveTrade}
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
