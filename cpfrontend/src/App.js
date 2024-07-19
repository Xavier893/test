import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "./components/Home";
import Orders from "./components/Orders";
import { GoogleOAuthProvider } from "@react-oauth/google";
import Navbar from "./components/Navbar";

const clientId =
	"482779531372-gdvfq8htbu212vpm45df4dd7928q0ls2.apps.googleusercontent.com"; // Replace with your Google client ID

const App = () => {
	return (
		<GoogleOAuthProvider clientId={clientId}>
			<Router>
				<Navbar />
				<Routes>
					<Route path="/" element={<Home />} />
					<Route path="/orders" element={<Orders />} />
				</Routes>
			</Router>
		</GoogleOAuthProvider>
	);
};

export default App;
