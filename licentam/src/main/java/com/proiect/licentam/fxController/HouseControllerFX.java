package com.proiect.licentam.fxController;

import com.proiect.licentam.utils.MyApplicationContextSingleton;
import com.proiect.licentam.model.Home;
import com.proiect.licentam.model.User;
import com.proiect.licentam.services.RealEstateService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@FxmlView("RealEstate.fxml")
public class HouseControllerFX {

    public TableView<Home> homeTable;
    public TableColumn<Home, String> typeColumn;
    public TableColumn<Home, String> priceColumn;
    public TableColumn<Home, String> surfaceColumn;
    public TableColumn<Home, String> yearBuiltColumn;
    public TableColumn<Home, String> zoneColumn;
    public TableColumn<Home, String> addressColumn;
    public TableColumn<Home, String> statusColumn;
    public TableColumn<Home, String> numberColumn;
    public Button showB;
    public Button saveB;
    public TextField textFieldPrice;
    public TextField textFieldSurface;
    public TextField textFieldYearBuilt;
    public TextField textFieldAddress;
    public ComboBox typeCB;
    public ComboBox zoneCB;
    public ComboBox soldCB;
    public Button deleteB;
    public ComboBox soldUpdateCB;
    public Button updateHome;
    public TextField textFieldNewPrice;
    public Button creditB;
    public Button futurePriceB;
    public Button returnOfInvestB;
    public TextField m2PriceFromTF;
    public TextField m2PriceToTF;
    public TextField yearFromTF;
    public TextField yearToTF;
    public ComboBox typeSelectCB;
    public ComboBox zoneSelectCB;
    public Button searchB;
    public Button myListB;
    public Button undervaluedB;
    public Button bestZone;
    public Button exportB;
    public TextField numberTextF;

    MyApplicationContextSingleton myApplicationContextSingleton;

    private RealEstateService realEstateService;

    private User currentUser;

    @FXML
    public Button logoutB;

    void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Autowired
    public HouseControllerFX(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
        initialize();
    }

