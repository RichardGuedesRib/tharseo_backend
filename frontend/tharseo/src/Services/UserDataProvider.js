import React, { createContext, useContext, useEffect, useState } from "react";
import { getUserData } from "./UserService";
import {} from "./AssetsService";

export const UserContext = React.createContext({});

export const UserDataProvider = (props) => {
  const [userProfile, setUserProfile] = useState(null);
  const [wallet, setWallet] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [userID, setUserID] = useState();

  useEffect(() => {
    return () => {};
  }, [userProfile, wallet, transactions]);

  const updateUserData = async () => {
    if (!userID) {
      return;
    }

    try {
      const profileData = await getUserData(userID);

      setUserProfile(profileData);
      setWallet(profileData.wallet);
      setTransactions(profileData.transactions);

      console.log("USERPROFILE", userProfile);
    } catch (error) {
      console.error("Error request data user", error);
    }
  };

  const setIDUser = (id) => {
    console.log(id);
    setUserID(id);
    updateUserData();
  };

  const setUser = (user) => {
    setUserProfile(user);
    setWallet(user.wallet);
    setTransactions(user.transactions);
    setIDUser(user.id);
    console.log("SETADO PELO LOGIN", user);
  };

  return (
    <UserContext.Provider
      value={{
        userProfile,
        wallet,
        transactions,
        updateUserData,
        setUser,
      }}
    >
      {props.children}
    </UserContext.Provider>
  );
};
