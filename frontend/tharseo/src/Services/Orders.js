import { useContext } from "react";
import serverConfig from "./ServerConfig";
import { UserContext } from "./UserDataProvider";

const Orders = () => {
  const { userProfile, wallet, transactions, updateUserData, setIDUser } =
    useContext(UserContext);

  const openOrder = async (
    amount,
    typeOperation,
    price,
    sideOperation,
    updateUserData,
    setContainerOrder
  ) => {
    let urlRequest;
    if (amount === "") {
      alert("Digite a quantidade");
    }
    if (typeOperation === "LIMIT") {
      if (price === "") {
        alert("Digite o preço desejado");
      }
      urlRequest = `${serverConfig.addressServerTharseo}/tharseo/neworderlimit?user=${userProfile.id}&acronym=BNBUSDT&side=${sideOperation}&timeinforce=GTC&quantity=${amount}&price=${price}`;
    } else if (typeOperation === "MARKET") {
      urlRequest = `${serverConfig.addressServerTharseo}/tharseo/newordermarketmanual?user=${userProfile.id}&acronym=BNBUSDT&side=${sideOperation}&timeinforce=GTC&quantity=${amount}`;
    } else {
      alert("Escolha o lado da operação AAA");
    }

    try {
      const request = await fetch(urlRequest, { method: "POST" });
      if (!request.ok) {
        throw new Error("Error when post order");
      }
      await updateUserData();
      alert("OK!");
      setContainerOrder(false);
    } catch (error) {
      console.error(`Error Requesting Order:`, error);
    }
  };
  return (openOrder);
}

export default Orders ;
