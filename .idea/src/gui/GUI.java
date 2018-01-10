package gui;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventDispatcher;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodRequests;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import util.*;
import real_time_model.*;

import static javafx.geometry.Orientation.HORIZONTAL;
import static javafx.geometry.Orientation.VERTICAL;

public class GUI extends Application {
    static Scene dummy_scene_to_load_and_hold_AEMBOT_stylesheet;
    static Image map_img;
    static Image robot_img;
    static Media bark_mp3;

    WindowWidget window;

    public static void main(String[] args)
        throws FileNotFoundException
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

//        stage.setTitle("Hello World!");
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//
//        ObservableList<String> css = btn.getStylesheets();
//        Object[] l = css.toArray();
//
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        stage.setScene(new Scene(root, 300, 250));
//        stage.show();

        /*

        WindowWidget                                    (no base class as yet)
            ConsoleWidget                               (extends Pane > Region > Group > Parent > Node)
                RealTimeModelWidget                     (extends Pane > Region > Group > Parent > Node)
                CameraFeedWidget                        (extends Pane > Region > Group > Parent > Node; need to integrate JavaCV)
                ControlPanelWidget                      (extends VBox > Pane > Region > Group > Parent > Node)
                    ModeToggleWidget                    (no base class as yet)
                    BarkButtonWidget                    (no base class as yet)
                DataPaneWidget                          (extends HBox > Pane > Region > Group > Parent > Node)
                    CoordinatesWidget                   (extends HBox > Pane > Region > Group > Parent > Node)
                    SpeedometerWidget                   (no base class as yet)
                    BatteryVoltageGraphWidget           (no base class as yet)
        */

        window = new WindowWidget(stage);
    }

    public static void set_node_geometry(Node node, double x, double y, double width, double height) {
        if (node.getContentBias() == HORIZONTAL) {
            node.minHeight(height);
            node.prefHeight(height);
            node.maxHeight(height);
        } else if (node.getContentBias() == VERTICAL) {
            node.minWidth(width);
            node.minWidth(width);
            node.minWidth(width);
        }

        node.resizeRelocate(x, y, width, height);
    }

    public static void set_region_geometry(Region r, double x, double y, double width, double height) {
        r.setMinSize(width, height);
        r.setPrefSize(width, height);
        r.setMaxSize(width, height);

        set_node_geometry(r, x, y, width, height);
    }
}

class NodeProperties {
    String                            accessibleHelp;
    String                            accessibleRoleDescription;
    AccessibleRole                    accessibleRole;
    String                            accessibleText;
    BlendMode                         blendMode;
    Bounds                            boundsInLocal;
    Bounds                            boundsInParent;
    CacheHint                         cacheHint;
    BooleanProperty                   cache;
    Node                              clip;
    Cursor                            cursor;
    DepthTest                         depthTest;
    ReadOnlyBooleanProperty           disabled;
    BooleanProperty                   disable;
    NodeOrientation                   effectiveNodeOrientation;
    Effect                            effect;
    EventDispatcher                   eventDispatcher;
    ReadOnlyBooleanProperty           focused;
    BooleanProperty                   focusTraversable;
    ReadOnlyBooleanProperty           hover;
    String                            id;
    InputMethodRequests               inputMethodRequests;
    ReadOnlyObjectProperty<Bounds>    layoutBounds;
    double                            layoutX;
    double                            layoutY;
    Transform                         localToParentTransform;
    Transform                         localToSceneTransform;
    BooleanProperty                   managed;
    BooleanProperty                   mouseTransparent;
    NodeOrientation                   nodeOrientation;
    double                            opacity;
    Parent                            parent;
    BooleanProperty                   pickOnBounds;
    ReadOnlyBooleanProperty           pressed;
    double                            rotate;
    Point3D                           rotationAxis;
    double                            scaleX;
    double                            scaleY;
    double                            scaleZ;
    Scene                             scene;
    String                            style;
    double                            translateX;
    double                            translateY;
    double                            translateZ;
    BooleanProperty                   visible;

