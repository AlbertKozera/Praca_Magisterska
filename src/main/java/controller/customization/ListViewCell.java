package controller.customization;

import config.Path;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.Objects;

public class ListViewCell extends ListCell<String> {
    private final HBox hbox = new HBox();
    private final Label label = new Label("(empty)");
    private final Hyperlink hyperlink = new Hyperlink();
    private final ImageView copy = new ImageView();
    private final ImageView copyPressed = new ImageView();

    public ListViewCell() {
        super();
        String style = Objects.requireNonNull(getClass().getResource("/css/listView.css")).toExternalForm();
        copy.setImage(getImage("img_6.png"));
        copyPressed.setImage(getImage("img_7.png"));
        hbox.getChildren().addAll(hyperlink, label);
        label.setPadding(new Insets(0, 0, 0, 10));
        hyperlink.setGraphic(copy);
        hyperlink.getStylesheets().add(style);
        setCopyListeners();
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        if (item != null && !empty) {
            setGraphic(hbox);
            label.setText(item);
        }
    }

    private void setCopyListeners() {
        hyperlink.setOnAction(event -> {
            final var clipboard = Clipboard.getSystemClipboard();
            final var content = new ClipboardContent();
            content.putString(getItem());
            clipboard.setContent(content);
        });
        hyperlink.setOnMousePressed(event -> hyperlink.setGraphic(copyPressed));
        hyperlink.setOnMouseReleased(event -> hyperlink.setGraphic(copy));
    }

    private Image getImage(String fileName) {
        return new Image(new File(Path.PROJECT_RESOURCES_IMG + fileName).toURI().toString());
    }
}
