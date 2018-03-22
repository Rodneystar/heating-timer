import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

  class ModeSwitchPane extends React.Component {
    constructor(props) {
      super(props);
    }
    render() {
      return (
        <div id="mode" className="container container-blue">
          <span className="form form-horizontal">
        	<header><h5 id="modeDisplay">Mode: {this.props.mode}</h5></header>

            <div className="buttonSpacingTop">
              <input type="button" id="ON" value="On" onClick={(event) => {this.props.handleModeSwitch(event.target.id)}}></input>
              <input type="button" id="OFF" value="Off" onClick={(event) => {this.props.handleModeSwitch(event.target.id)}}></input>
              <input type="button" id="TIMED" value="Timed" onClick={(event) => {this.props.handleModeSwitch(event.target.id)}}></input>
            </div>

            </span>
			      <footer> </footer>

		     </div>
      )
    }
	}


export {ModeSwitchPane as default}
