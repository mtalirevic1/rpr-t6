package ba.unsa.etf.rpr.tutorijal6;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class MainTest {

    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("sample.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Test
    public void nameFieldTest(FxRobot robot) {
        TextField nameField = robot.lookup("#ime").queryAs(TextField.class);
        robot.clickOn("#ime").write("Matej");
        assertEquals("Matej", nameField.getText());
    }

    @Test
    public void surnameFieldTest(FxRobot robot) {
        TextField surnameField = robot.lookup("#prezime").queryAs(TextField.class);
        robot.clickOn("#prezime").write("Talirević");
        assertEquals("Talirević", surnameField.getText());
    }

    @Test
    public void indexFieldTest(FxRobot robot) {
        TextField indexField = robot.lookup("#indeks").queryAs(TextField.class);
        robot.clickOn("#indeks").write("17749");
        assertEquals("17749", indexField.getText());
    }

    @Test
    public void genderBoxTest(FxRobot robot) {
        ChoiceBox genderBox = robot.lookup("#spol").queryAs(ChoiceBox.class);
        robot.clickOn("#spol").clickOn("Muški");
        assertEquals("Muški",genderBox.getValue().toString());
    }

    @Test
    public void emailTest(FxRobot robot) {
        TextField emailBox = robot.lookup("#email").queryAs(TextField.class);
        robot.clickOn("#email").write("mtalirevic1@etf.unsa.ba");
        assertEquals("mtalirevic1@etf.unsa.ba", emailBox.getText());
    }

    @Test
    public void telephoneTest(FxRobot robot) {
        TextField telephoneField = robot.lookup("#telefon").queryAs(TextField.class);
        robot.clickOn("#telefon").write("061-048882");
        assertEquals("061-048882", telephoneField.getText());
    }

    @Test
    public void adressTest(FxRobot robot) {
        TextField adressField = robot.lookup("#adresa").queryAs(TextField.class);
        robot.clickOn("#adresa").write("Gradačačka 74");
        assertEquals("Gradačačka 74", adressField.getText());
    }

    @Test
    public void podaciOStudijutest(FxRobot robot) {
        ChoiceBox odsjek = robot.lookup("#odsjek").queryAs(ChoiceBox.class);
        robot.clickOn("#odsjek").clickOn("RI");
        ChoiceBox godina = robot.lookup("#godStudija").queryAs(ChoiceBox.class);
        robot.clickOn("#godStudija").clickOn("Druga");
        ChoiceBox ciklus = robot.lookup("#ciklusStudija").queryAs(ChoiceBox.class);
        robot.clickOn("#ciklusStudija").clickOn("Bachelor");
        ChoiceBox status = robot.lookup("#redovan").queryAs(ChoiceBox.class);
        robot.clickOn("#redovan").clickOn("Samofinansirajući");
        ChoiceBox kategorija = robot.lookup("#boracka").queryAs(ChoiceBox.class);
        robot.clickOn("#boracka").clickOn("NE");
        assertAll("podaci o studiju",()->{
            assertEquals("RI", odsjek.getValue().toString());
            assertEquals("Druga", godina.getValue().toString());
            assertEquals("Bachelor", ciklus.getValue().toString());
            assertEquals("Samofinansirajući", status.getValue().toString());
            assertEquals("NE", kategorija.getValue().toString());
        });
    }
}