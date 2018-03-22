import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import RunBackPane from './RunBackPane.js'

  class AddSlotForm extends React.Component {
    constructor(props) {
      super(props);
      this.state = { start : this.getNow(),
                length: 0 }
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
      <div className="container container-blue">
        <header>
           <h5>Add timer slot:</h5>
        </header>

  	       <span className="form form-left" id="addForm">

              <label htmlFor="startTime">Time to start:</label>
              <input id="startTime" type="time" name="startTime"  value={this.state.start}
  							onChange={(event) => {
  								this.setState({start: event.target.value})
  							}}/>

  					<label htmlFor="length">length (minutes): </label>
              <input id="length" type="number" name="length" min="30" max="1490" step="30"  value={this.state.length}
  							onChange={(event) =>{
  								this.setState({length: event.target.value});
  							}}/>

  					<div className="buttonSpacingTop">
          		<input id="addButton" type="button" value="Add slot" onClick={this.handleAdd}></input>
  					</div>

  	       </span>


        <RunBackPane runBackFinish={this.props.runBackFinish}/>
        <footer>
        </footer>
      </div>
		)
	}
}

export {AddSlotForm as default}
