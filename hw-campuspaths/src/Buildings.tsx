import React, {Component} from 'react';

// state contains user's selection for buildings,
// along with entire buildings list for drop down menu
interface BuildingsState {
    start: string;
    end: string;
    buildingsList: Map<string,string>;
}

// props contains a call to onchange function, which takes in array of any
interface BuildingsProps {
    onChange(pathEdges: any[]): void;
}

class Buildings extends Component<BuildingsProps, BuildingsState> {
    constructor(props: any) {
        super(props);
        this.state = {
            start: "Start Building",
            end: "End Building",
            buildingsList: new Map<string, string>()
        };
    }
    // render function, creates drop down menus and extra buttons
    render() {
        const b = Object.values(this.state.buildingsList);
        const b2 = Object.entries(this.state.buildingsList);
        const b3: Map<string,string> = new Map<string, string>(); // creating an extra map so that it can be swapped
        b2.forEach(([key , value]) => b3.set(value, key)); // swapping key and values so that state can be updated
        return (
            <div>
                <center>
                <select onChange={(event => this.setState({start: b3.get(event.target.value) as string}))}>
                    <option>Starting Building</option>
                    {b.map((building, index) => (
                        <option key={index} value={building}>{building}</option>
                    ))}
                </select>
                <select onChange={(event => this.setState({end: b3.get(event.target.value) as string}))}>
                    <option>Ending Building</option>
                    {b.map((building, index) => (
                        <option key={index} value={building}>{building}</option>
                    ))}
                </select>
                </center>
               <center>
                    <button onClick={() => this.pathRequest(this.state.start, this.state.end)}>Find Path</button>
                    <button onClick={() => {this.props.onChange([])}}>Clear Map</button>
               </center>
            </div>
        );
    }

    path = (text: any) => { // parses through path and in returns shorter building names as a json
        const edges: any[] = [];
        for (let i = 0; i < text.path.length; i++) {
            const start = text.path[i].start;
            const end = text.path[i].end;
            const segment: any = {start: start, end: end, cost: 0}
            edges.push(segment);
        }
        this.props.onChange(edges); // calls onchange function in App
    }

    pathRequest = async (start: string, end: string) => { // function takes start and ending names as param
        try {
            let response = await fetch("http://localhost:4567/minPath?start=" + start + "&dest=" + end); // fetches using the link from the spark server.
            if (!response.ok) {
                alert("Response is null; please choose a valid path.");
                return;
            }
            let pathType = await response.json() as any;
            if (this.state.start === this.state.end) {
                alert("Both states are the same; please choose a valid path.");
                return;
            }
            this.path(pathType);
        } catch (e) {
            alert("Error contacting Spark Server, please try again.");
            console.log(e);
        }
    }



    buildingRequest = async () => { //  function grabs the list of buildings from the spark server.
        try {
            let response = await fetch("http://localhost:4567/buildingsList");
            if (!response.ok) {
                alert("Server response is null, please try again.");
                return;
            }
            let buildingType = await response.json() as Map<string,string>;
            this.setState({buildingsList: buildingType});
            console.log(buildingType);
        } catch (e) {
            alert("Error contacting the Spark Server, please try again.");
            console.log(e);
        }
    };

    componentDidMount() { // function to make sure the building request it sent out immediately after called
        this.buildingRequest();
    }

}

export default Buildings;