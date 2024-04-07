import { useState, useEffect } from "react";
import React from "react";

export default function Containergrid({ data, containerInputGrid, setContainerInputGrid }) {
  const [assetTitle, setAssetTitle] = useState("BTCUSDT");
  const [quota, setQuota] = useState("12.00");
  const [nGrid, setNGrid] = useState(1);
  const [percentGrid, setPercenteGrid] = useState(0);
  const [valueBase, setValueBase] = useState(0.0);


  const saveData = () => {
    if(quota === ""){
        alert("Digite o valor da quota");
    }
    if(nGrid === ""){
        alert("Digite o números de grids");
    }
    if(percentGrid === ""){
        alert("Digite o percentual entre os grids");
    }
    if(valueBase === ""){
        alert("Digite o valor base para inicio do Grid");
    }
  };

  

  return (
    <>
      <section
        className={`container-input-grid ${
          containerInputGrid ? "active" : "hidden"
        }`}
        id="container_order"
      >
        <span className="title-container-order">Configuração do Grid</span>

        <span className="asset-container-order">BTCUSDT</span>

        <aside className="container-type-order row-form-grid">
          <aside className="row-input-grid">
            <span className="type-order label-grid">VALOR</span>
            <input
              type="number"
              value={quota}
              onChange={(e) => setQuota(e.target.value)}
              className="input-grid"
            />
          </aside>

          <aside className="row-input-grid">
            <span className="type-order label-grid">GRIDS</span>
            <input
              type="number"
              value={nGrid}
              onChange={(e) => setNGrid(e.target.value)}
              className="input-grid"
            />
          </aside>
        </aside>

        <aside className="container-type-order row-form-grid">
          <aside className="row-input-grid">
            <span className="type-order label-grid">VALOR BASE</span>
            <input
              type="number"
              value={valueBase}
              onChange={(e) => setValueBase(e.target.value)}
              className="input-grid"
            />
          </aside>

          <aside className="row-input-grid">
            <span className="type-order label-grid">% GRIDS</span>
            <input
              type="number"
              value={percentGrid}
              onChange={(e) => setPercenteGrid(e.target.value)}
              className="input-grid"
            />
          </aside>
        </aside>

        <section className="container-buttons-order">
          <section className="btn-grid-order" onClick={() => saveData()}>
            <span class="material-symbols-outlined" style={{ fontSize: 30 }}>
              query_stats
            </span>
            <span className="text-btn-cancel-order">SALVAR</span>
          </section>

          <aside
            className="btn-order-cancel"
            onClick={() => setContainerInputGrid(false)}
          >
            <span class="material-symbols-outlined" style={{ fontSize: 30 }}>
              close
            </span>
            <span className="text-btn-cancel-order">CANCELAR</span>
          </aside>
        </section>
      </section>
      <section className="effect-disable"></section>
    </>
  );
}
