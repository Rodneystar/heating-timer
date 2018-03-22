import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

class TimerListRow extends React.Component {
  constructor(props) {
    super(props)
  }

  getStyle = () => {
    let selectedStyle = {
      backgroundColor : "#47A5DF",
      cursor: 'pointer'
    }
    let unselectedStyle = {
      cursor: 'pointer'
    }

    if(this.props.isSelected){
      console.log( "true: "+ selectedStyle);
      return selectedStyle;
    }else {
      console.log("false" + unselectedStyle);
      return unselectedStyle;
    }
  }

  setSelected = () => {
    this.props.onClick(this.props.id, this.props.isSelected);
  }

  render() {
    return (
      <tr
        id={`timerListRow${this.props.id}`}
        onClick={this.setSelected}
        style={this.getStyle()}>
        <td> {this.props.id}</td><td> {this.props.start}</td><td>{this.props.length}
        </td></tr>
    )
  }
}

export {TimerListRow as default}
