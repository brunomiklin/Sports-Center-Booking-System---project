package hr.tvz.sportapp.controller;

import hr.tvz.sportapp.repository.AppRepository;
import hr.tvz.sportapp.repository.RepositoryTaker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage stage;
    private static AppRepository repo;

    private SceneManager(){}

    public static void init(Stage primaryStage,AppRepository repository)
    {
        stage =primaryStage;
        repo = repository;
    }

    public static void showInputUsers()
    {
        setScene("/fxml/inputUsers.fxml","Unos korisnika");
    }
    public static void showUserSearch()
    {
        setScene("/fxml/userSearch.fxml","Pretraživanje");
    }
    public static void showInputHall()
    {
        setScene("/fxml/hallInput.fxml","Unos dvorana");
    }
    public static void showMain()
    {
        setScene("/fxml/mainScene.fxml","Početni prozor :)");
    }
    public static void showCreateBooking()
    {
        setScene("/fxml/createBooking.fxml","Kreiranje termina");
    }
    public static void showJoinBooking()
    {
        setScene("/fxml/joinBooking.fxml","Pridruživanje korisnika rezervaciji");
    }


    private static void setScene(String fxmlPath,String title)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(SceneManager.class.getResource("/fxml/style.css").toExternalForm());

            Object controller = fxmlLoader.getController();
            if(controller instanceof RepositoryTaker rt)
            {
                rt.setRepo(repo);
            }

            stage.setTitle(title);
            stage.setScene(scene);
        }catch (IOException ioe)
        {
            throw new RuntimeException("Ne mogu učitati FXML: " + fxmlPath, ioe);
        }



    }
}
