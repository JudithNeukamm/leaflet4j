/**
 * The MIT License (MIT)
 *
 * Copyright (C) 2015 Andreas Grimmer <a.grimmer@gmx.at>
 * Christoph Sperl <ch.sperl@gmx.at>
 * Stefan Wurzinger <swurzinger@gmx.at>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.apidesign.html.demo.l4jdemo;

import net.java.html.boot.BrowserBuilder;
import net.java.html.leaflet.ILayer;
import net.java.html.leaflet.Icon;
import net.java.html.leaflet.IconOptions;
import net.java.html.leaflet.LatLng;
import net.java.html.leaflet.event.MouseEvent;
import net.java.html.leaflet.event.MouseListener;
import net.java.html.leaflet.Map;
import net.java.html.leaflet.MapOptions;
import net.java.html.leaflet.TileLayer;
import net.java.html.leaflet.TileLayerOptions;
import net.java.html.leaflet.Marker;
import net.java.html.leaflet.MarkerOptions;
import net.java.html.leaflet.Polygon;
import net.java.html.leaflet.Popup;
import net.java.html.leaflet.PopupOptions;

/**
 *
 * @author Andreas Grimmer
 * @author Christoph Sperl
 * @author Stefan Wurzinger 
 */
public final class Main {

    private Main() {
    }

    /**
     * Launches the browser
     * @param args The command line arguments.
     * @throws Exception Any exception.
     */
    public static void main(String... args) throws Exception {
        BrowserBuilder bb = BrowserBuilder.newBrowser().
                loadPage("pages/index.html").
                loadClass(Main.class).
                invoke("onPageLoad", args);

        bb.showAndWait();
        System.exit(0);
    }

    /**
     * Called when page is ready
     * @throws java.lang.Exception
     */
    public static void onPageLoad() throws Exception {
        // Create custom layer
    //    ExampleCustomLayer duckLayer = new ExampleCustomLayer(new LatLng(48.337074, 14.319868), "https://cdnjs.cloudflare.com/ajax/libs/fatcow-icons/20130425/FatCow_Icons32x32/rubber_duck.png");

        // Create a map zoomed to Linz.
        MapOptions mapOptions = new MapOptions()
                .setCenter(new LatLng(48.336614, 14.319305))
                .setZoom(15)
                .setLayers(new ILayer[] { /* duckLayer */ });
        final Map map = new Map("map", mapOptions);
        
        // add a tile layer to the map
        TileLayerOptions tlo = new TileLayerOptions();
        tlo.setAttribution("Map data &copy; <a href='http://www.thunderforest.com/opencyclemap/'>OpenCycleMap</a> contributors, "
                + "<a href='http://creativecommons.org/licenses/by-sa/2.0/'>CC-BY-SA</a>, "
                + "Imagery © <a href='http://www.thunderforest.com/'>Thunderforest</a>");
        tlo.setMaxZoom(18);
        TileLayer layer = new TileLayer("http://{s}.tile.thunderforest.com/cycle/{z}/{x}/{y}.png", tlo);
        map.addLayer(layer);
        
        // Set a marker with a user defined icon
        Icon icon = new Icon(new IconOptions("leaflet-0.7.2/images/marker-icon.png"));
        Marker m = new Marker(new LatLng(48.336614, 14.33), new MarkerOptions().setIcon(icon));
        m.addTo(map);
        
        // Add a polygon. When you click on the polygon a popup shows up
        Polygon polygonLayer = new Polygon(new LatLng[] {
                new LatLng(48.335067, 14.320660),
                new LatLng(48.337335, 14.323642),
                new LatLng(48.335238, 14.328942),
                new LatLng(48.333883, 14.327612)
        });
        polygonLayer.addMouseListener(MouseEvent.Type.CLICK, new MouseListener() {
            @Override
            public void onEvent(MouseEvent ev) {
                PopupOptions popupOptions = new PopupOptions().setMaxWidth(400);
                Popup popup = new Popup(popupOptions);
                popup.setLatLng(ev.getLatLng());
                popup.setContent("The Leaflet API for Java has been created here!");
                popup.openOn(map);
            }
        });
        map.addLayer(polygonLayer);
    }
}
