package ru.job4j.hibernate.hsqlbd;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {

    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("postgres");
        pool.setPassword("7508");
        pool.setMaxTotal(2);
        byte[] file = null;
        try (InputStream inputStream = OrdersStoreTest.class.getClassLoader().getResourceAsStream("update_001.sql")) {
            file = inputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(new String(file, StandardCharsets.UTF_8)).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndGetTheOrderById() {
        OrdersStore ordersStore = new OrdersStore(pool);
        Order order = ordersStore.save(Order.of("name", "description"));
        Order result = ordersStore.findById(order.getId());
        assertThat(order, is(result));
    }

    @Test
    public void whenSaveOrderAndGetTheOrderByName() {
        OrdersStore ordersStore = new OrdersStore(pool);
        Order order = ordersStore.save(Order.of("name", "description"));
        Order result = ordersStore.findByName("name");
        assertThat(order, is(result));
    }

    @Test
    public void whenSaveOrderAndUpdateTheOrderThenGetTheOrderById() {
        OrdersStore ordersStore = new OrdersStore(pool);
        Order order = ordersStore.save(Order.of("name", "description"));
        boolean result = ordersStore.update(order.getId(), Order.of("Kirill", "desc"));
        Order resultOrder = ordersStore.findById(order.getId());
        assertTrue(result);
        assertThat(resultOrder.getName(), is("Kirill"));
    }
}