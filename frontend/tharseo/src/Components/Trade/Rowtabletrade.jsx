import React, { useEffect, useState, useContext } from "react";
import Icon from "react-crypto-icons";
import serverConfig from "../../Services/ServerConfig";
import { UserContext } from "../../Services/UserDataProvider";

function Rowtabletrades({
  id,
  symbol,
  name,
  balance,
  profit,
  trade,
  performance,
  isActive,
  setContainerInputGrid,
  getGridData,
  setGridConfig,
}) {
  const [toggle, setToggle] = useState(isActive);
  const [toggleColor, setToggleColor] = useState("#EAECEF");
  const [performanceColor, setPerformanceColor] = useState("#EAECEF");
  const [asset, setAsset] = useState(null);
  const { userProfile } = useContext(UserContext);

  useEffect(() => {
    setAsset(isActive);
  }, []);

  function toggled() {
    setToggle(!toggle);

    const isActive = { isActive: toggle ? 0 : 1 };

    const urlRequest = `${serverConfig.addressServerTharseo}/strategygriduser/insertgrid?user=${userProfile.id}&acronym=${name}`;

    fetch(urlRequest, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(isActive),
    })
      .then((res) => {
        if (!res.ok) {
          console.error("Error when requesting post grid");
        }
        console.log("Request Grid Succedly!");
        alert("OK!");
        setToggleColor(toggle ? "#EAECEF" : "#006BFA");
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function openGrid() {
    const asset = setGridConfig.filter((item) => {
      return item.acronym === name;
    });
    if (asset.length > 0) {
      setAsset(asset);

      const dataArray = [];
      const getPerformance = asset[0].performance;
      const getProfit = asset[0].profit;
      const getIsActive = asset[0].isActive;

      let quota = 0;
      let nGrid = 0;
      let percentGrid = 0;
      let valueBase = 0;

      if (asset[0].configStrategy) {
        const getConfigStrategy = JSON.parse(
          asset[0].configStrategy.replace(/\\/g, "")
        );
        quota = getConfigStrategy.quota;
        nGrid = getConfigStrategy.nGrid;
        percentGrid = getConfigStrategy.percentGrid;
        valueBase = getConfigStrategy.valueBase;
      }

      getGridData({
        id,
        name,
        getPerformance,
        getProfit,
        getIsActive,
        quota,
        nGrid,
        percentGrid,
        valueBase,
      });
    } else {
      getGridData({ id, name });
    }

    setContainerInputGrid(true);
  }

  return (
    <tr className="row-table-trades">
      <td className="label-active-trades row-media-icon">
        <span className="icon-active-trades ">
          {" "}
          <Icon name={symbol} size={25} />
        </span>
      </td>

      <td className="label-active-trades label-row-name row-media-name">{name}</td>
      <td className="label-active-trades row-media-balance">$ {balance}</td>
      <td className="label-active-trades row-media-profit">$ {profit}</td>
      <td className="label-active-trades row-media-performance">{performance}%</td>
      <td className="label-active-trades container-btn-active-trade row-media-btn">
        <span className="btn-trades" onClick={openGrid}>
          {trade}
        </span>
      </td>
      <td className="label-active-trades label-toogle-trade row-media-toggle">
        <span className="btn-toogle-trade " onClick={toggled}>
          <span
            class="material-symbols-outlined"
            id="btn-toogle"
            style={{ fontSize: 35, color: toggle ? "#006BFA" : "#EAECEF" }}
          >
            {toggle ? "toggle_on" : "toggle_off"}
          </span>
        </span>
      </td>
    </tr>
  );
}

export default Rowtabletrades;
