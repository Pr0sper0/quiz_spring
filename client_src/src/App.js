import React, { Component } from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import AddProject from "./components/Project/AddProject";
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTasks/UpdateProjectTask";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import Landing from "./components/Layout/Landing";
import Login from "./components/UserManagement/Login";
import Register from "./components/UserManagement/Register";
import "bootstrap/dist/css/bootstrap.css";
import { BrowserRouter as Router, Route } from "react-router-dom";

import { Provider } from "react-redux";
import store from "./store";

import { SET_CURRENT_USER } from './actions/types';
import jwt_decode from "jwt-decode";
import setJWTToken from "./securityUtils/setJWTToken";

const jwtToken = localStorage.jwtToken;

if (jwtToken) {
    setJWTToken(jwtToken);
    const decoded_jwtToken = jwt_decode(jwtToken);
    store.dispatch({
        type: SET_CURRENT_USER,
        payload: decoded_jwtToken
    });

    const currentTime = Date.now()/1000
    if(decoded_jwtToken.exp < currentTime) {
        window.location.href="/";
    }
}

class App extends Component {
    render() {
        return (
            <Provider store={store}>
                <Router>
                    <div className="App">
                        <Header />
                        {/*                         
                            // public Routes
                        */}
                        <Route exact path="/" component={Landing} />
                        <Route exact path="/login" component={Login} />
                        <Route exact path="/register" component={Register} />
                        {/*
                            // private Routes
                        */}

                        <Route exact path="/projectBoard/:id" component={ProjectBoard} />
                        <Route exact path="/dashboard" component={Dashboard} />
                        <Route exact path="/addProject" component={AddProject} />
                        <Route exact path="/updateProject/:id" component={UpdateProject} />
                        <Route exact path="/addProjectTask/:id" component={AddProjectTask} />
                        <Route exact path="/updateProjectTask/:backlog_id/:pt_id" component={UpdateProjectTask} />
                    </div>
                </Router>
            </Provider>
        );
    }
}

export default App;
