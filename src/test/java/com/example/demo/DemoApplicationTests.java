package com.example.demo;

import com.example.demo.models.Cart;
import com.example.demo.models.Item;
import com.example.demo.models.Recepit;
import com.example.demo.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DemoApplicationTests {
	User user;
	Item item1;
	Item item2;
	Item item3;
	Cart cart;

	@BeforeEach
    void contextLoads() {
		user = new User("username", "password", "email", "firstName", "lastName");
		item1 = new Item("name", "description", 1.0);
		item2 = new Item("name2", "description2", 2.0);
		item3 = new Item("name3", "description3", 3.0);
		cart = Cart.builder().id(1).total(0.0).build();
	}

	@Test
	void addItem() {
		assert(cart.getItems().isEmpty());

		cart.addItem(item1);
		assertEquals(1, cart.getItems().size());
		assertEquals(1, cart.getTotal());
		assertEquals(1, cart.getQty(item1));

		cart.addItem(item1);
		assertEquals(1, cart.getItems().size());
		assertEquals(2, cart.getTotal());
		assertEquals(2, cart.getQty(item1));
	}

	@Test
	void removeItem() {
		assert(cart.getItems().isEmpty());

		cart.addItem(item1);
		cart.addItem(item2);
		cart.addItem(item3);
		assertEquals(3, cart.getItems().size());
		assertEquals(6.0, cart.getTotal());

		cart.removeItem(item1);
		assertEquals(2, cart.getItems().size());
		assertEquals(5.0, cart.getTotal());

		cart.removeItem(item1);
		assertEquals(2, cart.getItems().size());
		assertEquals(5.0, cart.getTotal());
	}

	@Test
	void dropCart() {
		assertEquals(0, cart.getItems().size());
		cart.dropCart();
		assertEquals(0, cart.getItems().size());

		cart.addItem(item1);
		cart.addItem(item2);
		cart.addItem(item3);
		assertEquals(3, cart.getItems().size());
		assertEquals(6.0, cart.getTotal());

		cart.dropCart();
		assertEquals(0, cart.getItems().size());
		assertEquals(0.0, cart.getTotal());
	}

	@Test
	void checkout() {
		assert(cart.getItems().isEmpty());

		cart.addItem(item1);
		cart.addItem(item2);
		cart.addItem(item3);
		assertEquals(3, cart.getItems().size());
		assertEquals(6.0, cart.getTotal());

		Hashtable<Item, Integer> cart2 = new Hashtable<>(cart.getItems());

		Recepit recepit = cart.checkout(user);
		assertEquals(cart2, recepit.getCart());
		assertEquals(6.0, recepit.getTotal());
		assertEquals(user, recepit.getUser());
		assertEquals("2025-02-10", recepit.getDate());
		assertTrue(cart.getItems().isEmpty());
		assertEquals(0.0, cart.getTotal());
	}

	@Test
	void changeQty() {
        assertTrue(cart.getItems().isEmpty());

		cart.changeQty(item1, 1);
        assertTrue(cart.getItems().isEmpty());

		cart.addItem(item1);
		cart.addItem(item2);

		cart.changeQty(item1, 3);
		assertEquals(2, cart.getItems().size());
		assertEquals(3, cart.getQty(item1));

		cart.changeQty(item1, -1);
		assertEquals(2, cart.getItems().size());
		assertEquals(3, cart.getQty(item1));
	}

}
