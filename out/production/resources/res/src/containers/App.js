import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import TimerList from './TimerList.js';
import AddSlotForm from './AddSlotForm.js';
import ModeSwitchPane from './ModeSwitchPane.js'

class App extends React.Component {
  constructor() {
    super()
    this.hostUrl = "http://localhost:8000/"
    this.state = {jsonTable: []};
            this.getProps();
  }

  getProps = () => {
    axios.get(this.hostUrl + "update/")
      .then((resp) => {
        resp.data.hostUrl = this.hostUrl
          this.setState(resp.data);
      }, (reason) => {
        console.log(reason);
      });
  }

	handleAdd = (parms) => {
		parms.command = "addtimerevent";
		console.log("form submit thuckmyballs: " + parms.start)
    axios.post(this.hostUrl + "update/", JSON.stringify(parms))
      .then((response) => {
        console.log(response);
        this.setState({jsonTable: response.data.jsonTable})
      }, (reason) => {
        console.log(reason);
      })
	}

  handleRemove = (index) => {

    let parms = {removeIndex: index,
                  command: "removetimerevent"}
    console.log("index: " +parms.removeIndex + "command: " + parms.command);
    axios.post(this.hostUrl + "update/", JSON.stringify(parms))
      .then((response) => {
        this.setState({jsonTable: response.data.jsonTable})
      }, (reason) => {
        console.log(reason);
      })
  }

  handleModeSwitch = (mode) => {
    let parms = {command: "mode",
                  mode: mode}

    axios.post(this.hostUrl + "update/", JSON.stringify(parms))
      .then((response) => {
        console.log(response);
        this.setState({mode: response.data.mode})
      }, (reason) => {
        console.log(reason);
      })
    console.log(mode);
  }

  render() {
    return(
      <div>
        <AddSlotForm onSubmit={this.handleAdd}/>
        <ModeSwitchPane mode={this.state.mode} handleModeSwitch={this.handleModeSwitch}/>
        <TimerList data={this.state} handleRemove={this.handleRemove}/>
      </div>
    )
  }
}


export {App as default}
