import { useState, useEffect } from "react";
import React from "react";

export default function Containergrid({
  containerInputGrid,
  setContainerInputGrid,
  gridData,
  addressServer,
  user
}) {
  const [assetTitle, setAssetTitle] = useState("");
  const [quota, setQuota] = useState("");
  const [nGrid, setNGrid] = useState("");
  const [percentGrid, setPercenteGrid] = useState("");
  const [valueBase, setValueBase] = useState("");
  const [isActive, setIsActive] = useState("");

  useEffect(() => {
    if (gridData) {
      setAssetTitle(gridData.name || "");
      setQuota(gridData.quota || "0.00");
      setNGrid(gridData.nGrid || "0.00");
      setPercenteGrid(gridData.percentGrid || "0.00");
      setValueBase(gridData.valueBase || "0.00");
      setIsActive(gridData.getIsActive || null);
    }
  }, [gridData]);


  const saveData = async () => {
    if (!quota || quota <= 0) {
      alert("Digite o valor da quota");
      return;
    }
    if (!nGrid || nGrid <= 0) {
      alert("Digite o número de grids");
      return;
    }
    if (!percentGrid || percentGrid <= 0) {
      alert("Digite o percentual entre os grids");
      return;
    }
    if (!valueBase || valueBase <= 0) {
      alert("Digite o valor base para início do Grid");
      return;
    }

    const configStrategy = JSON.stringify({
      isActive: JSON.stringify(isActive),
      configStrategy: JSON.stringify({
        quota: quota,
        nGrid: nGrid,
        percentGrid: percentGrid,
        valueBase: valueBase,
      }),
    });

    console.log("STRINGFY", configStrategy);

    const urlRequest = `${addressServer}/strategygriduser/insertgrid?user=${user.id}&acronym=${gridData.name}`;

    await fetch(urlRequest, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: configStrategy,
    })
      .then((res) => {
        if (!res.ok) {
          console.error("Error when requesting post grid");
        }
        console.log("Request Grid Succedly!");
        alert("OK!");
        setContainerInputGrid(false);
      })
      .catch((error) => {
        console.error(error);
      });
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

        <span className="asset-container-order">
          {gridData ? gridData.name : ""}
        </span>

        <aside className="container-type-order row-form-grid">
          <aside className="row-input-grid">
            <span className="type-order label-grid">VALOR</span>
            <input
              type="number"
              value={quota}
              onChange={(e) => {
                setQuota(e.target.value);
              }}
              className="input-grid"
            />
          </aside>

          <aside className="row-input-grid">
            <span className="type-order label-grid">GRIDS</span>
            <input
              type="number"
              value={nGrid}
              onChange={(e) => {
                setNGrid(e.target.value);
              }}
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
              onChange={(e) => {
                setValueBase(e.target.value);
              }}
              className="input-grid"
            />
          </aside>

          <aside className="row-input-grid">
            <span className="type-order label-grid">% GRIDS</span>
            <input
              type="number"
              value={percentGrid}
              onChange={(e) => {
                setPercenteGrid(e.target.value);
              }}
              className="input-grid"
            />
          </aside>
        </aside>

        <section className="container-buttons-order">
          <section className="btn-grid-order" onClick={saveData}>
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
