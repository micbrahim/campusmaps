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

import React, {Component} from 'react';

interface EdgeListProps {
    onChange(edges: string[]): void;  // called when a new edge list is ready
}
interface EdgeListState{
    boxText: string
    warningText: string
}
/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            boxText: "",
            warningText: ""
        };
    }
    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={event => {this.setState({boxText: event.target.value})}}
                    value={this.state.boxText}
                /> <br/>
                <button onClick={event => {
                    let text: any[] = this.state.boxText.split(`\n`);
                    let correctFormat: boolean = true;

                    for (let i: number = 0; i < text.length; i++) {
                        text[i] = text[i].trim();
                        let line: any[] = text[i].split(` `);

                        if (line.length !== 5) {
                            correctFormat = false;

                            this.setState({warningText: `Line ${i + 1} does not have 5 elements`})

                            break;
                        } else if (isNaN(line[0]) || isNaN(line[1]) || isNaN(line[2]) || isNaN(line[3])) {
                            correctFormat = false;

                            this.setState({
                                warningText: `Line ${i + 1} contains a coordinate that is not
                            a number`})

                            break;
                        } else if (!isNaN(line[4]) || typeof line[4] !== "string") {

                            correctFormat = false;
                            this.setState({warningText: `Line ${i + 1} contains an invalid color`})

                            break;
                        } else if ((line[0] < 0 || line[0] > 4000) || (line[1] < 0 || line[1] > 4000) ||
                            (line[2] < 0 || line[2] > 4000) || (line[3] < 0 || line[3] > 4000)) {

                            correctFormat = false;
                            this.setState({
                                warningText: `Line ${i + 1} contains a coordinate not
                                between 0 and 4000!`
                            })

                            break;
                        }
                    }
                    if (correctFormat) {
                        this.setState({warningText: ""});
                        this.props.onChange(text);
                    } else {
                        this.props.onChange([]);
                    }
                }}>
                    Draw</button>
                <button onClick={event => {this.setState({boxText: ""});
                    this.setState({warningText: ""}); this.props.onChange([])}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