    public NodeProperties(Node node) {
        accessibleHelp                 = node.getAccessibleHelp();
        accessibleRoleDescription      = node.getAccessibleRoleDescription();
        accessibleRole                 = node.getAccessibleRole();
        accessibleText                 = node.getAccessibleText();
        blendMode                      = node.getBlendMode();
        boundsInLocal                  = node.getBoundsInLocal();
        boundsInParent                 = node.getBoundsInParent();
        cacheHint                      = node.getCacheHint();
        cache                          = node.cacheProperty();
        clip                           = node.getClip();
        cursor                         = node.getCursor();
        depthTest                      = node.getDepthTest();
        disabled                       = node.disabledProperty();
        disable                        = node.disableProperty();
        effectiveNodeOrientation       = node.getEffectiveNodeOrientation();
        effect                         = node.getEffect();
        eventDispatcher                = node.getEventDispatcher();
        focused                        = node.focusedProperty();
        focusTraversable               = node.focusTraversableProperty();
        hover                          = node.hoverProperty();
        id                             = node.getId();
        inputMethodRequests            = node.getInputMethodRequests();
        layoutBounds                   = node.layoutBoundsProperty();
        layoutX                        = node.getLayoutX();
        layoutY                        = node.getLayoutY();
        localToParentTransform         = node.getLocalToParentTransform();
        localToSceneTransform          = node.getLocalToSceneTransform();
        managed                        = node.managedProperty();
        mouseTransparent               = node.mouseTransparentProperty();
        nodeOrientation                = node.getNodeOrientation();
        opacity                        = node.getOpacity();
        parent                         = node.getParent();
        pickOnBounds                   = node.pickOnBoundsProperty();
        pressed                        = node.pressedProperty();
        rotate                         = node.getRotate();
        rotationAxis                   = node.getRotationAxis();
        scaleX                         = node.getScaleX();
        scaleY                         = node.getScaleY();
        scaleZ                         = node.getScaleZ();
        scene                          = node.getScene();
        style                          = node.getStyle();
        translateX                     = node.getTranslateX();
        translateY                     = node.getTranslateY();
        translateZ                     = node.getTranslateZ();
        visible                        = node.visibleProperty();
    }
}

class ParentProperties {
    ReadOnlyBooleanProperty needsLayout;

    public ParentProperties(Parent p) {
        needsLayout = p.needsLayoutProperty();
    }
}

class RegionProperties {
    Background                 background;
    Border                     border;
    BooleanProperty            cacheShape;
    BooleanProperty            centerShape;
    double                     height;
    Insets                     insets;
    double                     maxHeight;
    double                     maxWidth;
    double                     minHeight;
    double                     minWidth;
    Insets                     opaqueInsets;
    Insets                     padding;
    double                     prefHeight;
    double                     prefWidth;
    BooleanProperty            scaleShape;
    Shape                      shape;
    BooleanProperty            snapToPixel;
    ReadOnlyDoubleProperty     width;

    public RegionProperties(Region r) {
        background     = r.getBackground();
        border         = r.getBorder();
        cacheShape     = r.cacheShapeProperty();
        centerShape    = r.centerShapeProperty();
        height         = r.getHeight();
        insets         = r.getInsets();
        maxHeight      = r.getMaxHeight();
        maxWidth       = r.getMaxWidth();
        minHeight      = r.getMinHeight();
        minWidth       = r.getMinWidth();
        opaqueInsets   = r.getOpaqueInsets();
        padding        = r.getPadding();
        prefHeight     = r.getPrefHeight();
        prefWidth      = r.getPrefWidth();
        scaleShape     = r.scaleShapeProperty();
        shape          = r.getShape();
        snapToPixel    = r.snapToPixelProperty();
        width          = r.widthProperty();
    }
}

class LinealProperties {
    NodeProperties     node_props;
    ParentProperties   parent_props;
    RegionProperties   region_props;

    public LinealProperties(Node n) {
        node_props = new NodeProperties(n);

        if (n instanceof Region) {
            Region r = (Region)n;
            parent_props = new ParentProperties(r);
            region_props = new RegionProperties(r);
            return;
        }

        if (n instanceof Parent) {
            Parent p = (Parent)n;
            parent_props = new ParentProperties(p);
            return;
        }
    }
}

class TilePaneProperties {
    Pos             alignment;
    double          hgap;
    Orientation     orientation;
    int             prefColumns;
    int             prefRows;
    double          prefTileHeight;
    double          prefTileWidth;
    Pos             tileAlignment;
    double          tileHeight;
    double          tileWidth;
    double          vgap;

    TilePaneProperties(TilePane p) {
        alignment          = p.getAlignment();
        hgap               = p.getHgap();
        orientation        = p.getOrientation();
        prefColumns        = p.getPrefColumns();
        prefRows           = p.getPrefRows();
        prefTileHeight     = p.getPrefTileHeight();
        prefTileWidth      = p.getPrefTileWidth();
        tileAlignment      = p.getTileAlignment();
        tileHeight         = p.getTileHeight();
        tileWidth          = p.getTileWidth();
        vgap               = p.getVgap();
    }
}
