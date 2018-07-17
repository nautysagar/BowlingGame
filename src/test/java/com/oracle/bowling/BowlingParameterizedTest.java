package com.oracle.bowling;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.oracle.bowling.Bowling;

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BowlingParameterizedTest {
	enum Type {
		POSTIVE, NEGATIVE
	};

	@Rule
	public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	private static Bowling b = null;

	String input;
	int result;
	String message;
	Type type;

	
	public BowlingParameterizedTest(Type type, String input, int result, String message) {
		this.type = type;
		this.input = input;
		this.result = result;
		this.message = message;
	}

	@Parameters(name = "{index}: testGame({0}+{1}) = {2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ Type.POSTIVE, "10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n", 300,
						"Test Case failed with All Strikes{10}" },
				{ Type.POSTIVE, "5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n", 150,
						"Test Case failed with All Spares{5}" },
				{ Type.POSTIVE, "10\n3\n4\n5\n3\n5\n5\n5\n5\n6\n3\n8\n2\n4\n4\n3\n3\n4\n5\n", 109,
						"Test Case failed with Randam inputs" },
				{ Type.POSTIVE, "3\n4\n5\n4\n6\n3\n7\n2\n4\n4\n8\n1\n5\n5\n3\n6\n4\n5\n5\n5\n5\n", 97,
						"Test Case failed with last Spares{5}" },
				{ Type.POSTIVE, "3\n5\n4\n6\n10\n6\n2\n7\n3\n8\n1\n6\n3\n6\n3\n5\n5\n10\n5\n5\n", 139,
						"Test Case failed with last Strikes{10}" },
				{ Type.NEGATIVE, "20\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n", -1,
						"Test Case failed Invalid Inputs in Start" },
				{ Type.NEGATIVE, "10\n10\n10\n10\n10\n10\n20\n10\n10\n10\n10\n10\n10\n", -1,
						"Test Case failed Invalid Inputs in Middle" },
				{ Type.NEGATIVE, "10\n3\n4\n5\n3\n5\n5\n5\n5\n6\n3\n8\n2\n4\n4\n3\n3\n4\n25\n3\n", -1,
						"Test Case failed Invalid Inputs in End" },

		});
	}

	@Before
	public void intilaze() {
		b = new Bowling();
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	/*
	 * Positive Test case starts here All data will be intact and should be
	 * retrieved
	 */

	@Test
	public void testGame() {
		systemInMock.provideText(input);
		b.startGame();
		if (type == Type.POSTIVE) {
			assertEquals(message, result, b.getScore());
		} else {
			Assert.assertThat(errContent.toString(), CoreMatchers.containsString("Invalid Input"));
		}

	}

}
