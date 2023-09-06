package tvz.hr.projekt;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.LoggerFactory;
import tvz.hr.projekt.changes.Changes;
import tvz.hr.projekt.data.BazaPodataka;
import tvz.hr.projekt.iznimke.DataSourceException;
import tvz.hr.projekt.niti.BuyFlightsNit;
import tvz.hr.projekt.niti.ReadingChanges;
import tvz.hr.projekt.niti.WritingChanges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static tvz.hr.projekt.entiteti.Flight.logger;

public class HelloApplication extends Application {
    public static final String userAdmin = "admin";

    public static final String userGuest = "user";
    public static List<Changes> listaPromjena = new ArrayList<>();

    private static Stage appStage;

    private static BazaPodataka dataSource;

    static {
        try {
            dataSource = new BazaPodataka();
        } catch (DataSourceException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        appStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setScene(scene);
        stage.show();



        var buyFlightsTimeline = new Timeline(
                new KeyFrame(Duration.seconds(20), e -> Platform.runLater(new BuyFlightsNit()))
        );
        buyFlightsTimeline.setCycleCount(Timeline.INDEFINITE);
        buyFlightsTimeline.play();

        var writingChangesTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> Platform.runLater(new WritingChanges()))
        );
        writingChangesTimeline.setCycleCount(Timeline.INDEFINITE);
        writingChangesTimeline.play();

        var readingChangesTimeline = new Timeline(
                new KeyFrame(Duration.seconds(25), e-> Platform.runLater(new ReadingChanges()))
        );
        readingChangesTimeline.setCycleCount(Timeline.INDEFINITE);
        readingChangesTimeline.play();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void showWindow(String resourcePath) {
        try {
            var window = (Parent) FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(resourcePath)));
            appStage.setScene(new Scene(window));
            appStage.show();
            logger.info("Window " + resourcePath + " opened!");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BazaPodataka getDataSource() {
        return dataSource;
    }
}