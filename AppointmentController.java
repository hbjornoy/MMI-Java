package application;


import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.Initializable;

import application.Appointment;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.CheckBox;
import javafx.scene.control.*;



public class AppointmentController implements Initializable{
	
	Appointment model = new Appointment();
	
	@FXML private TextField formaltext;
	@FXML private Text formalValid;
	
	@FXML private TextField romtext;
	@FXML private Text romValid;
	
	@FXML private DatePicker dato;
	@FXML private Text dateValid;
	
	@FXML private ComboBox<LocalTime> fraTid;
	@FXML private ComboBox<LocalTime> tilTid;
	@FXML private Text timeValid;
	
	@FXML private CheckBox repetisjon;
	@FXML private ToggleGroup toggleGroup;
	@FXML private RadioButton freq1;
	@FXML private RadioButton freq2;
	@FXML private RadioButton freq3;
	
	@FXML private DatePicker sluttDato;
	@FXML private Text sluttDateValid;
	
	@FXML private Button submit;
	@FXML private Text submitValid;
	
	
	
	@FXML
	private void formalFocusChange() {
		if (isFormalValid(formaltext.getText().trim())) {
			updateFormalModel();
			formalValid.setVisible(false);
		} 
		else {
			formalValid.setVisible(true);
			formalValid.setText("Mangler formål");
		}
	}
	
	private boolean isFormalValid(String text) {
		if (text == null || text.length() < 1) {
			model.setFormal(null);
			return false;
		}
		else {
			return true;
		}
	}
	
	@FXML
	private void romChange() {
		if (isRomValid(romtext.getText().trim())) {
			romValid.setVisible(false);
		} 
		else {
			romValid.setVisible(true);
			romValid.setText("Wrong format, FormatEX: IT-bygget 203, Realfagsbygget 10 osv");
		}
	}
	
	private final String REGEX1 = "[a-zA-Z-]+";
	private final String REGEX2 = "[0-9]+";  	//FÅ REGEX TIL Å GJELDE FOR DEF.  ([a-z\-A-Z])+\s([0-9])+
	private boolean isRomValid(String romtext) {
		String[] t = romtext.split(" ");
		if (t.length != 2) {
			model.setRom(null);
			return false;
		}
		else if (Pattern.matches(REGEX1, t[0]) && Pattern.matches(REGEX2, t[1])) {
			return true;
		}
		model.setRom(null);
		return false;
	}
	
	@FXML
	private void romFocusChange() {
		if (isRomValid(romtext.getText().trim())) {
			updateRomModel();
		}
	}
	
	@FXML
	private void onDateChange() {
		if (isDateValid()) {
			dateValid.setVisible(false);
			updateDateModel();
		} else {
			dateValid.setVisible(true);
			dateValid.setText("Wrong format");
		}
	}
	
	private boolean isDateValid() {
		if (dato.getValue().isAfter(LocalDate.now())) {
			if(!sluttDato.isDisabled() && sluttDato.getValue() != null) {onSluttDateChange();}
			return true;
		} 
		else {
			model.setDato(null);
			return false;
		}
	}
	
	public Appointment getModel() {
		return model;
	}
	
	
	private void updateFormalModel() {
		if (model != null) {
			model.setFormal(formaltext.getText().trim());
		}
	}
	private void updateRomModel() {
		if (model != null) {
			model.setRom(romtext.getText().trim());
		}
	}
	
	private void updateDateModel() {
		if (model != null) {
			model.setDato(dato.getValue());
		}
	}
	
	private void updateTimeModel() {
		if (model != null) {
			model.setFra(fraTid.getValue());
			model.setTil(tilTid.getValue());
		}
	}
	
	private void updateRepetisjonModel() {
		updateFreqModel();
		updateSluttDateModel();
	}
	
	private void updateFreqModel() {
		if (model != null && freq1.isSelected()) {
			model.setRepetisjon(1);
		}
		else if(model != null && freq2.isSelected()) {
			model.setRepetisjon(7);
		}
		else if(model != null && freq3.isSelected()) {
			model.setRepetisjon(28);
		}
	}
	
	private void updateSluttDateModel() {
		if (model != null) {
			model.setSlutt(sluttDato.getValue());
		}
	}
	
	private void clearRepetisjonModel() {
		if (model != null) {
			model.setRepetisjon(0);
			model.setSlutt(null);
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Collection<LocalTime> items = new ArrayList<LocalTime>();
		
		for (LocalTime time = LocalTime.of(8,0); time.isBefore(LocalTime.of(23,45)); time = time.plusMinutes(15)) {
			items.add(time);
		}

        fraTid.getItems().addAll(items);
        tilTid.getItems().addAll(items);
        freq1.setDisable(true);
		freq2.setDisable(true);
		freq3.setDisable(true);
        sluttDato.setDisable(true);
	}
	
	public void onTimeChange () {
		if (timeValid()) {
			timeValid.setVisible(false);
			updateTimeModel();
		} else {
			timeValid.setVisible(true);
			timeValid.setText("Wrong format");
		}
		//nextitem?
	}
	
	private boolean timeValid() {
		if (fraTid.getValue() == null || tilTid.getValue() == null || fraTid.getValue().plusMinutes(1).isAfter(tilTid.getValue())) {
			model.setFra(null);
			model.setTil(null);
			return false;
		} else {
			return true;
		}
	}
	
	
	@FXML
	private void onCheckbox() {
		if (repetisjon.isSelected()) {
			toggleRepetisjon();
			updateRepetisjonModel();
		}
		else {
			clearRepetisjonModel();
			toggleRepetisjon();
		}
	}
	
	private void toggleRepetisjon() {
		if (repetisjon.isSelected()) {
			freq1.setDisable(false);
			freq2.setDisable(false);
			freq3.setDisable(false);
			sluttDato.setDisable(false);
			sluttDateValid.setDisable(false);
		}
		else {
			freq1.setDisable(true);
			freq2.setDisable(true);
			freq3.setDisable(true);
			sluttDato.setDisable(true);
			sluttDateValid.setDisable(true);
		}
	}
	
	@FXML
	private void onFreqChange() {
		updateFreqModel();
	}
	
	
	@FXML
	private void onSluttDateChange() {
		if (isSluttDateValid()) {
			sluttDateValid.setVisible(false);
			updateSluttDateModel();
		} else {
			sluttDateValid.setVisible(true);
			sluttDateValid.setText("Wrong format");
		}
	}
	
	private boolean isSluttDateValid() {
		if (dato != null && sluttDato.getValue().isAfter(dato.getValue())) {
			return true;
		} 
		model.setSlutt(null);
		return false;
	}
	
	@FXML
	private void onSubmit() {
		if (isSubmitValid()) {
			submitValid.setText("Your Appointment(s) have been scheduled");
			submitValid.setFill(Color.GREEN);
			submitValid.setVisible(true);
			System.out.println(getModel().toString());
		} else {
			submitValid.setText("You have not filled in everything");
			submitValid.setFill(Color.RED);
			submitValid.setVisible(true);
		}
	}
	
	//private boolean isSubmitValid() {
	//	return true;
	//}
	
	private boolean isSubmitValid() {
		if (model.getFormal()==null || model.getRom()==null || model.getDato()==null 
				|| model.getFra()==null || model.getTil()==null) {
			return false;
		} 
		else if (model.getRepetisjon()!=0 && model.getSlutt()==null) {
			return false;
		}
		else if (model.getRepetisjon()==0 && model.getSlutt()!=null) {
			return false;
		}
		return true;
	}
		
}