    public void logOut(ActionEvent actionEvent) {

        MyApplicationContextSingleton myApplicationContextSingleton = this.myApplicationContextSingleton.getInstance();
        FxWeaver fxWeaver = myApplicationContextSingleton.getApplicationContext().getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(UserControllerFX.class);
        Scene scene = new Scene(root);
        Stage stage = (Stage) logoutB.getScene().getWindow();
        UserControllerFX userControllerFX = fxWeaver.getBean(UserControllerFX.class);
        scene.getStylesheets().add(userControllerFX.getClass().getResource("appStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Real Estate Investment App");
        stage.getIcons().add(new Image(UserControllerFX.class.getResourceAsStream("reia.png")));
        stage.show();
    }

    public void populateTable(ActionEvent actionEvent) {

        homeTable.getItems().clear();
        ObservableList<Home> homeObservableList = FXCollections.observableArrayList(realEstateService.getHomeList());
        homeTable.getItems().addAll(homeObservableList);
    }

    private void clearTextFields() {
        textFieldNewPrice.clear();
        textFieldAddress.clear();
        textFieldPrice.clear();
        textFieldSurface.clear();
        textFieldYearBuilt.clear();
        numberTextF.clear();
        m2PriceFromTF.clear();
        m2PriceToTF.clear();
        yearFromTF.clear();
        yearToTF.clear();
    }


    public void initialize() {

        try {
            typeCB.getItems().addAll(
                    "Flat",
                    "House"
            );
        } catch (NullPointerException ignored) {
        }
        try {
            typeSelectCB.getItems().addAll(
                    "Type",
                    "Flat",
                    "House"
            );
        } catch (NullPointerException ignored) {
        }
        try {
            zoneCB.getItems().addAll(
                    "Ultra Central",
                    "Central",
                    "Medium quarter",
                    "Periphery",
                    "Metropolitan area"
            );
        } catch (NullPointerException ignored) {
        }
        try {
            zoneSelectCB.getItems().addAll(
                    "Zone",
                    "Ultra Central",
                    "Central",
                    "Medium quarter",
                    "Periphery",
                    "Metropolitan area"
            );
        } catch (NullPointerException ignored) {
        }
        try {
            soldCB.getItems().addAll(
                    "Sold",
                    "Not Sold"
            );
        } catch (NullPointerException ignored) {
        }
        try {
            soldUpdateCB.getItems().addAll(
                    "Sold",
                    "Not Sold"
            );
        } catch (NullPointerException ignored) {
        }
        try {
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            typeColumn.setStyle("-fx-base: #989d41");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            priceColumn.setStyle("-fx-base: #989d41");
            surfaceColumn.setCellValueFactory(new PropertyValueFactory<>("constructedArea"));
            surfaceColumn.setStyle("-fx-base: #989d41");
            yearBuiltColumn.setCellValueFactory(new PropertyValueFactory<>("buildYear"));
            yearBuiltColumn.setStyle("-fx-base: #989d41");
            zoneColumn.setCellValueFactory(new PropertyValueFactory<>("zoneType"));
            zoneColumn.setStyle("-fx-base: #989d41");
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            addressColumn.setStyle("-fx-base: #989d41");
            numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
            numberColumn.setStyle("-fx-base: #989d41");
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            statusColumn.setStyle("-fx-base: #989d41");
        } catch (NullPointerException ignored) {
        }

    }


    public void saveHome(ActionEvent actionEvent) {
        try {
            Home home = new Home();
            home.setType(typeCB.getValue().toString());
            home.setPrice(Integer.valueOf(textFieldPrice.getText()));
            home.setConstructedArea(Integer.valueOf(textFieldSurface.getText()));
            home.setBuildYear(Integer.valueOf(textFieldYearBuilt.getText()));
            home.setZoneType(zoneCB.getValue().toString());
            home.setAddress(textFieldAddress.getText());
            home.setNumber(numberTextF.getText());
            home.setStatus(soldCB.getValue().toString());
            home.setValuePoints(0);
            home.setUser(getCurrentUser());

            realEstateService.saveHome(home);
            clearTextFields();
            showB.fire();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setTitle("Save Error");
            alert.setHeaderText("An error occured while saving the home");
            alert.setContentText("Please text valid inputs");
            alert.showAndWait();
            clearTextFields();
        }catch (NullPointerException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setTitle("Save Error");
            alert.setHeaderText("An error occured while saving the home");
            alert.setContentText("Please select valid options");
            alert.showAndWait();
            clearTextFields();
        }
    }


    public void deleteHome(ActionEvent actionEvent) {
        Home home = homeTable.getSelectionModel().getSelectedItem();
        if (home != null) {
            if (realEstateService.deleteHome(home)) {
                showB.fire();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setTitle("Delete home");
            alert.setHeaderText("Home not deleted");
            alert.setContentText("Please SELECT a flat or house");
            alert.showAndWait();
        }
    }


    public void updateHome(ActionEvent actionEvent) {
        Home home = homeTable.getSelectionModel().getSelectedItem();

        try {
            if (home != null) {
                if (!soldUpdateCB.getSelectionModel().isEmpty()) {
                    home.setStatus(soldUpdateCB.getValue().toString());
                }
                if (!textFieldNewPrice.getText().equals("")) {
                    home.setPrice(Integer.valueOf(textFieldNewPrice.getText()));
                }
                realEstateService.updateHome(home);
                clearTextFields();
                showB.fire();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
                alert.setTitle("Update Error");
                alert.setHeaderText("An error occured while updating the home");
                alert.setContentText("Please SELECT a home");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setTitle("Update Error");
            alert.setHeaderText("An error occured while updating the home");
            alert.setContentText("Please enter valid inputs");
            alert.showAndWait();
            clearTextFields();
        }
    }


    private Integer getBigDecimal(Double number) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(number));
        int intValue = bigDecimal.intValue();
        return intValue;
    }

    private void setButtonColors(Alert alert){
        List<ButtonType> buttonList = alert.getDialogPane().getButtonTypes();
        for(ButtonType bt : buttonList){

        }
    }


    public void calculateCredit(ActionEvent actionEvent) {
        Home home = homeTable.getSelectionModel().getSelectedItem();
        if (home != null) {
            Alert chooseAlert = new Alert(Alert.AlertType.CONFIRMATION);
            chooseAlert.setTitle("Credit Estimation");
            chooseAlert.setHeaderText(null);
            chooseAlert.setContentText("Choose your option.");
            chooseAlert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());

            ButtonType buttonTypeOne = new ButtonType("Custom");
            ButtonType buttonTypeTwo = new ButtonType("Estimation");
            chooseAlert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = chooseAlert.showAndWait();

            if (result.get() == buttonTypeOne) {
                try {
                    Dialog<Pair<String, String>> dialog = new Dialog<>();
                    dialog.setTitle("Custom Credit");
                    dialog.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());

                    ButtonType chooseButtonType = new ButtonType("Calculate", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(chooseButtonType);


                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);
                    gridPane.setPadding(new Insets(20, 150, 10, 10));

                    TextField creditYears = new TextField();
                    creditYears.setPromptText("Years");
                    TextField interest = new TextField();
                    interest.setPromptText("Interest");

                    gridPane.add(creditYears, 0, 0);
                    gridPane.add(interest, 2, 0);

                    dialog.getDialogPane().setContent(gridPane);

                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == chooseButtonType) {
                            return new Pair<>(creditYears.getText(), interest.getText());
                        }
                        return null;
                    });

