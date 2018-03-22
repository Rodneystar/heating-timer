import ReactDOM from 'react-dom'
import React from 'react'

<img width="75" src={props.avatar_url}/>
	<div style={{display: 'inline-block', marginLeft: 24}}>
		<div> {props.name} </div>
		<div> {props.company} </div>
	</div>
</div>
);
};


const CardList = (props) => {
return (
<div>
	{props.cards.map(card => <Card key = {card.id} {...card}/>)}
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
		{ name : "Paul O’Shannessy" ,
			company:"Facebook",
			avatar_url:"https://avatars1.githubusercontent.com/u/8445?v=4" },
		{ name : "Ben Alpert" ,
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
			<CardList cards = {this.state.cards} />
		</div>
	)
}
}

ReactDOM.render(<Card />, mountNode);
