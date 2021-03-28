package ru.kopylov.snippeter.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.management.BookmarkManager;
import ru.kopylov.snippeter.model.Bookmark;
import ru.kopylov.snippeter.model.Source;

import java.util.Optional;

public class BookmarksView implements Viewable {
    private static Logger logger = Logger.getLogger(BookmarksView.class);
    Context ctx = Context.getInstance();
    private BookmarkManager manager;
    private ListView<Bookmark> listView;
    private VBox root;
    private ObservableList<Bookmark> bookmarksList = FXCollections.observableArrayList();
    private EventHandler<ActionEvent> addBookmarkEventHandler = (event -> {
        Optional<String> bookmarkName = showInputTextDialog();
        TextViewerWebViewImpl textViewerWebView = ctx.get(TextViewer.class);
        if (bookmarkName.isPresent()) {
            saveBookmark(bookmarkName.get());
        }
        updateBookmarks();
    });

    public BookmarksView(Stage parent) {
        root = new VBox();
        listView = new ListView<>(bookmarksList);
        root.getChildren().addAll(listView);
        manager = ctx.get(BookmarkManager.class);

        listView.setOnMouseClicked(action -> {
            if (action.getClickCount() == 2) {
                Bookmark bookmark = listView.getSelectionModel().getSelectedItem();
                TextViewerWebViewImpl textViewerWebView = ctx.get(TextViewer.class);
                textViewerWebView.scrollTo(calcPosition(bookmark));
                parent.close();
            }
        });
        ctx.put(this);
    }

    private int calcPosition(Bookmark bookmark){
        TextViewerWebViewImpl textViewerWebView = ctx.get(TextViewer.class);
        int currentMaxPos =  textViewerWebView.getMaxYPos();
        if(Math.abs(currentMaxPos-bookmark.getMaxPosition()) <10){
            logger.info("no height changed");
            logger.info("prev height "+ bookmark.getMaxPosition());
            logger.info("prev pos "+ bookmark.getPosition());
            logger.info("curr height "+ currentMaxPos);
            return bookmark.getPosition();
        }else {
            double rate = (double) currentMaxPos / bookmark.getMaxPosition();
            logger.info("prev height "+ bookmark.getMaxPosition());
            logger.info("prev pos "+ bookmark.getPosition());
            logger.info("curr height "+ currentMaxPos);
            logger.info("curr height "+ (int)(bookmark.getPosition()*rate));
            logger.info("rate"+ rate);
            return  (int)(bookmark.getPosition()*rate);
        }
    }

    public void saveBookmark(String name) {
        TextViewerWebViewImpl textViewerWebView = ctx.get(TextViewer.class);
        int pos = textViewerWebView.getYPos();
        int maxPos = textViewerWebView.getMaxYPos();
        Source source = ctx.get(Source.class);
        if (source != null) {
            if (pos > 0 && maxPos>1) {
                try {
                    manager.merge(new Bookmark(name, source, pos, maxPos));
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }
            }
        }
    }

    public EventHandler<ActionEvent> getAddBookmarkEventHandler() {
        return addBookmarkEventHandler;
    }

    public void updateBookmarks() {
        Source source = ctx.get(Source.class);
        if (source == null) return;
        bookmarksList.clear();
        bookmarksList.addAll(manager.getBookmarks(source));
    }

    @Override
    public Node getView() {
        return root;
    }

    private Optional<String> showInputTextDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Новая закладка");
        dialog.setContentText("Метка:");
        return dialog.showAndWait();
    }
}
