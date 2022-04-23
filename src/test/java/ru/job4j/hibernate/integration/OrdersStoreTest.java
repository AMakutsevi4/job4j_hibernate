package ru.job4j.hibernate.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
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
    public void whenFindByNameOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order order = new Order(1, "name1", "description1",
                new Timestamp(System.currentTimeMillis()));
        store.save(order);
        Order rsl = store.findByName(order.getName());
        assertThat(rsl.getDescription(), is(order.getDescription()));
    }

    @Test
    public void whenFindByIdOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order order = new Order(1, "name1", "description1",
                new Timestamp(System.currentTimeMillis()));
        store.save(order);
        assertThat(store.findById(1), is(order));
    }

    @Test
    public void whenUpdateOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order order = new Order(1, "name1", "description1",
                new Timestamp(System.currentTimeMillis()));
        store.save(order);
        Order order1 = new Order(1, "name2", "description2",
                new Timestamp(System.currentTimeMillis()));
        store.update(order1);
        Order rsl = store.findById(order.getId());
        assertThat(rsl.getId(), is(order1.getId()));
        assertThat(rsl.getName(), is(order1.getName()));
        assertThat(rsl.getDescription(), is(order1.getDescription()));
        assertThat(rsl.getCreated(), is(order1.getCreated()));
    }
}