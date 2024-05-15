import React, { useEffect, useState, useContext } from "react";
import Icon from "react-crypto-icons";
import {
  toggleAssetUser,
  getAllAssetsByUser,
} from "../../Services/AssetsService";
import { UserContext } from "../../Services/UserDataProvider";

function SelectAsset({
  listAssets,
  selectAssets,
  setSelectAssets,
 
}) {
  const { userProfile, wallet, transactions, updateUserData, setUser } = useContext(UserContext);
  const [assets, setAssets] = useState([]);
  const [limit, setLimit] = useState(10);
  const [newWallet, setNewWallet] = useState(wallet);
  
  const updateAssetsUser = async () => {
    const assetsFilter = Array.isArray(listAssets)
      ? listAssets.slice(0, limit)
      : [];
    setAssets(assetsFilter);

    const assetsUser = await getAllAssetsByUser(userProfile.id);
    setNewWallet(Array.isArray(assetsFilter) ? assetsUser : []);
  };

  const handleToggleAsset = async (item) => {
    await toggleAssetUser(newWallet, userProfile.id, item);
    updateAssetsUser();
  };

  useEffect(() => {
    updateAssetsUser();
  }, [limit, listAssets]);

  return (
    <>
      <section
        className={`background-disable ${selectAssets ? "show" : "close"}`}
      ></section>

      <main
        className={`container-select-assets ${selectAssets ? "show" : "close"}`}
      >
        <header className="select-assets-header">
          <span className="select-assets-title">Selecione os Ativos</span>
          <aside className="select-assets-find">
            <input
              type="text"
              name=""
              id=""
              className="select-assets-find-input no-border"
              placeholder="não implementado"
            />
            <span className="select-assets-find-icon">
              <span class="material-symbols-outlined">search</span>
            </span>
          </aside>
        </header>

        <section className="select-assets-body">
          <table className="select-assets-body-table">
            <tbody>
              {assets.map((item) => {
                const isAssetInWallet =
                  Array.isArray(newWallet) &&
                  newWallet.some(
                    (walletItem) => walletItem.acronym === item.acronym
                  );
                return (
                  <tr className="select-assets-table-row">
                    <td className="select-assets-table-cell">
                      <span className="select-assets-table-container-icon">
                        {" "}
                        <Icon
                          name={item.acronym.replace("USDT", "").toLowerCase()}
                          size={23}
                        />
                      </span>
                    </td>
                    <td className="select-assets-table-row-cell assets-row-asset">
                      {item.acronym}
                    </td>
                    <td className="select-assets-table-row-cell">
                      <span
                        className="select-assets-table-row-control"
                        onClick={() => handleToggleAsset(item)}
                      >
                        <span
                          class="material-symbols-outlined"
                          style={{
                            color: isAssetInWallet ? "#E55764" : "#56BC7C",
                          }}
                        >
                          {isAssetInWallet ? "remove_circle" : "add_circle"}
                        </span>
                      </span>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </section>
        <span
          className="select-assets-footer-showmore"
          onClick={() => setLimit((prevLimit) => prevLimit + 5)}
        >
          Ver Mais
        </span>
        <footer className="select-assets-footer">
          <span
            className="select-assets-footer-btn"
            onClick={() => {
              setSelectAssets(false);
              updateUserData();
            }
              
            }
          >
            Fechar
          </span>
        </footer>
      </main>
    </>
  );
}

export default SelectAsset;
