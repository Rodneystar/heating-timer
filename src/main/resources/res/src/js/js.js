import ReactDOM from 'react-dom'
import React from 'react'
import axios from 'axios'

const Card = (props) => {
	return (
  	<div style={{margin: '1em'}}>
			<img width="75" src={props.avatar_url}/>
			<div style={{display: 'inline-block', marginLeft: 24}}>
				<div> {props.name} </div>
				<div> {props.company} </div>
			</div>
		</div>
  );
};


const CardList = (props) => {
	const listItems = props.cards.map((card) =>
		<Card key={card.id} avatar_url={card.avatar_url} name={card.name} company={card.company}/>
	);
	return (
  	<div>
    	{listItems}
    </div>
  )
}

class Form extends React.Component {
	state = {userName: ''}
	handleSubmit = (event) => {
  	event.preventDefault();
    console.log('Event: Form Submit: ', this.state.userName);
    axios.get(`https://api.github.com/users/${this.state.userName}`)
    	.then(resp => {
      	this.props.onSubmit(resp.data);
        this.setState({userName: ''})
      });
  };
	render() {
  	return (
      <form onSubmit={this.handleSubmit}>
        <input type="text"
        				value={this.state.userName}
                onChange={(event) => this.setState({userName: event.target.value})}
                placeholder="Github username" />
        <button type="submit"> Add card</button>
      </form>
      )
    };
	}

  class App extends React.Component {

  	state = {
    	cards: [
        { id : 2389174,
					name : "Paul O’Shannessy" ,
          company:"Facebook",
          avatar_url:"https://avatars1.githubusercontent.com/u/8445?v=4" },
        { id : 2389176,
					name : "Ben Alpert" ,
          company:"Facebook",
          avatar_url:"https://avatars0.githubusercontent.com/u/7585659?v=4" },
      ]
};

addNewCard = (cardInfo) => {
	this.setState(prevState => ({
  	cards: prevState.cards.concat(cardInfo)
  }));
}

  	render() {
    	return (
      	<div>
        	<Form onSubmit={this.addNewCard}/>
					<span>
          	<CardList cards = {this.state.cards} />
					</span>
					<span>
          	<CardList cards = {this.state.cards} />
					</span>
        </div>
      )
    }
  }

ReactDOM.render(<App/>, document.getElementById("app"));
