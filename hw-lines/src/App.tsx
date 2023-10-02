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

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    edges: string[]
    darkMode: boolean
}

class App extends Component<{}, AppState> { // <- {} means no props.

  constructor(props: any) {
    super(props);
    this.state = {
        edges: [],
        darkMode: false,
    };
  }

    toggleDarkMode = () => {
        this.setState(prevState => ({ darkMode: !prevState.darkMode }));
    }

    render() {
    return (
            <div className={this.state.darkMode ? 'dark-mode' : ''}>
                <div>
                    <h1 id="app-title">Line Mapper!</h1>
                    <center><button onClick={this.toggleDarkMode}>
                        {this.state.darkMode ? "Switch to Light Mode" : "Switch to Dark Mode"}
                    </button></center>
                    <div>
                        <Map edges = {this.state.edges} />
                    </div>
                    <EdgeList
                        onChange={(boxText) => {
                            this.setState({edges: boxText})
                        }}
                    />
                </div>
            </div>
    );
  }
}

export default App;
