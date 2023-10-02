/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package campuspaths;

import campuspaths.utils.CORSFilter;
import pathfinder.CampusMap;
import com.google.gson.Gson;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.*;
import java.util.*;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        CampusMap map = new CampusMap();
        Spark.get("/minPath", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String start = request.queryParams("start");
                String dest =  request.queryParams("dest");

                if (start == null || dest == null) {
                    Spark.halt(400, "Start or destination is null, please try again");
                }

                Path<Point> shortestPath = map.findShortestPath(start, dest);
                Gson gson = new Gson();
                return gson.toJson(shortestPath);
            }
        });

        Spark.get("/buildingsList", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String, String> buildingsList = map.buildingNames();
                Gson gson = new Gson();
                return gson.toJson(buildingsList);
            }
        });
    }

}
