package ba.unsa.etf.rpr.tutorijal6;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    public TextField ime;
    public TextField prezime;
    public TextField indeks;
    public TextField jmbg;
    public ChoiceBox spol;
    public DatePicker datumRodjenja;
    public ComboBox mjestoRodjenja;
    public TextField adresa;
    public TextField telefon;
    public TextField email;
    public ChoiceBox odsjek;
    public ChoiceBox godStudija;
    public ChoiceBox ciklusStudija;
    public ChoiceBox redovan;
    public ChoiceBox boracka;
    public Button confirmButton;

    public boolean imeTacno;
    public boolean prezimeTacno;
    public boolean indeksTacan;
    public boolean jmbgTacan;
    public boolean datumTacan;
    public boolean telefonTacan;
    public boolean emailTacan;
    public boolean mjestoTacno;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean isImeValid(String s){
        if (s.length() > 20 || s.length() < 1) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(Character.isLetter(c) || c == ' ')) return false;
        }
        return true;
    }

    public boolean isPrezimeValid(String s) {
        if (s.length() > 20 || s.length() < 1) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(Character.isLetter(c) || c == '-')) return false;
        }
        return true;
    }

    public boolean isIndeksValid(String s) {
        if (s.length() != 5) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public boolean isJmbgValid(String jmbg) {
        if (jmbg.length() != 13) return false;
        else if (datumRodjenja.getValue() == null) return false;
        else if (spol.getValue() == null) return false;
        for (int i = 0; i < jmbg.length(); i++) {
            char c = jmbg.charAt(i);
            if (!Character.isDigit(c)) return false;
        }
        int dan = (jmbg.charAt(0) - '0') * 10 + (jmbg.charAt(1) - '0');
        int mjesec = (jmbg.charAt(2) - '0') * 10 + (jmbg.charAt(3) - '0');
        int godina = 1000 + (jmbg.charAt(4) - '0') * 100 + (jmbg.charAt(5) - '0') * 10 + (jmbg.charAt(6) - '0');
        LocalDate date = datumRodjenja.getValue();
        if (dan != date.getDayOfMonth() || mjesec != date.getMonthValue() || godina != date.getYear()) return false;
        int politickaRegija = (jmbg.charAt(7) - '0') * 10 + (jmbg.charAt(8) - '0');
        if (politickaRegija < 0 || politickaRegija > 96) return false;
        int jedinstveniBroj = (jmbg.charAt(9) - '0') * 100 + (jmbg.charAt(10) - '0') * 10 + (jmbg.charAt(11) - '0');
        String s = (String) spol.getValue();
        if (s == "Muški" && (jedinstveniBroj < 0 || jedinstveniBroj > 499)) return false;
        else if (s == "Ženski" && (jedinstveniBroj > 999 || jedinstveniBroj < 500)) return false;
        int kontrolnaCifra = 11 - ((7 * ((jmbg.charAt(0) - '0') + (jmbg.charAt(6) - '0')) + 6 * ((jmbg.charAt(1) - '0') + (jmbg.charAt(7) - '0')) + 5 * ((jmbg.charAt(2) - '0') + (jmbg.charAt(8) - '0')) + 4 * ((jmbg.charAt(3) - '0') + (jmbg.charAt(9) - '0')) + 3 * ((jmbg.charAt(4) - '0') + (jmbg.charAt(10) - '0')) + 2 * ((jmbg.charAt(5) - '0') + (jmbg.charAt(11) - '0'))) % 11);
        if (kontrolnaCifra > 9) kontrolnaCifra = 0;
        if (kontrolnaCifra != (jmbg.charAt(12) - '0')) return false;
        return true;
    }

    public boolean isDatumValid(LocalDate date) {
        if (datumRodjenja.getValue() == null) return false;
        LocalDate date1 = LocalDate.now();
        if (date.getYear() > date1.getYear()) return false;
        else if (date.getMonthValue() > date1.getMonthValue() && date.getYear() == date1.getYear()) return false;
        else if (date.getDayOfMonth() > date1.getDayOfMonth() && date.getMonthValue() == date1.getMonthValue() && date.getYear() == date1.getYear())
            return false;
        return true;
    }

    public boolean isMjestoValid(String s) {
        if (s.isEmpty() || s.length() > 20) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(Character.isLetter(c) || c==' ')) return false;
        }
        return true;
    }

    public static boolean isEmailValid(String emailStr){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
    public static boolean isTelephoneValid(String nmbr){
        if (nmbr.isEmpty()) return true;
        Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
        Pattern pattern1 = Pattern.compile("\\d{3}-\\d{6}");
        Matcher matcher = pattern.matcher(nmbr);
        Matcher matcher1 = pattern1.matcher(nmbr);
        return (matcher.matches()|| matcher1.matches());
    }

    @FXML
    public void initialize(){
        imeTacno=false;
        ime.getStyleClass().add("poljeNeispravno");
        ime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isImeValid(newValue)) {
                ime.getStyleClass().removeAll("poljeNeispravno");
                ime.getStyleClass().add("poljeIspravno");
                imeTacno = true;
            } else {
                ime.getStyleClass().removeAll("poljeIspravno");
                ime.getStyleClass().add("poljeNeispravno");
                imeTacno = false;
            }
        });

        prezimeTacno=false;
        prezime.getStyleClass().add("poljeNeispravno");
        prezime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isPrezimeValid(newValue)) {
                prezime.getStyleClass().removeAll("poljeNeispravno");
                prezime.getStyleClass().add("poljeIspravno");
                prezimeTacno = true;
            } else {
                prezime.getStyleClass().removeAll("poljeIspravno");
                prezime.getStyleClass().add("poljeNeispravno");
                prezimeTacno = false;
            }
        });

        indeksTacan=false;
        indeks.getStyleClass().add("poljeNeispravno");
        indeks.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isIndeksValid(newValue)) {
                indeks.getStyleClass().removeAll("poljeNeispravno");
                indeks.getStyleClass().add("poljeIspravno");
                indeksTacan = true;
            } else {
                indeks.getStyleClass().removeAll("poljeIspravno");
                indeks.getStyleClass().add("poljeNeispravno");
                indeksTacan = false;
            }
        });

        jmbgTacan=false;
        jmbg.getStyleClass().add("poljeNeispravno");
        jmbg.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isJmbgValid(newValue)) {
                jmbg.getStyleClass().removeAll("poljeNeispravno");
                jmbg.getStyleClass().add("poljeIspravno");
                jmbgTacan = true;
            } else {
                jmbg.getStyleClass().removeAll("poljeIspravno");
                jmbg.getStyleClass().add("poljeNeispravno");
                jmbgTacan = false;
            }
        });

        datumTacan=false;
        datumRodjenja.getStyleClass().add("poljeNeispravno");
        datumRodjenja.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isDatumValid(newValue)) {
                datumRodjenja.getStyleClass().removeAll("poljeNeispravno");
                datumRodjenja.getStyleClass().add("poljeIspravno");
                datumTacan = true;
            } else {
                datumRodjenja.getStyleClass().removeAll("poljeIspravno");
                datumRodjenja.getStyleClass().add("poljeNeispravno");
                datumTacan = false;
            }
        });

        telefonTacan=false;
        telefon.getStyleClass().add("poljeNeispravno");
        telefon.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isTelephoneValid(newValue)) {
                telefon.getStyleClass().removeAll("poljeNeispravno");
                telefon.getStyleClass().add("poljeIspravno");
                telefonTacan = true;
            } else {
                telefon.getStyleClass().removeAll("poljeIspravno");
                telefon.getStyleClass().add("poljeNeispravno");
                telefonTacan = false;
            }
        });

        emailTacan=false;
        email.getStyleClass().add("poljeNeispravno");
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isEmailValid(newValue)) {
                email.getStyleClass().removeAll("poljeNeispravno");
                email.getStyleClass().add("poljeIspravno");
                emailTacan = true;
            } else {
                email.getStyleClass().removeAll("poljeIspravno");
                email.getStyleClass().add("poljeNeispravno");
                emailTacan = false;
            }
        });

        mjestoTacno=false;
        mjestoRodjenja.getStyleClass().add("poljeNeispravno");
        mjestoRodjenja.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isMjestoValid((String) newValue)) {
                mjestoRodjenja.getStyleClass().removeAll("poljeNeispravno");
                mjestoRodjenja.getStyleClass().add("poljeIspravno");
                mjestoTacno = true;
            } else {
                mjestoRodjenja.getStyleClass().removeAll("poljeIspravno");
                mjestoRodjenja.getStyleClass().add("poljeNeispravno");
                mjestoTacno = false;
            }
        });
    }

    public boolean isEverythingValid(){
        if(!(imeTacno && prezimeTacno && indeksTacan && jmbgTacan && datumTacan && emailTacan && telefonTacan && mjestoTacno && spol.getValue()!=null)) return false;
        return true;
    }

    public void clickOnPotvrdi(ActionEvent event) {
        if (!isEverythingValid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nije validno");
            alert.setHeaderText("Unos podataka o studentu nije validan!");
            alert.setContentText("Crvenom bojom označeni su podaci koji su pogrešni ili nedostaju.");
            alert.show();
        }
        else {
            System.out.println("PODACI O STUDENTU");
            System.out.println("*Osnovni podaci* \n" + "Ime i prezime: " + ime.getText() + " " + prezime.getText() + "\nBroj indeksa: " + indeks.getText() + "\nSpol: " + spol.getValue().toString());
            System.out.println("*Detaljni lični podaci* \n" + "JMBG: " + jmbg.getText() + "\nDatum rođenja: " + datumRodjenja.getValue().toString() + "\nMjesto rođenja: " + mjestoRodjenja.getValue().toString());
            System.out.println("*Kontakt podaci* ");
            if (!adresa.getText().isEmpty()) System.out.println("Kontakt adresa: " + adresa.getText());
            else if (!telefon.getText().isEmpty()) System.out.println("Kontakt telefon: " + telefon.getText());
            System.out.println("E-mail adresa: " + email.getText());
            if (odsjek.getValue() != null || godStudija.getValue() != null || ciklusStudija.getValue() != null || redovan.getValue() != null || boracka.getValue() != null)
                System.out.println("Podaci o studiju ");
            if (odsjek.getValue() != null)
                System.out.println("Smjer: " + odsjek.getValue().toString());
            if (godStudija.getValue() != null)
                System.out.println("Godina: " + godStudija.getValue().toString());
            if (godStudija.getValue() != null)
                System.out.println("Ciklus: " + ciklusStudija.getValue().toString());
            if (redovan.getValue() != null)
                System.out.println("Status: " + redovan.getValue().toString());
            if(boracka.getValue() != null)
                System.out.println("Boračka kategorija: " + boracka.getValue().toString());

        }
    }
}
