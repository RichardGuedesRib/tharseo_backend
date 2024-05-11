import serverConfig from "./ServerConfig";

export async function getAllAssetsServer() {
  const request = `${serverConfig.addressServerTharseo}/assets`;
  try {
    const response = await fetch(request);
    if (!response.ok) {
      console.error("Error request list assets server");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
  }
}

export async function getAllAssetsByUser(iduser) {
  const request = `${serverConfig.addressServerTharseo}/assetsuser/user?iduser=${iduser}`;
  try {
    const response = await fetch(request);
    if (!response.ok) {
      console.error("Error request list assets server");
    }
    const data = await response.json();
   
    return data;
  } catch (error) {
    console.error(error);
  }
}

export async function toggleAssetUser(wallet, user, item) {
  try {
    let method;
    let operation;
    

    const isAssetInWallet =
      user &&
      Array.isArray(wallet) &&
      wallet.some((walletItem) => walletItem.acronym === item.acronym);

    if (!isAssetInWallet) {
      method = `${serverConfig.addressServerTharseo}/assetsuser?iduser=${user}&symbol=${item.acronym}`;
      operation = "POST";
    } else {
      const idItemWallet = wallet.find((itemWallet) => itemWallet.acronym === item.acronym);
      method = `${serverConfig.addressServerTharseo}/assetsuser/${idItemWallet.id}`;
      operation = "DELETE";
    }

    const response = await fetch(method, {
      method: operation,
      headers: { "Content-Type": "application/json" },
    });

    if (!response.ok) {
      console.error(
        `Error when ${operation === "POST" ? "adding" : "removing"} asset`
      );
      return;
    }
    
  } catch (error) {
    console.error("Error requesting asset operation", error);
  }
}