                    Optional<Pair<String, String>> resultForCredit = dialog.showAndWait();

                    if (resultForCredit.isPresent()) {
                        double creditForCustomYears = realEstateService.calculateCostumCredit(home, Integer.valueOf(resultForCredit.get().getKey()), Double.valueOf(resultForCredit.get().getValue()));

                        Alert informCustomCreditAlert = new Alert(Alert.AlertType.INFORMATION);
                        informCustomCreditAlert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());
                        informCustomCreditAlert.setTitle("Credits");
                        informCustomCreditAlert.setHeaderText(null);
                        informCustomCreditAlert.setContentText("Credit for " + resultForCredit.get().getKey() + " years: " + getBigDecimal(creditForCustomYears));
                        informCustomCreditAlert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
                    alert.setTitle("Calculation Error");
                    alert.setHeaderText("An error occured while calculating custom credit");
                    alert.setContentText("Please enter valid input");
                    alert.showAndWait();
                    creditB.fire();
                }
            } else if (result.get() == buttonTypeTwo) {
                ArrayList<Double> creditList = realEstateService.calculateCredit(home);

                Alert informCreditAlert = new Alert(Alert.AlertType.INFORMATION);
                informCreditAlert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());
                informCreditAlert.setTitle("Credits");
                informCreditAlert.setHeaderText(null);
                informCreditAlert.setContentText("Credit 10 years: " + getBigDecimal(creditList.get(0)) + "\n" +
                        "Credit 20 years: " + getBigDecimal(creditList.get(1)) + "\n" +
                        "Credit 30 years: " + getBigDecimal(creditList.get(2)) + "\n");
                informCreditAlert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setTitle("Credit calculation");
            alert.setHeaderText("Estimated credit not calculated");
            alert.setContentText("Please SELECT a flat or house");
            alert.showAndWait();
        }
    }


    public void calculateFuturePrice(ActionEvent actionEvent) {
        Home home = homeTable.getSelectionModel().getSelectedItem();
        try {
            if (home != null) {
                TextInputDialog dialog = new TextInputDialog();

                dialog.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());


                dialog.setTitle("Calculate future price");
                dialog.setHeaderText("Calculate price after x years:");
                dialog.setContentText("Please enter number of years: ");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String afterXyears = result.get();
                    double futurePrice = realEstateService.calculatePrice(home, Integer.valueOf(afterXyears));
                    Integer newPrice = getBigDecimal(futurePrice);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());
                    alert.setTitle("Future estimated price: ");
                    alert.setHeaderText(null);
                    alert.setContentText("Future estimated price: " + newPrice);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
                alert.setTitle("Future price");
                alert.setHeaderText("Future price not calculated");
                alert.setContentText("Please SELECT a flat or house");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setTitle("Calculation Error");
            alert.setHeaderText("An error occured while calculating future price");
            alert.setContentText("Please enter valid input");
            alert.showAndWait();
            futurePriceB.fire();
        }
    }


    public void calculateReturnOfInvestment(ActionEvent actionEvent) {
        Home home = homeTable.getSelectionModel().getSelectedItem();
        try {
            if (home != null) {
                TextInputDialog dialog = new TextInputDialog();

                dialog.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());

                dialog.setTitle("Calculate return of investment");
                dialog.setHeaderText("Calculate return of investment:");
                dialog.setContentText("Please enter estimated monthly rent: ");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String estimatedRent = result.get();
                    double returnOfInvestment = realEstateService.calculateReturnOfInvestment(home, Integer.valueOf(estimatedRent));

                    BigDecimal bd = new BigDecimal(returnOfInvestment).setScale(2, RoundingMode.HALF_UP);
                    double newReturnOfInvestment = bd.doubleValue();
                    String doubleAsString = String.valueOf(newReturnOfInvestment);
                    int indexOfDecimal = doubleAsString.indexOf(".");


                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());
                    alert.setTitle("Return of investment");
                    alert.setHeaderText(null);
                    alert.setContentText("Return of investment: " + doubleAsString.substring(indexOfDecimal + 1));
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
                alert.setTitle("Return of investment");
                alert.setHeaderText("ROI not calculated");
                alert.setContentText("Please SELECT a flat or house");
                alert.showAndWait();
            }
        }catch (NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setTitle("Calculation Error");
            alert.setHeaderText("An error occured while calculating ROI");
            alert.setContentText("Please enter valid input");
            alert.showAndWait();
            returnOfInvestB.fire();
        }

    }


    public void searchSpecificHomes(ActionEvent actionEvent) {

        homeTable.getItems().clear();
        //String zoeSelect = zoneSelectCB.getValue().toString();

        try {
            String homeType;
            if (typeSelectCB.getValue() == null || typeSelectCB.getValue().equals("Type")) {
                homeType = " ";
            } else {
                homeType = typeSelectCB.getValue().toString();
            }
            String homeZone;
            if (zoneSelectCB.getValue() == null || zoneSelectCB.getValue().equals("Zone")) {
                homeZone = " ";
            } else {
                homeZone = zoneSelectCB.getValue().toString();
            }
            Integer yearFrom;
            if (yearFromTF.getText().equals("")) {
                yearFrom = -1;
            } else {
                yearFrom = Integer.valueOf(yearFromTF.getText());
            }
            Integer yearTo;
            if (yearToTF.getText().equals("")) {
                yearTo = -1;
            } else {
                yearTo = Integer.valueOf(yearToTF.getText());
            }
            Integer priceFrom;
            if (m2PriceFromTF.getText().equals("")) {
                priceFrom = -1;
            } else {
                priceFrom = Integer.valueOf(m2PriceFromTF.getText());
            }
            Integer priceTo;
            if (m2PriceToTF.getText().equals("")) {
                priceTo = -1;
            } else {
                priceTo = Integer.valueOf(m2PriceToTF.getText());
            }
            List<Home> temporaryHomeList = realEstateService.getSpecificHomes(homeType, homeZone, yearFrom, yearTo, priceFrom, priceTo);

            ObservableList<Home> homeObservableList = FXCollections.observableArrayList(temporaryHomeList);
            homeTable.getItems().addAll(homeObservableList);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setTitle("Search Error");
            alert.setHeaderText("An error occured while searching for homes");
            alert.setContentText("Please enter valid inputs");
            alert.showAndWait();
        }


        clearTextFields();
        typeSelectCB.getSelectionModel().clearSelection();
        zoneSelectCB.getSelectionModel().clearSelection();
    }

    public void showMyHomes(ActionEvent actionEvent) {
        homeTable.getItems().clear();

        List<Home> temporaryHomeList = realEstateService.getMyHomes(getCurrentUser());

        ObservableList<Home> homeObservableList = FXCollections.observableArrayList(temporaryHomeList);
        homeTable.getItems().addAll(homeObservableList);
    }

    public void showUndervaluedHomes(ActionEvent actionEvent) {
        homeTable.getItems().clear();

        List<Home> temporaryHomeList = realEstateService.getUndervaluedHomes();

        ObservableList<Home> homeObservableList = FXCollections.observableArrayList(temporaryHomeList);
        homeTable.getItems().addAll(homeObservableList);
    }

    public void getBestZones(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());
        alert.setTitle("Best Zone ");
        alert.setHeaderText(null);
        alert.setContentText("Best Zone to invest is: " + realEstateService.getBestZone(realEstateService.getHomeList()));
        alert.showAndWait();

        homeTable.getItems().clear();

        List<Home> temporaryHomeList = realEstateService.getBestZoneHomes();

        ObservableList<Home> homeObservableList = FXCollections.observableArrayList(temporaryHomeList);
        homeTable.getItems().addAll(homeObservableList);
    }

    public void exportHomeToPdf(ActionEvent actionEvent) {
        Integer sizeOfHomeTable = homeTable.getItems().size();
        List<Home> homeList = homeTable.getItems().subList(0, sizeOfHomeTable);
        if (realEstateService.exportHomesToPdf(homeList) && homeList.size() != 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("appStyle.css").toExternalForm());
            alert.setTitle("PDF Export");
            alert.setHeaderText(null);
            alert.setContentText("File exported succesfully on your desktop");
            alert.showAndWait();
        } else if(homeList.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setTitle("PDF Export");
            alert.setHeaderText("Nothing to export");
            alert.setContentText("Please select data to export");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getScene().getStylesheets().add(HouseControllerFX.class.getResource("errorStyle.css").toExternalForm());
            alert.setTitle("PDF Export");
            alert.setHeaderText("Error occured while exporting file");
            alert.setContentText("Please try again");
            alert.showAndWait();
        }
    }
}
