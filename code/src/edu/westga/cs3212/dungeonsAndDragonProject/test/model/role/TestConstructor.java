package edu.westga.cs3212.dungeonsAndDragonProject.test.model.role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;

class TestConstructor {

	@Test
	void testValidConstruction() {
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));
		Role role = new Role("A Test role", "A Test role", testFeatures, testProficiencies);

		assertEquals("A Test role", role.getName());
		assertEquals(testFeatures, role.getFeatures());
		assertEquals(testProficiencies, role.getProficiencies());
		assertEquals("A Test role", role.getFlavorText());

	}

	@Test
	void testNullName() {
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));

		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> new Role(null, "A Test role", testFeatures, testProficiencies));
		assertEquals("name cannot be null", ex.getMessage());

	}

	@Test
	void testEmptyName() {
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));

		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> new Role("", "A Test role", testFeatures, testProficiencies));
		assertEquals("name cannot be empty", ex.getMessage());

	}

	@Test
	void testNullFlavorText() {
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));

		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> new Role("A Test role", null, testFeatures, testProficiencies));
		assertEquals("flavor text cannot be null", ex.getMessage());

	}

	@Test
	void testEmptyFlavorText() {
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));
		Set<String> testFeatures = new HashSet<String>();

		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> new Role("A Test role", "", testFeatures, testProficiencies));
		assertEquals("flavor text cannot be empty", ex.getMessage());

	}

	@Test
	void testNullFeatures() {
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));

		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> new Role("A Test role", "A Test role", null, testProficiencies));
		assertEquals("features cannot be null", ex.getMessage());
	}

	@Test
	void testEmptyFeatures() {
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));
		Set<String> testFeatures = new HashSet<String>();
		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> new Role("A Test role", "A Test role", testFeatures, testProficiencies));
		assertEquals("features cannot be empty", ex.getMessage());
	}

	@Test
	void testNullProficiencies() {
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));

		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> new Role("A Test role", "A Test role", testFeatures, null));
		assertEquals("proficiencies cannot be null", ex.getMessage());
	}

	@Test
	void testEmptyProficiencies() {
		Set<String> testProficiencies = new HashSet<String>();
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));

		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> new Role("A Test role", "A Test role", testFeatures, testProficiencies));
		assertEquals("proficiencies cannot be empty", ex.getMessage());
	}
	
	@Test
	void testSetFeaturesValid() {
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));
		Role role = new Role("A Test role", "A Test role", testFeatures, testProficiencies);
		
		Set<String> newTestFeatures = new HashSet<String>(Arrays.asList("test feature"));
		
		role.setFeatures(newTestFeatures);
	}
	
	@Test
	void testSetFeatureBlank() {
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));
		Role role = new Role("A Test role", "A Test role", testFeatures, testProficiencies);
		
		Set<String> newTestFeatures = new HashSet<String>();
		
		assertThrows(IllegalArgumentException.class, ()-> {
			role.setFeatures(newTestFeatures);
		});
	}
	
	@Test
	void testSetFeatureNull() {
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));
		Role role = new Role("A Test role", "A Test role", testFeatures, testProficiencies);
		
		Set<String> newTestFeatures = null;
		
		assertThrows(IllegalArgumentException.class, ()-> {
			role.setFeatures(newTestFeatures);
		});
	}
	
	@Test
	void testToString() {
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));
		Role role = new Role("A Test role", "A Test role", testFeatures, testProficiencies);
		
		assertEquals("A Test role", role.toString());
	}
	
	@Test
	void testRoleDetails() {
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));
		Role role = new Role("A Test role", "A Test role", testFeatures, testProficiencies);
		
		String details = "A Test role" + "\n" + "Features: test feature " + "\n" 
				+ "Proficencies: test proficiency ";
		
		assertEquals(details, role.roleDetails());
	}
}