package jqa.elevator;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.lessThan;


public class StimulusTest
{
	@Test
	public void compareTo()
			throws Exception
	{
		Stimulus one = new Stimulus();
		Stimulus two = new Stimulus();

		one.timestamp = 0;
		one.type = Stimulus.StimulusType.ButtonDown;
		two.timestamp = 100;
		two.type = Stimulus.StimulusType.ButtonDown;

		assertThat(one.compareTo(two), lessThan(0));
		assertThat(two.compareTo(one), greaterThan(0));

		one.timestamp = 100;
		two.timestamp = 100;
		assertThat(one.compareTo(two), is(0));

		one.type = Stimulus.StimulusType.ButtonDown;
		two.type = Stimulus.StimulusType.ButtonUp;
		assertThat(one.compareTo(two), lessThan(0));
	}

	@Test
	public void equals()
			throws Exception
	{
		Stimulus one = new Stimulus();
		Stimulus two = new Stimulus();

		one.timestamp = 0;
		one.type = Stimulus.StimulusType.ButtonDown;
		two.timestamp = 100;
		two.type = Stimulus.StimulusType.ButtonDown;

		assertThat(one.equals(two), is(false));
		assertThat(two.equals(one), is(false));

		one.timestamp = 100;
		two.timestamp = 100;
		assertThat(one.equals(two), is(true));

		one.type = Stimulus.StimulusType.ButtonDown;
		two.type = Stimulus.StimulusType.ButtonUp;
		assertThat(one.equals(two), is(false));

		assertThat(one.equals(null), is(false));
		assertThat(one.equals(new Object()), is(false));
	}

}
