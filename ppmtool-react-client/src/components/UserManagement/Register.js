import React, { Component } from 'react';
import { createNewUsers } from "../../actions/SecurityActions";
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import classnames from "classnames";


 class Register extends Component {

    constructor(props){
        super(props)

        this.state = {
            "username": "",
            "fullName": "",
            "password": "",
            "confirmPassword": "",
            "errors": {}
        }
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidUpdate(prevProps,prevState){

        if (this.props.errors !== prevProps.errors){
            this.setState({errors:this.props.errors});
        }
    }
    componentDidMount(){
        if(this.props.security.validToken){
            this.props.history.push("/dashboard");
        }
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
            "errors": this.state.errors
        }
        this.props.createNewUsers(newUser, this.props.history);
    }

    render() {

        const {errors} = this.state;
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
                            className={classnames("form-control form-control-lg", {
                                "is-invalid": errors.fullName
                            })}                             
                            placeholder= "Full Name" name="fullName"
                                value={this.state.fullName}  onChange={this.onChange}/>  
                                {
                                    errors.fullName && (
                                        <div className ="invalid-feedback">{errors.fullName}</div>
                                    )
                                }
                        </div>
                        <div className="form-group">
                            <input type="text" 
                            className={classnames("form-control form-control-lg", {
                                "is-invalid": errors.username
                            })} 
                            placeholder="Email Address (username)" name="username" 
                            value={this.state.username} onChange={this.onChange} />
                            {
                                errors.username && (
                                    <div className ="invalid-feedback">{errors.username}</div>
                                )
                            }
                        </div>
                        <div className="form-group">
                            <input type="password" 
                            className={classnames("form-control form-control-lg", {
                                "is-invalid": errors.password
                            })} 
                            placeholder="Password" name="password" 
                            value={this.state.password} onChange={this.onChange} />
                            {
                                errors.password && (
                                    <div className ="invalid-feedback">{errors.password}</div>
                                )
                            }
                        </div>
                        <div className="form-group">
                            <input type="password" 
                            className={classnames("form-control form-control-lg", {
                                "is-invalid": errors.confirmPassword
                            })}  
                            placeholder="Confirm Password" name="confirmPassword" 
                            value={this.state.confirmPassword} onChange={this.onChange} />
                            {
                                errors.confirmPassword && (
                                    <div className ="invalid-feedback">{errors.confirmPassword}</div>
                                )
                            }
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
    createNewUser: PropTypes.func.isRequired,
    security: PropTypes.object.isRequired 
};

const mapStateToProps = state => ({

    errors: state.errors,
    security: state.security
});

export default connect(mapStateToProps,{createNewUsers})(Register);