package gui;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

class BatteryVoltageGraphWidget extends LineChart {

    BatteryVoltageGraphWidget() {
        super(new NumberAxis(0, 180, 10), new NumberAxis(0, 15, 1));

        setTitle("Battery Usage");

        getXAxis().setLabel("Elapsed Time");
        getYAxis().setLabel("Voltage");

        getData().add(new Series());

        for (int i = 0; i < 100; i++)
            add_point(i, 15 - Math.log(i));
    }

    void add_point(double elapsed_time, double voltage) {
        ((Series)getData().get(0)).getData().add(new Data(elapsed_time, voltage));
    }
}
