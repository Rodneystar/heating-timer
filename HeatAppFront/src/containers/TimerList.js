import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import TimerListRow from './TimerListRow.js'

class TimerList extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.props.data;
  }

selectRow = (index, selected) => {
  if(selected) this.setState( {removeIndex: -1});
  else this.setState( {removeIndex: index });
  console.log("index : " + this.state.removeIndex);
}

getIsSelected = (index) => {
  if(index===this.state.removeIndex) {
    return true;
  }else {
    return false;
  }
}

doRemoveItem = () => {
  this.props.handleRemove(this.state.removeIndex);
  console.log("remove: " + this.state.removeIndex);
}

renderTimerListArray = () => {
    let timerListArray = this.props.data.jsonTable.map((row,index) => (
        <TimerListRow start={row.onTime} length={row.length} key={index} id={index}
              onClick={this.selectRow}
              isSelected={this.getIsSelected(index)}/>
  ));
  return timerListArray;
}

  render() {
    return (
      <div id="timerTable" className="container container-blue">
			<header></header>
			<table className="table table-blank table-hover" >
				<thead>
					<tr>
					<th>ID</th>
						<th>Start Time</th>
						<th>Length</th>
					</tr>
				</thead>
				<tbody id="tableBody">
            {this.renderTimerListArray()}
				</tbody>
			</table>
			<span className="form form-horizontal">
				<input type="button" value="Remove" onClick={this.doRemoveItem}></input>
				<p id="removeMessage"></p>
			</span>
			<footer></footer>
		</div>
    )
  }
}

export {TimerList as default}
