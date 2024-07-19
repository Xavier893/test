import React, { useState, useEffect } from "react";
import { Container, Button, Table } from "react-bootstrap";
import axios from "axios";

const Orders = () => {
	const [orders, setOrders] = useState([]);
	const client = JSON.parse(localStorage.getItem("client"));

	useEffect(() => {
		if (client) {
			axios
				.get(`https://cpazureback.azurewebsites.net/api/orders/client/${client.id}`)
				.then((response) => setOrders(response.data));
		}
	}, [client]);

	const handleDeleteOrder = (orderId) => {
		axios.delete(`https://cpazureback.azurewebsites.net/api/orders/${orderId}`).then(() => {
			setOrders(orders.filter((order) => order.id !== orderId));
		});
	};

	const handleRemoveOrderItem = (orderId, itemId) => {
		const order = orders.find((order) => order.id === orderId);
		const updatedOrderItems = order.orderItems.filter(
			(item) => item.id !== itemId
		);

		axios
			.put(`https://cpazureback.azurewebsites.net/api/orders/${orderId}`, {
				...order,
				orderItems: updatedOrderItems,
			})
			.then(() => {
				setOrders(
					orders.map((order) =>
						order.id === orderId
							? {
									...order,
									orderItems: updatedOrderItems,
							  }
							: order
					)
				);
			});
	};

	return (
		<Container>
			<h1 className="mt-4">My Orders</h1>
			{orders.map((order) => (
				<div key={order.id} className="mb-4">
					<h2>Order #{order.id}</h2>
					<Table striped bordered hover>
						<thead>
							<tr>
								<th>Product</th>
								<th>Quantity</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							{order.orderItems.map((item) => (
								<tr key={item.id}>
									<td>{item.product.name}</td>
									<td>
										{item.quantity}
									</td>
									<td>
										<Button
											variant="danger"
											onClick={() => handleRemoveOrderItem(order.id, item.id)}
										>
											Remove
										</Button>
									</td>
								</tr>
							))}
						</tbody>
					</Table>
					<Button variant="danger" onClick={() => handleDeleteOrder(order.id)}>
						Delete Order
					</Button>
				</div>
			))}
		</Container>
	);
};

export default Orders;
