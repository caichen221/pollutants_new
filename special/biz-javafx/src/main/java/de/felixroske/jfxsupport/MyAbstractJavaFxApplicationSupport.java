//package de.felixroske.jfxsupport;
//
//import com.iscas.biz.javafx.controller.BaseController;
//import javafx.application.Application;
//import javafx.application.HostServices;
//import javafx.application.Platform;
//import javafx.collections.ObservableList;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.DialogPane;
//import javafx.scene.control.Separator;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.SpringApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.function.Consumer;
//
///**
// * //TODO
// *
// * @author zhuquanwen
// * @vesion 1.0
// * @date 2020/3/22 21:36
// * @since jdk1.8
// */
//@Slf4j
//public abstract class MyAbstractJavaFxApplicationSupport extends Application {
//    private static Logger LOGGER = LoggerFactory.getLogger(AbstractJavaFxApplicationSupport.class);
//
//    private static String[] savedArgs = new String[0];
//
//    static Class<? extends AbstractFxmlView> savedInitialView;
//    static SplashScreen splashScreen;
//    private static ConfigurableApplicationContext applicationContext;
//
//
//    private static List<javafx.scene.image.Image> icons = new ArrayList<>();
//    private final List<javafx.scene.image.Image> defaultIcons = new ArrayList<>();
//
//    private final CompletableFuture<Runnable> splashIsShowing;
//
//    protected MyAbstractJavaFxApplicationSupport() {
//        splashIsShowing = new CompletableFuture<>();
//    }
//
//    public static Stage getStage() {
//        return GUIState.getStage();
//    }
//
//    public static Scene getScene() {
//        return GUIState.getScene();
//    }
//
//    public static HostServices getAppHostServices() {
//        return GUIState.getHostServices();
//    }
//
//    public static SystemTray getSystemTray() {
//        return GUIState.getSystemTray();
//    }
//
//
//    /**
//     * @param window The FxmlView derived class that should be shown.
//     * @param anchorPane
//     */
//    public static BaseController showView(final Class<? extends AbstractFxmlView> window, AnchorPane anchorPane) {
//        final AbstractFxmlView view = applicationContext.getBean(window);
//        BaseController presenter = (BaseController) view.getPresenter();
//        Parent view1 = view.getView();
//        anchorPane.getChildren().clear();
//        anchorPane.getChildren().add(view1);
//        return presenter;
//    }
//
//    public static Parent getView(final Class<? extends AbstractFxmlView> window) {
//        final AbstractFxmlView view = applicationContext.getBean(window);
//        Parent view1 = view.getView();
//        return view1;
//    }
//
//
//    public static BaseController showNewView(final Class<? extends AbstractFxmlView> window) {
//        final AbstractFxmlView view = applicationContext.getBean(window);
//
//        Stage newStage = new Stage();
//        Stage oldStage = getStage();
//        Scene newScene;
//        if (view.getView().getScene() != null) {
//            // This view was already shown so
//            // we have a scene for it and use this one.
//            newScene = view.getView().getScene();
//        } else {
//            newScene = new Scene(view.getView());
//        }
//
//        newStage.setScene(newScene);
//        newStage.setTitle(view.getDefaultTitle());
//        newStage.initStyle(view.getDefaultStyle());
//        newStage.getIcons().add(new javafx.scene.image.Image("icons/gear_64x64.png"));
//
//        newStage.show();
//        if (oldStage != null) {
//            oldStage.close();
//        }
//        BaseController presenter = (BaseController) view.getPresenter();
//        return presenter;
//    }
//
//    public static BaseController showNewViewWithMo(final Class<? extends AbstractFxmlView> window, Modality modality, Stage stage) {
//        final AbstractFxmlView view = applicationContext.getBean(window);
//
//        Stage newStage = new Stage();
//        Stage oldStage = getStage();
//        Scene newScene;
//        if (view.getView().getScene() != null) {
//            // This view was already shown so
//            // we have a scene for it and use this one.
//            newScene = view.getView().getScene();
//        } else {
//            newScene = new Scene(view.getView());
//        }
//
//        newStage.setScene(newScene);
//        newStage.setTitle(view.getDefaultTitle());
//        newStage.initStyle(view.getDefaultStyle());
//        newStage.getIcons().add(new javafx.scene.image.Image("icons/gear_64x64.png"));
//        newStage.initModality(modality);
//        newStage.initOwner(stage);
//        newStage.show();
//        if (oldStage != null) {
//            oldStage.close();
//        }
//        BaseController presenter = (BaseController) view.getPresenter();
//        return presenter;
//    }
//
//    /**
//     * @param window The FxmlView derived class that should be shown.
//     * @param mode   See {@code javafx.stage.Modality}.
//     */
//    public static void showView(final Class<? extends AbstractFxmlView> window, final Modality mode) {
//        final AbstractFxmlView view = applicationContext.getBean(window);
//        Stage newStage = new Stage();
//
//        Scene newScene;
//        if (view.getView().getScene() != null) {
//            // This view was already shown so
//            // we have a scene for it and use this one.
//            newScene = view.getView().getScene();
//        } else {
//            newScene = new Scene(view.getView());
//        }
//
//        newStage.setScene(newScene);
//        newStage.initModality(mode);
//        newStage.initOwner(getStage());
//        newStage.setTitle(view.getDefaultTitle());
//        newStage.initStyle(view.getDefaultStyle());
//        newStage.showAndWait();
//    }
//
//    private void loadIcons(ConfigurableApplicationContext ctx) {
//        try {
//            final List<String> fsImages = PropertyReaderHelper.get(ctx.getEnvironment(), Constant.KEY_APPICONS);
//
//            if (!fsImages.isEmpty()) {
//                fsImages.forEach((s) ->
//                        {
//                            javafx.scene.image.Image img = new javafx.scene.image.Image(getClass().getResource(s).toExternalForm());
//                            icons.add(img);
//                        }
//                );
//            } else { // add factory images
//                icons.addAll(defaultIcons);
//            }
//        } catch (Exception e) {
//            LOGGER.error("Failed to load icons: ", e);
//        }
//
//
//    }
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see javafx.application.Application#init()
//     */
//    @Override
//    public void init() throws Exception {
//        // Load in JavaFx Thread and reused by Completable Future, but should no be a big deal.
//        defaultIcons.addAll(loadDefaultIcons());
//        CompletableFuture.supplyAsync(() ->
//                SpringApplication.run(this.getClass(), savedArgs)
//        ).whenComplete((ctx, throwable) -> {
//            if (throwable != null) {
//                LOGGER.error("Failed to load spring application context: ", throwable);
//                Platform.runLater(() -> showErrorAlert(throwable));
//            } else {
//                Platform.runLater(() -> {
//                    loadIcons(ctx);
//                    launchApplicationView(ctx);
//                });
//            }
//        }).thenAcceptBothAsync(splashIsShowing, (ctx, closeSplash) -> {
//            Platform.runLater(closeSplash);
//        });
//    }
//
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see javafx.application.Application#start(javafx.stage.Stage)
//     */
//    @Override
//    public void start(final Stage stage) throws Exception {
//
//        GUIState.setStage(stage);
//        GUIState.setHostServices(this.getHostServices());
//        final Stage splashStage = new Stage(StageStyle.TRANSPARENT);
//
//        if (MyAbstractJavaFxApplicationSupport.splashScreen.visible()) {
//            final Scene splashScene = new Scene(splashScreen.getParent(), javafx.scene.paint.Color.TRANSPARENT);
//            splashStage.setScene(splashScene);
//            splashStage.getIcons().addAll(defaultIcons);
//            splashStage.initStyle(StageStyle.TRANSPARENT);
//            beforeShowingSplash(splashStage);
//            splashStage.show();
//        }
//
//        splashIsShowing.complete(() -> {
//            showInitialView();
//            if (MyAbstractJavaFxApplicationSupport.splashScreen.visible()) {
//                splashStage.hide();
//                splashStage.setScene(null);
//            }
//        });
//    }
//
//
//    /**
//     * Show initial view.
//     */
//    private void showInitialView() {
//        final String stageStyle = applicationContext.getEnvironment().getProperty(Constant.KEY_STAGE_STYLE);
//        if (stageStyle != null) {
//            GUIState.getStage().initStyle(StageStyle.valueOf(stageStyle.toUpperCase()));
//        } else {
//            GUIState.getStage().initStyle(StageStyle.DECORATED);
//        }
//
//        beforeInitialView(GUIState.getStage(), applicationContext);
//
//        showView(savedInitialView);
//    }
//
//
//    /**
//     * Launch application view.
//     */
//    private void launchApplicationView(final ConfigurableApplicationContext ctx) {
//        MyAbstractJavaFxApplicationSupport.applicationContext = ctx;
//    }
//
//    /**
//     * Show view.
//     *
//     * @param newView the new view
//     */
//    public static void showView(final Class<? extends AbstractFxmlView> newView) {
//        try {
//            final AbstractFxmlView view = applicationContext.getBean(newView);
//
//            if (GUIState.getScene() == null) {
//                GUIState.setScene(new Scene(view.getView()));
//            } else {
//                GUIState.getScene().setRoot(view.getView());
//            }
//            GUIState.getStage().setScene(GUIState.getScene());
//
//            applyEnvPropsToView();
//
//            GUIState.getStage().setTitle(view.getDefaultTitle());
//
//            GUIState.getStage().getIcons().addAll(icons);
//            GUIState.getStage().show();
//
//        } catch (Throwable t) {
//            LOGGER.error("Failed to load application: ", t);
//            showErrorAlert(t);
//        }
//    }
//
//    /**
//     * Show error alert that close app.
//     *
//     * @param throwable cause of error
//     */
//    private static void showErrorAlert(Throwable throwable) {
//        Alert alert = new Alert(Alert.AlertType.ERROR, "Oops! An unrecoverable error occurred.\n" +
//                "Please contact your software vendor.\n\n" +
//                "The application will stop now.\n\n" +
//                "Error: " + throwable.getMessage());
//        alert.showAndWait().ifPresent(response -> Platform.exit());
//    }
//
//    public static void showInfo(String message) {
//        log.info(message);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
//        alert.showAndWait();
//    }
//
//    public static void showConfirm(String message, Consumer consumer) {
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
//        DialogPane dialogPane = alert.getDialogPane();
//        ObservableList<ButtonType> buttonTypes = dialogPane.getButtonTypes();
//        javafx.scene.control.Button yes = (javafx.scene.control.Button) dialogPane.lookupButton(buttonTypes.get(0));
//        javafx.scene.control.Button no = (javafx.scene.control.Button) dialogPane.lookupButton(buttonTypes.get(1));
//        no.setDefaultButton(true);
//        yes.setDefaultButton(false);
//
//        yes.setOnAction((e) -> {
//            consumer.accept(true);
//        });
//        no.setOnAction(e -> {
//            consumer.accept(false);
//        });
//
//        alert.showAndWait();
//    }
//
//    /**
//     * throwable可以为空
//     * */
//    public static void showError(String message, Throwable throwable) {
//        //统一抛出异常
//        if (throwable == null) {
//            log.error(message);
//        } else {
//            log.error(message, throwable);
//        }
//
//        Alert alert = new Alert(Alert.AlertType.ERROR, message);
//        DialogPane dialogPane = alert.getDialogPane();
//        if (throwable != null) {
//            AnchorPane anchorPane = new AnchorPane();
//            javafx.scene.control.Label label = new javafx.scene.control.Label(throwable.getMessage());
//            label.setWrapText(true);
//            label.setTextFill(javafx.scene.paint.Color.RED);
//            Separator separator = new Separator();
//            anchorPane.getChildren().add(separator);
//            separator.prefWidthProperty().bind(anchorPane.widthProperty());
//            separator.setLayoutX(0);
//            separator.setLayoutY(10);
//            label.setLayoutX(8);
//            label.setLayoutY(20);
//            anchorPane.getChildren().add(label);
//            dialogPane.setExpandableContent(anchorPane);
//            dialogPane.setExpanded(false);
//
//            dialogPane.setMaxWidth(360);
//            dialogPane.setMinWidth(360);
//            label.setMaxWidth(340);
//            javafx.geometry.Insets insets = new javafx.geometry.Insets(0, 0, 15, 0);
//            label.setPadding(insets);
//        }
//
//        alert.showAndWait();
//    }
//
//    /**
//     * Apply env props to view.
//     */
//    private static void applyEnvPropsToView() {
//        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_TITLE, String.class,
//                GUIState.getStage()::setTitle);
//
//        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_STAGE_WIDTH, Double.class,
//                GUIState.getStage()::setWidth);
//
//        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_STAGE_HEIGHT, Double.class,
//                GUIState.getStage()::setHeight);
//
//        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_STAGE_RESIZABLE, Boolean.class,
//                GUIState.getStage()::setResizable);
//    }
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see javafx.application.Application#stop()
//     */
//    @Override
//    public void stop() throws Exception {
//        super.stop();
//        if (applicationContext != null) {
//            applicationContext.close();
//        } // else: someone did it already
//    }
//
//    /**
//     * Sets the title. Allows to overwrite values applied during construction at
//     * a later time.
//     *
//     * @param title the new title
//     */
//    protected static void setTitle(final String title) {
//        GUIState.getStage().setTitle(title);
//    }
//
//    /**
//     * Launch app.
//     *
//     * @param appClass the app class
//     * @param view     the view
//     * @param args     the args
//     */
//    public static void launch(final Class<? extends Application> appClass,
//                              final Class<? extends AbstractFxmlView> view, final String[] args) {
//
//        launch(appClass, view, new SplashScreen(), args);
//    }
//    /**
//     * Launch app.
//     * @deprecated To be more in line with javafx.application please use launch
//     * @param appClass the app class
//     * @param view     the view
//     * @param args     the args
//     */
//    @Deprecated
//    public static void launchApp(final Class<? extends Application> appClass,
//                                 final Class<? extends AbstractFxmlView> view, final String[] args) {
//
//        launch(appClass, view, new SplashScreen(), args);
//    }
//
//    /**
//     * Launch app.
//     *
//     * @param appClass     the app class
//     * @param view         the view
//     * @param splashScreen the splash screen
//     * @param args         the args
//     */
//    public static void launch(final Class<? extends Application> appClass,
//                              final Class<? extends AbstractFxmlView> view, final SplashScreen splashScreen, final String[] args) {
//        savedInitialView = view;
//        savedArgs = args;
//
//        if (splashScreen != null) {
//            MyAbstractJavaFxApplicationSupport.splashScreen = splashScreen;
//        } else {
//            MyAbstractJavaFxApplicationSupport.splashScreen = new SplashScreen();
//        }
//
//        if (SystemTray.isSupported()) {
//            GUIState.setSystemTray(SystemTray.getSystemTray());
//        }
//
//        Application.launch(appClass, args);
//    }
//    /**
//     * Launch app.
//     *
//     * @deprecated To be more in line with javafx.application please use launch
//     * @param appClass     the app class
//     * @param view         the view
//     * @param splashScreen the splash screen
//     * @param args         the args
//     */
//    @Deprecated
//    public static void launchApp(final Class<? extends Application> appClass,
//                                 final Class<? extends AbstractFxmlView> view, final SplashScreen splashScreen, final String[] args) {
//        launch(appClass, view, splashScreen, args);
//    }
//
//    /**
//     * Gets called after full initialization of Spring application context
//     * and JavaFX platform right before the initial view is shown.
//     * Override this method as a hook to add special code for your app. Especially meant to
//     * add AWT code to add a system tray icon and behavior by calling
//     * GUIState.getSystemTray() and modifying it accordingly.
//     * <p>
//     * By default noop.
//     *
//     * @param stage can be used to customize the stage before being displayed
//     * @param ctx   represents spring ctx where you can loog for beans.
//     */
//    public void beforeInitialView(final Stage stage, final ConfigurableApplicationContext ctx) {
//    }
//
//    public void beforeShowingSplash(Stage splashStage) {
//
//    }
//
//    public Collection<javafx.scene.image.Image> loadDefaultIcons() {
//        return Arrays.asList(new javafx.scene.image.Image(getClass().getResource("/icons/gear_16x16.png").toExternalForm()),
//                new javafx.scene.image.Image(getClass().getResource("/icons/gear_24x24.png").toExternalForm()),
//                new javafx.scene.image.Image(getClass().getResource("/icons/gear_36x36.png").toExternalForm()),
//                new javafx.scene.image.Image(getClass().getResource("/icons/gear_42x42.png").toExternalForm()),
//                new javafx.scene.image.Image(getClass().getResource("/icons/gear_64x64.png").toExternalForm()));
//    }
//}