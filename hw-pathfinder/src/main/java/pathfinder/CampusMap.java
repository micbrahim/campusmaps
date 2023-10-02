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

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.*;

import java.util.*;

/**
 * implementation of an immutable map of the University of Washington
 */
public class CampusMap implements ModelAPI {
    // Buildings, paths between locations are stored in lists, while the entire campus is represented as a
    // Graph<Point, Double>
    // RI:
    //  buildings(0), ..., buildings(n) ≠ null,
    //  paths(0), ..., paths(m) ≠ null,
    //  buildings, paths ≠ null,
    //  campus contains all buildings, and is ≠ null
    // AF(this):
    //  list containing the buildings in campus == this.buildings
    //  list containing the paths between points in campus == this.paths
    //  map containing the entire campus == this.campus
    private List<CampusPath> paths;
    private List<CampusBuilding> buildings;
    private Graph<Point, Double> campus;

    private Map<String, Graph<Point, Double>.GraphNode> nodes;

    private static final boolean DEBUGGER = false;

    private void checkRep(){

        assert this.paths != null : "this.nodes is null ";
        assert this.buildings != null: "this.buildings is null";
        assert this.campus != null: "this.campus is null";

        if(DEBUGGER){
            for(CampusBuilding b : this.buildings){
                assert campus.getNode(new Point(b.getX(), b.getY())) != null;
            }
        }
    }

    /**
     * Creates a CampusMap object through parsing a list of buildings and paths from campus_buildings.csv,
     * campus_paths.csv.
     */
    public CampusMap(){


        this.buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        this.paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        this.campus = new Graph<>();

        for(CampusPath path : this.paths){
            Point l1 = new Point(path.getX1(), path.getY1());
            Point l2 = new Point(path.getX2(), path.getY2());

            if(campus.getNode(l1) == null){
                campus.addNode(campus.new GraphNode(l1));
            }
            if(campus.getNode(l2) == null){
                campus.addNode(campus.new GraphNode(l2));
            }
            campus.addEdge(campus.new GraphEdge(campus.getNode(l1), campus.getNode(l2), path.getDistance()));
        }
        this.mapStringToNode();
        checkRep();
    }
    /**
     * @param shortName The short name of a building to query.
     * @return {@literal true} iff the short name provided exists in this campus map.
     */
    @Override
    public boolean shortNameExists(String shortName) {
        checkRep();
        for(CampusBuilding b : buildings){
            if(shortName.equals(b.getShortName())){
                return true;
            }
        }
        checkRep();
        return false;
    }
    /**
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
    @Override
    public String longNameForShort(String shortName) throws IllegalArgumentException {
        checkRep();
        for(CampusBuilding b : this.buildings){
            if(shortName.equals(b.getShortName())){
                return b.getLongName();
            }
        }
        checkRep();
        throw new IllegalArgumentException("Short name provided does not exist");
    }
    /**
     * @return A mapping from all the buildings' short names to their long names in this campus map.
     */
    @Override
    public Map<String, String> buildingNames() {
        checkRep();
        Map<String, String> names = new HashMap<>();
        for(CampusBuilding b : this.buildings){
            names.put(b.getShortName(), b.getLongName());
        }
        checkRep();
        return names;
    }
    /**
     * Finds the shortest path, by distance, between the two provided buildings.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
     * if none exists.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) throws IllegalArgumentException {
        checkRep();
        if(startShortName == null || endShortName == null) {
            throw new IllegalArgumentException("Null building name");
        }else if(!this.shortNameExists(startShortName) || !this.shortNameExists(endShortName)){
            throw new IllegalArgumentException("Invalid building name");
        }

        Graph<Point, Double>.GraphNode start = this.nodes.get(startShortName);
        Graph<Point, Double>.GraphNode end = this.nodes.get(endShortName);
        Algo<Point> shortestPath = new Algo<>(this.campus, start, end);

        checkRep();
        return shortestPath.getMinPath();

    }

    //Private helper method to map names of buildings to a GraphNode object
    private void mapStringToNode(){
        checkRep();
        this.nodes = new HashMap<>();
        for(CampusBuilding b : this.buildings){
            for(Graph<Point, Double>.GraphNode n : this.campus.nodes()){
                if(b.getX() == n.getName().getX() && b.getY() == n.getName().getY()){
                    nodes.put(b.getShortName(), n);
                }
            }
        }
        checkRep();
    }

}
