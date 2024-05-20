import React, { useState, useEffect } from "react";
import ChartConstructor from "./ChartConstructor";

function Chart({ data, wallet, setContainerOrder }) {
  const [assetSelected, setAssetSelected] = useState("BNBUSDT");
  const [valueAsset, setValueAsset] = useState(null);
  const [assetProfitValue, setAssetProfitValue] = useState(0.0);
  const [assetProfitPercent, setAssetProfitPercent] = useState(0.0);

  useEffect(() => {
    const getValueAsset = () => {
      if (wallet && wallet.length > 0) {
        const item = wallet.find((item) => item.acronym === assetSelected);
        if (item) {
          setValueAsset(item.price ? item.price.toFixed(2) : 0.0);
        } else {
          setValueAsset(0.0);
        }
      } else {
        setValueAsset(null);
      }
    };

    getValueAsset();
  }, [assetSelected, wallet]);

  const btnSelectAsset = document.getElementById("btn-Select-asset");
  if (btnSelectAsset) {
    btnSelectAsset.addEventListener("click", () => {
      setAssetSelected("ETHUSDT");
    });
  }

  return (
    <div className="app-chart">
      <div className="container-header-data">
        <div className="chartData">
          <div className="chart-icon-data">
            <span class="material-symbols-outlined" style={{ fontSize: 35 }}>
              payments
            </span>
          </div>
          <div className="chart-item-data">
            <span className="title-data">Valor Total</span>
            <span className="value-data">
              $ {valueAsset}
            </span>
          </div>
        </div>
        <div className="chartData">
          <div className="chart-icon-data">
            <span class="material-symbols-outlined" style={{ fontSize: 35 }}>
              monetization_on
            </span>
          </div>
          <div className="chart-item-data">
            <span className="title-data">Profit</span>
            <span className="value-data">$ {assetProfitValue}</span>
          </div>
        </div>
        <div className="chartData">
          <div className="chart-icon-data">
            <span class="material-symbols-outlined" style={{ fontSize: 35 }}>
              finance_mode
            </span>
          </div>
          <div className="chart-item-data">
            <span className="title-data">Profit</span>
            <span className="value-data">{assetProfitPercent}%</span>
          </div>
        </div>
      </div>

      <div className="container-header-options">
        <div className="container-header-options-top">
          <span className="title-header-options">Performance do Ativo</span>
        </div>
        <div className="container-header-options-bottom">
          <div className="container-header-options-bottom-left">
            <div className="button-asset">
              <span className="icon-asset-btn"></span>
              <span className="name-asset-btn">{assetSelected}</span>
              <span className="select-asset-btn" id="btn-Select-asset">
                <span class="material-symbols-outlined">expand_more</span>
              </span>
            </div>
            <div className="type-chart">
              <span class="material-symbols-outlined" style={{ fontSize: 40 }}>
                candlestick_chart
              </span>
            </div>
            <div
              className="btn-purchase"
              id="btn-purchase-manual"
              onClick={() => setContainerOrder(true)}
            >
              <span class="material-symbols-outlined" style={{ fontSize: 30 }}>
                shopping_cart
              </span>
            </div>
          </div>
          <div className="container-header-options-bottom-right">
            <span className="btn-time-chart">1M</span>
            <span className="btn-time-chart">5M</span>
            <span className="btn-time-chart">15M</span>
          </div>
        </div>
      </div>

      <div className="container-chart" id="chart-container">
        <ChartConstructor data={data} />
      </div>
    </div>
  );
}

export default Chart;
