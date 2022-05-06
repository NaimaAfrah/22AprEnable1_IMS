package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.DBUtils;

public class OrderItemDAO implements Dao<OrderItem> {

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public OrderItem modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		Long order_id = resultSet.getLong("order_id");
		Long item_id = resultSet.getLong("item_id");
		Long quantity = resultSet.getLong("quantity");
		return new OrderItem(id, order_id, item_id, quantity);
	}

	// Reads all orderItems from the database

	@Override
	public List<OrderItem> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement
						.executeQuery("SELECT * FROM orders_items;");) {
			List<OrderItem> orderItems = new ArrayList<>();
			while (resultSet.next()) {
				orderItems.add(modelFromResultSet(resultSet));
			}
			return orderItems;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public OrderItem readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders_items ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// Creates a orderItem in the database

	@Override
	public OrderItem create(OrderItem orderItem) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT INTO orders_items(order_id, item_id, quantity) VALUES(?, ?, ?)");) {
			statement.setLong(1, orderItem.getOrder_id());
			statement.setLong(2, orderItem.getItem_id());
			statement.setLong(3, orderItem.getQuantity());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public OrderItem read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders_items WHERE id = ?");) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// Updates an orderItem in the database

	@Override
	public OrderItem update(OrderItem orderItem) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE orders_items SET order_id= ?, item_id =? , quantity = ? WHERE id = ?");) {
			statement.setLong(1, orderItem.getOrder_id());
			statement.setLong(2, orderItem.getItem_id());
			statement.setLong(3, orderItem.getQuantity());
			statement.setLong(4, orderItem.getId());
			statement.executeUpdate();
			return read(orderItem.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// Deletes a orderItem in the database

	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM orders_items WHERE id =?;");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return 0;
	}

}
