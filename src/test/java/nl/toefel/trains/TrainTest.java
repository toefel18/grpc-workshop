package nl.toefel.trains;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TrainTest {

    @Test
    public void moveToNewLocation() {
        Railway rail = Railway.utrechtAmsterdam();
        Train train = new Train("1234", rail);

        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(1)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(2)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(3)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(4)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(5)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(6)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(7)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(8)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(9)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(10)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(11)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(12)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(13)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(14)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(15)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(16)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(17)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(18)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(19)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(1)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(2)));
        assertThat(train.moveToNewLocation(), equalTo(rail.sections.get(3)));
    }
}