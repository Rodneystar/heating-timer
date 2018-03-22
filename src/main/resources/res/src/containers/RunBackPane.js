import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

  class RunBackPane extends React.Component {
    constructor(props) {
      super(props);
      this.state = {runBackLength: 0};
    }

	getNow() {

	}

	handleAdd = (event) => {

	}

	render() {
		return(
      <div id="runBackForm" className="container container-blue">
        <div className="form form-horizontal">
          <label htmlFor="runBackLength">Set Run-back </label>
            <input id="runBackLength" type="number" name="runBackLength" min="30" max="1490" step="30"  value={this.state.runBackLength}
              onChange={(event) =>{
                this.setState({runBackLength: event.target.value});
              }}/>
        </div>
    	</div>
		)
	}
}

export {RunBackPane as default}
