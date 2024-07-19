import React, { useState, useEffect } from "react";
import { Container, Row, Col, Button } from "react-bootstrap";
import axios from "axios";
import { GoogleLogin } from "@react-oauth/google";
import { jwtDecode } from "jwt-decode";

const Home = () => {
	const [products, setProducts] = useState([]);
	const [client, setClient] = useState(
		JSON.parse(localStorage.getItem("client"))
	);
	const [user, setUser] = useState(JSON.parse(localStorage.getItem("user")));

	useEffect(() => {
		axios
			.get("http://localhost:8080/api/products")
			.then((response) => setProducts(response.data));
	}, []);

	useEffect(() => {
		if (user) {
			console.log("user is here: ", user);
			axios
				.post("http://localhost:8080/api/clients", {
					name: user.name,
					email: user.email,
				})
				.then((res) => {
					setClient(res.data);
					localStorage.setItem("client", JSON.stringify(res.data));
				})
				.catch((err) => {
					console.error("Error creating or retrieving client:", err);
				});
		}
	}, [user]);

	const handleLoginSuccess = (credentialResponse) => {
		const token = credentialResponse.credential;
		const userInfo = jwtDecode(token);

		setUser(userInfo);
		localStorage.setItem("user", JSON.stringify(userInfo));
	};

	const handleAddToOrder = async (productId) => {
		if (!user) {
			document.getElementById("googleLoginButton").click();
		} else {
			console.log(user);
			console.log(client.id);
			try {
				const response = await axios.get(
					`http://localhost:8080/api/orders/client/${client.id}`
				);
				const orders = response.data;

				if (orders.length > 0) {
					const order = orders[0];
					const existingItem = order.orderItems.find(
						(item) => item.product.id === productId
					);

					if (existingItem) {
						await axios.put(
							`http://localhost:8080/api/orders/${order.id}/items/${existingItem.id}`,
							{
								product: { id: productId },
								quantity: existingItem.quantity + 1,
							}
						);
					} else {
						await axios.put(
							`http://localhost:8080/api/orders/${order.id}/items`,
							{
								product: { id: productId },
								quantity: 1,
							}
						);
					}
				} else {
					await axios.post(`http://localhost:8080/api/orders/${client.id}`, {
						orderItems: [{ product: { id: productId }, quantity: 1 }],
					});

					const newOrderResponse = await axios.get(
						`http://localhost:8080/api/orders/client/${client.id}`
					);
					const newOrder = newOrderResponse.data[0];

					await axios.put(
						`http://localhost:8080/api/orders/${newOrder.id}/items`,
						{
							product: { id: productId },
							quantity: 1,
						}
					);
				}
				alert("Product added to order");
			} catch (err) {
				console.error("Error adding to order:", err);
			}
		}
	};

	return (
		<Container>
			<h1 className="mt-4">Products</h1>
			{user ? (
				<p>
					Welcome, {user.name} ({user.email})
				</p>
			) : (
				<div id="googleLoginButton">
					<GoogleLogin
						onSuccess={handleLoginSuccess}
						onError={() => console.log("Login Failed")}
					/>
				</div>
			)}
			<Row>
				{products.map((product) => (
					<Col key={product.id} md={4}>
						<div className="card mb-4">
							<div className="card-body">
								<h5 className="card-title">{product.name}</h5>
								<p className="card-text">R{product.price}</p>
								<Button onClick={() => handleAddToOrder(product.id)}>
									Add to Order
								</Button>
							</div>
						</div>
					</Col>
				))}
			</Row>
		</Container>
	);
};

export default Home;
