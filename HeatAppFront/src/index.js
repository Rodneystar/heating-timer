

import React from 'react'
import ReactDOM from 'react-dom'
import { AppContainer } from 'react-hot-loader'
import axios from 'axios'

import App from './containers/App.js'


function render() {
	const Component = require("./containers/App.js").default;
	ReactDOM.render(
		<AppContainer>
			<Component/>
		</AppContainer>,
		document.getElementById('root')
	);
};

render(App);

if (module.hot) {
	module.hot.accept('./containers/App', () => {
		render()})
}
