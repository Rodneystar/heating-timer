import React from 'react'
import ReactDOM from 'react-dom'
import axios from 'axios'

class AddSlotForm extends React.Component {
	now = this.getNow;
	state = { startTime: this.getNow() ,
						length: 0
					}

	getNow() {
		let n = new Date();
		let t = n.toTimeString().substring(0,5);
		return t;
	}

	handleAdd = (event) => {
		this.props.onSubmit(this.state);
	}

	render() {
		return(
      <div className="container container-blue form form-left">
				who?
    	 <header>
        	<h5>Add timer slot:</h5>
       </header>
	       <span>

            <label htmlFor="startTime">Time to start: </label>
            <input id="startTime" type="time" name="startTime"  value={this.state.startTime}
							onChange={(event) => {
								this.setState({startTime: event.target.value})
							}}/>

					<label htmlFor="length">length (minutes): </label>
            <input id="length" type="number" name="length" min="30" max="1490" step="30"  value={this.state.length}
							onChange={(event) =>{
								this.setState({length: event.target.value});
							}}/>
					<div className="buttonSpacingTop">
        		<input type="button" value="Add slot" onClick={this.handleAdd}/>
					</div>

	       </span>
       <footer>
			 </footer>
    	</div>
		);
	};
};

class App extends React.Component {
  state = {jsonTable:
					[{onTime:"21:53",length:60}],
					mode:"OFF",
					start:"14:38",
					length:0,
					removeIndex:0,
					command:""}

	timeNow = "00:58";

	handleAdd = (parms) => {
		parms.command = "addtimerevent"
		console.log("form submit thuckmyballs: " + parms.startTime)
	}

  render() {
    return(
      <AddSlotForm onSubmit={this.handleAdd}/>
    )
  }
}

export {App as default}
