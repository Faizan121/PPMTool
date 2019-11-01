import React, { Component } from 'react';
import { createNewUsers } from "../../actions/SecurityActions";
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import classnames from "classnames";


 class Register extends Component {

    constructor(props){
        super(props)

        this.state = {
            "username": " ",
            "fullName": " ",
            "password": "",
            "confirmPassword": "",
            "errors": { }
        }
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange (e) {
            this.setState({ [e.target.name] : e.target.value });
    }

    onSubmit (e){
        e.preventDefault();
        const newUser = {
            "username": this.state.username,
            "fullName": this.state.fullName,
            "password": this.state.password,
            "confirmPassword": this.state.confirmPassword,
        }
        this.props.createNewUsers(newUser, this.props.history);
    }

    render() {
        return (
            <div className="register">
        <div className="container">
            <div className="row">
                <div className="col-md-8 m-auto">
                    <h1 className="display-4 text-center">Sign Up</h1>
                    <p className="lead text-center">Create your Account</p>
                    <form onSubmit = {this.onSubmit} >
                        <div className="form-group">
                            <input type="text" 
                            className="form-control form-control-lg" 
                            placeholder="Full Name" name="fullName"
                                value={this.state.fullName}  onChange={this.onChange}/>
                        </div>
                        <div className="form-group">
                            <input type="text" 
                            className="form-control form-control-lg" 
                            placeholder="Email Address (username)" name="username" 
                            value={this.state.username} onChange={this.onChange} />

                        </div>
                        <div className="form-group">
                            <input type="password" 
                            className="form-control form-control-lg" 
                            placeholder="Password" name="password" 
                            value={this.state.password} onChange={this.onChange} />
                        </div>
                        <div className="form-group">
                            <input type="password" 
                            className="form-control form-control-lg" 
                            placeholder="Confirm Password" name="confirmPassword" 
                            value={this.state.confirmPassword} onChange={this.onChange} />
                        </div>
                        <input type="submit" className="btn btn-info btn-block mt-4" />
                    </form>
                </div>
            </div>
        </div>
    </div>

        )
    }
}

Register.propTypes = {

    errors: PropTypes.object.isRequired,
    createNewUser: PropTypes.func.isRequired 
}

const mapStateToProps = state => ({

    errors: state.errors
})

export default connect(mapStateToProps,{createNewUsers})(Register);
//export default Register;